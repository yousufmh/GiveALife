package com.greensquare.give_a_life;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.greensquare.give_a_life.models.Data;
import com.greensquare.give_a_life.models.Request;
import com.greensquare.give_a_life.databinding.ActivitySingalItemBinding;

public class SingalItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Data data = Data.getInstance();
        Request request = data.getRequest();
        ActivitySingalItemBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_singal_item);
        binding.setRequest(request);


    }
}
