package com.greensquare.give_a_life.Utility;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.greensquare.give_a_life.Models.Request;
import com.greensquare.give_a_life.Models.User;
import com.greensquare.give_a_life.R;

import java.util.ArrayList;
import java.util.List;

public class DataMaster {


    private Activity activity;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user;

    public DataMaster(Activity activity){
        this.activity = activity;
    }

    public FirebaseAuth getAuth(){
        return auth;
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

    public void verfyEmail() {

        user.sendEmailVerification().addOnCompleteListener(activity, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }

    public void login(final String usernameStr, String passwordStr, final LoginUser loginUser) {

    auth.signInWithEmailAndPassword(usernameStr,passwordStr).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {

            if(task.isSuccessful()&&auth!=null){
                if(task.getResult().getUser().isEmailVerified()) {
                    db.collection("users")
                            .document(usernameStr)
                            .get()
                            .addOnCompleteListener(activity, new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if(task.isSuccessful()) {
                                        User user = task.getResult().toObject(User.class);
                                        loginUser.login(true, true,user);
                                    }else{
                                        Log.d("DM","getting Data failed");
                                        loginUser.login(false,false,null);
                                    }
                                }
                            });
                }else{
                    loginUser.login(true,false,null);
                }
            }else{
                loginUser.login(false,false,null);
            }
        }
    });
    }

    public void checkConnection() {
    }

    public void getData(boolean DorR,final GetRequests getRequests) {
        final ArrayList<Request> requests = new ArrayList<>();

        db.collection("requests").whereEqualTo("donor",DorR)
                .get()
                .addOnCompleteListener(activity,
                    new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    if(task.isSuccessful()){

                        Request temp;
                        List<DocumentSnapshot> documentSnapshot = task.getResult().getDocuments();

                        for(int i=0; i<documentSnapshot.size();i++){
                            DocumentSnapshot doc = documentSnapshot.get(i);
                            temp = doc.toObject(Request.class);
                            requests.add(temp);
                        }

                        getRequests.fetch(requests);

                    }

            }
        });

    }


        public interface CreateUser {
        void exisit(boolean exisit);
    }

    public interface GetRequests{
        void fetch(ArrayList<Request> requests);
    }

    public interface LoginUser {
        void login(boolean exists, boolean verified, User user);
    }

}
