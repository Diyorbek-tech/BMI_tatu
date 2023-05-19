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

public class Admin_User_Adapter extends RecyclerView.Adapter<Admin_User_Adapter.MyViewHolder> {


    private final Context context;
    ArrayList<User> list;

    public Admin_User_Adapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.admin_order_item, parent, false);
        return new MyViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User user = list.get(position);
        holder.ismi.setText(user.getIsmi());
        holder.email.setText(user.getEmail());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView ismi, email;


        public MyViewHolder(View itemview) {
            super(itemview);
            ismi = itemview.findViewById(R.id.usernameid);
            email = itemview.findViewById(R.id.emailid);

            itemview.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            try {
                int positions = getAdapterPosition();
                Intent intent = new Intent(context, Admin_User_Data.class);

                intent.putExtra("name", list.get(positions).ismi);
                intent.putExtra("login", list.get(positions).login);
                intent.putExtra("email", list.get(positions).email);
                intent.putExtra("phone", list.get(positions).phone);

                context.startActivity(intent);
            }
            catch (Exception exception){

                Toast.makeText(context,"Adapter"+exception.getMessage() , Toast.LENGTH_SHORT).show();

            }


        }
    }

}
