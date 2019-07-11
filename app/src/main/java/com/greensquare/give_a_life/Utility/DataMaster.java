package com.greensquare.give_a_life.Utility;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.greensquare.give_a_life.Models.User;

public class DataMaster {


    private Activity activity;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user;

    public DataMaster(Activity activity){
        this.activity = activity;
    }



    public void signUP(final User newUser, String passwordStr, final CreateUser createUser) {

        auth.createUserWithEmailAndPassword(
                newUser.getEmail()
                ,passwordStr)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    createUser.exisit(false);
                    db.collection("users").document(newUser.getEmail()).set(newUser);
                    user = task.getResult().getUser();
                    verfyEmail();
                }else{
                    createUser.exisit(true);
                }
            }
        });
    }

    private void verfyEmail() {

        user.sendEmailVerification().addOnCompleteListener(activity, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }

    public void login(String usernameStr, String passwordStr, final CreateUser exist) {

    auth.signInWithEmailAndPassword(usernameStr,passwordStr).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {

            if(task.isSuccessful()&&auth!=null){
                if(task.getResult().getUser().isEmailVerified()) {
                    exist.exisit(true);
                }else{
                    if(activity.getCurrentFocus()!=null)
                    Snackbar.make(activity.getCurrentFocus(),"your Email is not verified",Snackbar.LENGTH_INDEFINITE).show();
                    exist.exisit(false);
                }
            }else{
                exist.exisit(false);
            }
        }
    });
    }


    public interface CreateUser {
        void exisit(boolean exisit);
    }
}
