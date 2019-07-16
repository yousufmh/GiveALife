package com.greensquare.give_a_life.Utility;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.greensquare.give_a_life.models.Request;
import com.greensquare.give_a_life.models.User;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class DataMaster {


    private Activity activity;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseDatabase DB = FirebaseDatabase.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user;

    private boolean internetAccess;
    private final static String TAG = "DataMaster";

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
                    user = task.getResult().getUser();
                    loginUser.login(true,false,null);
                }
            }else{
                loginUser.login(false,false,null);
            }
        }
    });
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

    public void addRequest(Request request) {

        db.collection("requests").add(request);

    }

    public void internetConnection(final CheckInternetAccess connect){

        new InternetAccess(new CheckInternetAccess() {
            @Override
            public void access(boolean connected) {
                connect.access(connected);
            }
        }).execute();

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

    public interface CheckInternetAccess{
        void access(boolean connected);
    }


    public class InternetAccess extends AsyncTask<Void,Void,Boolean>{

        CheckInternetAccess checking;

        public InternetAccess(CheckInternetAccess mChecking){
            checking = mChecking;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            Socket socket = new Socket();
            InetSocketAddress ipAddress = new InetSocketAddress("8.8.8.8", 53);
            try {
                socket.connect(ipAddress,2500);
                socket.close();
                return true;
            } catch (IOException e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {

            checking.access(aBoolean);
            Log.d(TAG, "The internet Access is within onPost "+aBoolean);
            super.onPostExecute(aBoolean);
        }
    }

}
