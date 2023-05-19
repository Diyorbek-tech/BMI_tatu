package com.xasanboyevdiyorbek.bmi_tatu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Orders_fragment extends Fragment {

    MyOrders_Adapter myadapter_order;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;


    ArrayList<Orders> orders_list;
    String[] name;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Orders_fragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Orders_fragment newInstance(String param1, String param2) {
        Orders_fragment fragment = new Orders_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_orders_fragment, container, false);
        try {


            User_info user = new User_info();

            recyclerView = v.findViewById(R.id.order_view);

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


            orders_list = new ArrayList<>();


            myadapter_order = new MyOrders_Adapter(getActivity(), orders_list);

            recyclerView.setAdapter(myadapter_order);

            databaseReference = FirebaseDatabase.getInstance().getReference("Orders/" + user.getName());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        Orders orders = dataSnapshot.getValue(Orders.class);
                        orders_list.add(orders);

                    }
                    myadapter_order.notifyDataSetChanged();


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        } catch (Exception e) {
            Toast.makeText(getActivity(), "xatolik " + e, Toast.LENGTH_SHORT).show();
        }

        return v;

    }


}

