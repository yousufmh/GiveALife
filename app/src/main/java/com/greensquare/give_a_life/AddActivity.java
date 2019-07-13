package com.greensquare.give_a_life;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.greensquare.give_a_life.models.Data;
import com.greensquare.give_a_life.models.Request;
import com.greensquare.give_a_life.models.User;
import com.greensquare.give_a_life.Utility.DataMaster;

import java.text.DateFormat;
import java.util.Date;


public class AddActivity extends AppCompatActivity {

    private DataMaster dm;
    private Data data;
    private User user;
    private Request request;

    private Button confrim,cancel;
    private EditText name, mobile, location,note,dueDateText;
    private Spinner bloodType;
    private RadioButton donor,requester,asap,dueDate;
    private ArrayAdapter<CharSequence> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        dm = new DataMaster(this);
        data = Data.getInstance();
        user = data.getUser();
        request = new Request();



        name = findViewById(R.id.name_add);
        mobile = findViewById(R.id.mobile_add);
        location = findViewById(R.id.address_add);
        note = findViewById(R.id.note);
        bloodType = findViewById(R.id.blood_type2);
        donor = findViewById(R.id.donor);
        requester = findViewById(R.id.requester);
        asap = findViewById(R.id.asap);
        dueDate = findViewById(R.id.due_date);
        dueDateText = findViewById(R.id.due_date_text);
        confrim = findViewById(R.id.confirm_add);
        cancel = findViewById(R.id.cancel);

        adapter = ArrayAdapter.createFromResource(this,
                R.array.blood_type,android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodType.setAdapter(adapter);


    }

    @Override
    protected void onStart() {
        super.onStart();

        name.setText(user.getName());
        mobile.setText(user.getMobile());
        dueDateText.setVisibility(View.INVISIBLE);

        dueDate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    dueDateText.setVisibility(View.VISIBLE);
                }else{
                    dueDateText.setVisibility(View.INVISIBLE);
                }
            }
        });

        bloodType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                request.setBloodType(adapterView.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        confrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request.setName(name.getText().toString());
                request.setDonor(donor.isChecked());
                request.setLocation(location.getText().toString());
                request.setNote(note.getText().toString());
                request.setUserContact(mobile.getText().toString());
                Date date = new Date();
                request.setPostDate(DateFormat.getDateInstance().format(date));
                if(asap.isChecked())
                    request.setDueDate(asap.getText().toString());
                else
                    request.setDueDate(dueDateText.getText().toString());

                dm.addRequest(request);

                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
