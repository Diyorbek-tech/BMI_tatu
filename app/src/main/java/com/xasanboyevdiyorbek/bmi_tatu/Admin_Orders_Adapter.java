package com.xasanboyevdiyorbek.bmi_tatu;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Admin_Orders_Adapter extends RecyclerView.Adapter<Admin_Orders_Adapter.MyViewHolder> {


    private Context context;
    ArrayList<Orders> list;

    public Admin_Orders_Adapter(Context context, ArrayList<Orders> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Orders order = list.get(position);

        holder.product.setText(order.getProduct());
        holder.count.setText(order.getCount());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView product,count;


        public MyViewHolder(View itemview) {
            super(itemview);

            product = itemview.findViewById(R.id.ismi);
            count = itemview.findViewById(R.id.email);

            itemview.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            try {
                int positions = getAdapterPosition();
                // Toast.makeText(context, " " + positions + "-bosildi!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, Order_datails.class);

                intent.putExtra("product", list.get(positions).product);
                intent.putExtra("weight", list.get(positions).weight);
                intent.putExtra("height", list.get(positions).hieght);
                intent.putExtra("addition", list.get(positions).addition);
                intent.putExtra("count", list.get(positions).count);

                context.startActivity(intent);
            }
            catch (Exception exception){

                Toast.makeText(context,"Adapter"+exception.getMessage() , Toast.LENGTH_SHORT).show();

            }


        }
    }

}
