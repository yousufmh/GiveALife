package com.greensquare.give_a_life.Tabs;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.greensquare.give_a_life.Models.Data;
import com.greensquare.give_a_life.R;
import com.greensquare.give_a_life.Utility.DataMaster;
import com.greensquare.give_a_life.Utility.RequestListAdaptor;

/**
 * A simple {@link Fragment} subclass.
 */
public class Donor extends Fragment {


    public Donor() {
        // Required empty public constructor
    }

    private Data data;
    private DataMaster dm;
    private RecyclerView donerRV;
    private RequestListAdaptor adaptor;
    private RecyclerView.LayoutManager layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.fragment_donor,container,false);



        return view;
    }

}
