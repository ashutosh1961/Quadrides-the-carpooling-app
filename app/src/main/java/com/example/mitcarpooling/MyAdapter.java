package com.example.mitcarpooling;

import android.content.Context;
import android.content.Intent;
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

        holder.recDate.setText(dataList.get(position).getDataDate());
        holder.recTime.setText(dataList.get(position).getDataTime());
        holder.recSource.setText(dataList.get(position).getDataSource());
        holder.recDestination.setText(dataList.get(position).getDataDestination());
        holder.recAmount.setText(dataList.get(position).getDataAmount());

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, recyclerviewDetailed.class);
                intent.putExtra("amount", dataList.get(holder.getAdapterPosition()).getDataAmount());

                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}


class MyViewHolder extends RecyclerView.ViewHolder{

    TextView recDate, recTime, recSource, recDestination, recAmount;
    CardView recCard;


    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        recDate = itemView.findViewById(R.id.recDate);
        recTime = itemView.findViewById(R.id.recTime);
        recSource = itemView.findViewById(R.id.recSource);
        recDestination = itemView.findViewById(R.id.recDestination);
        recAmount = itemView.findViewById(R.id.recAmount);



    }
}
