package ru.kuznecov.ivan.rent.utils;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class FireBaseSaveAvatar {
    private static final String TAG = "FireBaseSaveAvatar";
    private StorageReference storageReference;
    private UriPhotoListener uriPhotoListener;

    public interface UriPhotoListener{
        void getStringPhoto(String photo);
    }
    public void setUriPhotoListener(UriPhotoListener uriPhotoListener){
        this.uriPhotoListener = uriPhotoListener;
    }
    public FireBaseSaveAvatar() {

        storageReference = FirebaseStorage.getInstance().getReference();
    }


    public void saveAvatar(Uri photoURI, String waySave){
        final StorageReference ref = storageReference.child(waySave);
        UploadTask uploadTask = ref.putFile(photoURI);
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                // Continue with the task to get the download URL
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    uriPhotoListener.getStringPhoto(downloadUri.toString());
                } else {
                    // Handle failures
                    // ...
                }
            }
        });
    }
}
