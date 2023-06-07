package com.xasanboyevdiyorbek.bmi_tatu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Home_fragment extends Fragment {

    TextView fullname, emailuser;
    Button orderbtn,ordertamirbtn;


    DatabaseReference databaseReference;


    ArrayList<String> listuser;
    ArrayList<Orders> orders_list;
    String[] name;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Home_fragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Home_fragment newInstance(String param1, String param2) {
        Home_fragment fragment = new Home_fragment();
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
        View v = inflater.inflate(R.layout.fragment_home_fragment, container, false);
        try {

            fullname = v.findViewById(R.id.full_name);
            emailuser = v.findViewById(R.id.emailuser);
            orderbtn = v.findViewById(R.id.orderbtn);
            ordertamirbtn = v.findViewById(R.id.ordertamirbtn);


            orderbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getActivity(),Make_Order.class);
                    startActivity(intent);
                }
            });

            ordertamirbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i =new Intent(getActivity(),MakeTamirlashOrder.class);
                    startActivity(i);
                }
            });

            listuser = new ArrayList<>();
            User_info insonhome = new User_info();
            databaseReference = FirebaseDatabase.getInstance().getReference("users/" + insonhome.getName());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    String ism=dataSnapshot.child("ismi").getValue(String.class);
                    String email=dataSnapshot.child("email").getValue(String.class);
                    fullname.setText(ism);
                    emailuser.setText(email);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getActivity(), "xatolik " + databaseError, Toast.LENGTH_SHORT).show();

                }
            });


        } catch (Exception e) {
            Toast.makeText(getActivity(), "xatolik " + e, Toast.LENGTH_SHORT).show();
        }

        return v;

    }


}

