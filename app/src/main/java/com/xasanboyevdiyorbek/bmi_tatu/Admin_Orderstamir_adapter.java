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

public class Admin_Orderstamir_adapter extends RecyclerView.Adapter<Admin_Orderstamir_adapter.MyViewHolder> {


    private Context context;
    ArrayList<Ordertamir> list;

    public Admin_Orderstamir_adapter(Context context, ArrayList<Ordertamir> list) {
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
        Ordertamir order = list.get(position);

        holder.product.setText(order.getProduct());
        holder.count.setText(order.getPhone());
        holder.soni.setText("phone:");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView product,count,soni;


        public MyViewHolder(View itemview) {
            super(itemview);

            product = itemview.findViewById(R.id.ismi);
            count = itemview.findViewById(R.id.email);
            soni = itemview.findViewById(R.id.sonitxtid);

            itemview.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            try {
                int positions = getAdapterPosition();
//                Toast.makeText(context, " " + positions + "-bosildi!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, Ordertamir_details.class);

                intent.putExtra("product", list.get(positions).product);
                intent.putExtra("phone", list.get(positions).phone);
                intent.putExtra("img", list.get(positions).urlimg);
                intent.putExtra("addition", list.get(positions).addition);

                context.startActivity(intent);
            }
            catch (Exception exception){

                Toast.makeText(context,"Adapter"+exception.getMessage() , Toast.LENGTH_SHORT).show();

            }


        }
    }

}
