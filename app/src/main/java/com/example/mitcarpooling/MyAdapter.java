package com.example.mitcarpooling;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private List<DataClass> dataList;

    public MyAdapter(List<DataClass> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DataClass data = dataList.get(position);

        holder.recDate.setText(data.getDate());
        holder.recTime.setText(data.getTime());
        holder.recSource.setText(data.getSource());
        holder.recDestination.setText(data.getDestination());
        holder.recAmount.setText(data.getAmount());

        holder.recCard.setOnClickListener(view -> {
            // Create an Intent to start the detail activity
            Intent intent = new Intent(context, recyclerviewDetailed.class);

            // Pass the ride details to the detailed activity
            intent.putExtra("date", data.getDate());
            intent.putExtra("time", data.getTime());
            intent.putExtra("source", data.getSource());
            intent.putExtra("destination", data.getDestination());
            intent.putExtra("amount", data.getAmount());
            intent.putExtra("vehicleNo", data.getVehicleNo());

            // Start the detail activity
            context.startActivity(intent);
        });
    }




    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {

    TextView recDate, recTime, recSource, recDestination, recAmount;
    CardView recCard;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        // Initialize views from the layout file (recycler_item.xml)
        recDate = itemView.findViewById(R.id.recDate);
        recTime = itemView.findViewById(R.id.recTime);
        recSource = itemView.findViewById(R.id.recSource);
        recDestination = itemView.findViewById(R.id.recDestination);
        recAmount = itemView.findViewById(R.id.recAmount);
        recCard = itemView.findViewById(R.id.recCard); // CardView that contains the views
    }
}