package com.greensquare.give_a_life.Utility;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.greensquare.give_a_life.models.Data;
import com.greensquare.give_a_life.models.Request;
import com.greensquare.give_a_life.R;
import com.greensquare.give_a_life.SingalItem;

import java.util.ArrayList;

public class RequestListAdaptor extends RecyclerView.Adapter<RequestListAdaptor.ReqestVH> {


    public class ReqestVH extends RecyclerView.ViewHolder {

        TextView name,date, bloodType;
        public ReqestVH(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            bloodType = itemView.findViewById(R.id.blood_type);
        }
    }

    private ArrayList<Request> requests;
    private Context context;
    public RequestListAdaptor(ArrayList<Request> requestsPara,Context context){
        this.requests = requestsPara;
        this.context = context;
    }

    @NonNull
    @Override
    public ReqestVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new ReqestVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReqestVH holder, int position) {
        final Data data = Data.getInstance();
        final Request request = requests.get(position);
        holder.name.setText(request.getName());
        holder.date.setText(request.getDueDate());
        holder.bloodType.setText(request.getBloodType());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                data.setRequest(request);
                Intent intent = new Intent(context, SingalItem.class);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return requests.size();
    }




}
