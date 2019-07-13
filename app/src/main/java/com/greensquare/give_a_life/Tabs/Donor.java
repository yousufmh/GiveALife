package com.greensquare.give_a_life.Tabs;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.greensquare.give_a_life.models.Data;
import com.greensquare.give_a_life.models.Request;
import com.greensquare.give_a_life.R;
import com.greensquare.give_a_life.Utility.DataMaster;
import com.greensquare.give_a_life.Utility.RequestListAdaptor;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Donor extends Fragment {


    public Donor() {
        // Required empty public constructor
    }

    private Data data;
    private DataMaster dm;
    private RecyclerView donorRV;
    private RequestListAdaptor adaptor;
    private RecyclerView.LayoutManager layout;
    private ArrayList<Request> donors;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.fragment_donor,container,false);
        data = Data.getInstance();
        dm = new DataMaster(getActivity());
        donors = new ArrayList<>();
        donorRV = view.findViewById(R.id.rv_donor);
        adaptor = new RequestListAdaptor(donors,getContext());
        layout = new LinearLayoutManager(getContext());

        donorRV.setAdapter(adaptor);
        donorRV.setLayoutManager(layout);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        dm.getData(true, new DataMaster.GetRequests() {
            @Override
            public void fetch(ArrayList<Request> requests) {

                donors.addAll(requests);
                adaptor.notifyDataSetChanged();

            }
        });

    }
}
