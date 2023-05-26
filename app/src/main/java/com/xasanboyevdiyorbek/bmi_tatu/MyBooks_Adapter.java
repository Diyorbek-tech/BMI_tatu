package com.xasanboyevdiyorbek.bmi_tatu;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyBooks_Adapter extends RecyclerView.Adapter<MyBooks_Adapter.MyViewHolder> {


    private Context context;
    ArrayList<Books> list;

    public MyBooks_Adapter(Context context, ArrayList<Books> list) {
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
        Books order = list.get(position);

        holder.kitobnomi.setText(order.getTitle());
        holder.muallif.setText(order.getMuallif());
        holder.rating.setText(order.getRating());
        holder.time.setText(order.getAddedtime());
        holder.year.setText(order.getYear());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView kitobnomi, muallif, rating, time,year;


        public MyViewHolder(View itemview) {
            super(itemview);

            kitobnomi = itemview.findViewById(R.id.kitobnomi);
            muallif = itemview.findViewById(R.id.muallif);
            rating = itemview.findViewById(R.id.ratingitem);
            time = itemview.findViewById(R.id.timeadded);
            year = itemview.findViewById(R.id.yil);

            itemview.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            try {
                int positions = getAdapterPosition();
//                Toast.makeText(context, " " + positions + "-bosildi!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setType("application/pdf");
                intent.setData(Uri.parse(list.get(positions).getUrlbook()));
                context.startActivity(intent);
//                Intent intent = new Intent(context, Order_datails.class);
//
//                intent.putExtra("product", list.get(positions).product);
//                intent.putExtra("weight", list.get(positions).weight);
//                intent.putExtra("height", list.get(positions).hieght);
//                intent.putExtra("addition", list.get(positions).addition);
//                intent.putExtra("count", list.get(positions).count);
//
//                context.startActivity(intent);
            } catch (Exception exception) {

                Toast.makeText(context, "Adapter" + exception.getMessage(), Toast.LENGTH_SHORT).show();

            }


        }
    }

}
