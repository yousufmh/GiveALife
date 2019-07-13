package com.greensquare.give_a_life;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;
import com.greensquare.give_a_life.models.Data;
import com.greensquare.give_a_life.models.User;
import com.greensquare.give_a_life.Utility.DataMaster;

public class Signup extends AppCompatActivity {


    private Button confirm;
    private EditText name, mobile,email,password,confirmPassword;
    private Spinner bloodtype;
    private ProgressBar progressBar;
    private User user;
    private DataMaster dm;
    private Data data;
    private ArrayAdapter<CharSequence> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        dm = new DataMaster(this);
        data = Data.getInstance();
        user = new User();

        name = findViewById(R.id.name);
        mobile = findViewById(R.id.mobile);
        email = findViewById(R.id.email_SignUP);
        password = findViewById(R.id.password_SignUp);
        confirmPassword = findViewById(R.id.password_SignUp2);

        bloodtype = findViewById(R.id.blood_type);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.blood_type,android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodtype.setAdapter(adapter);

        confirm = findViewById(R.id.confirm);
    }

    @Override
    protected void onStart() {
        super.onStart();

        bloodtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                user.setBloodType(adapterView.getItemAtPosition(i).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passwordStr = password.getText().toString();
                String emailStr = email.getText().toString();
                boolean passwordMatch = passwordStr.equals(confirmPassword.getText().toString());
                boolean passwordComplex = passwordStr.length() >= 8;
                boolean emailCheck = emailStr.equals("")||emailStr.contains(" ")||!emailStr.contains("@");
                boolean passwordEmpty = passwordStr.equals("")||passwordStr.contains(" ");



                if(!passwordMatch){
                    Snackbar.make(password,R.string.password_not_match,Snackbar.LENGTH_LONG).show();
                    password.setText("");
                    confirmPassword.setText("");
                    return;
                }

                if(!passwordComplex){
                    Snackbar.make(password,R.string.password_restruction,Snackbar.LENGTH_LONG).show();
                    password.setText("");
                    confirmPassword.setText("");
                    return;
                }

                if(passwordEmpty){
                    Snackbar.make(password,R.string.required,Snackbar.LENGTH_LONG).show();
                    password.setText("");
                    confirmPassword.setText("");
                    return;
                }

                if(emailCheck){
                    Snackbar.make(password,R.string.email_restruction,Snackbar.LENGTH_LONG).show();
                    return;
                }


                user.setName(name.getText().toString());
                user.setMobile(mobile.getText().toString());
                user.setEmail(email.getText().toString());


                dm.signUP(user, passwordStr, new DataMaster.CreateUser() {
                    @Override
                    public void exisit(boolean exisit) {
                        if (!exisit){
                            Intent intent = new Intent(Signup.this, Login.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Snackbar.make(password,R.string.error_duplicate,Snackbar.LENGTH_LONG).show();
                            Intent intent = new Intent(Signup.this, Signup.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });

            }
        });
    }
}
