package com.example.avnish.whatsapp_clone.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.avnish.whatsapp_clone.R;
import com.example.avnish.whatsapp_clone.UserListRecyclerView.UserList_Databook;
import com.example.avnish.whatsapp_clone.UserListRecyclerView.userlist_Adapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllUserFragment extends Fragment {
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ArrayList<UserList_Databook> arrayList;
    userlist_Adapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_userlist, container, false);
        //initialize()
        {
            recyclerView = (RecyclerView) view.findViewById(R.id.Recyclerview);
            linearLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            mAdapter = new userlist_Adapter(arrayList);
            recyclerView.setAdapter(mAdapter);
            fetchData();

        }

        return view;
    }

    private void fetchData() {
        arrayList = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    UserList_Databook databook = dataSnapshot.getValue(UserList_Databook.class);
                    arrayList.add(databook);
                    mAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
