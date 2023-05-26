package com.xasanboyevdiyorbek.bmi_tatu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Orders_fragment extends Fragment {

    MyBooks_Adapter myBooks_adapter;
    RecyclerView recyclerView;
    Query databaseReference;
    SearchView searchView;


    ArrayList<Books> books_list;
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



            recyclerView = v.findViewById(R.id.order_view);
            searchView = v.findViewById(R.id.search_bar);
            searchView.setQueryHint("Kitob nomini kiriting...");

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


            books_list = new ArrayList<>();


            myBooks_adapter = new MyBooks_Adapter(getActivity(), books_list);

            recyclerView.setAdapter(myBooks_adapter);

            databaseReference = FirebaseDatabase.getInstance().getReference("books");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        Books books = dataSnapshot.getValue(Books.class);
                        books_list.add(books);

                    }
                    myBooks_adapter.notifyDataSetChanged();


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    databaseReference = FirebaseDatabase.getInstance().getReference("books").orderByChild("title").startAt(s).endAt(s+"\uf8ff");
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            books_list.clear();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                                Books books = dataSnapshot.getValue(Books.class);
                                books_list.add(books);

                            }
                            myBooks_adapter.notifyDataSetChanged();


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    return false;
                }
            });



        } catch (Exception e) {
            Toast.makeText(getActivity(), "xatolik " + e, Toast.LENGTH_SHORT).show();
        }

        return v;

    }


}

