package ru.kuznecov.ivan.rent.activity;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import ru.kuznecov.ivan.rent.R;


public class HomeActivity extends BaseActivity {

    private static final String TAG = "HomeActiv";

    FirebaseAuth mAuth = FirebaseAuth .getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initBottomNavigationView(1);
        Log.i(TAG, "Hi!");


      /*  createUser();
        signInUser();*/

    }
 /*   private void createUser(){
        mAuth.createUserWithEmailAndPassword("eon123.09@gmail.com", "neo12309")
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.i(TAG, "create: OOOOOOOOOKKKKK");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            Log.i(TAG , "create: Noooooooooooooo");
                        }
                    }
                });
    }

    private void signInUser(){
        mAuth.signInWithEmailAndPassword("neo123.09@gmail.com", "neo12309")
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.i(TAG, "signInWithEmail:failure", task.getException());

                        }

                        // ...
                    }
                });
    }*/
}
