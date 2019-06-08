package ru.kuznecov.ivan.rent.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MakePhoto {

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    private Activity activity;
    private Uri photoUri;

    public MakePhoto(Activity activity) {
        this.activity = activity;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        return image;
    }

    public Uri dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            File image = null;
            try {
                image = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            photoUri = FileProvider.getUriForFile(activity,
                    "ru.kuznecov.ivan.rent.fileprovider",
                    image);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            return photoUri;
        }
        return null;
    }
}
