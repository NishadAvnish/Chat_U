package com.example.avnish.whatsapp_clone.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.avnish.whatsapp_clone.R;
import com.example.avnish.whatsapp_clone.UserListRecyclerView.UserList_Databook;
import com.example.avnish.whatsapp_clone.UserListRecyclerView.userlist_Adapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class chatfragment extends Fragment {
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ArrayList<UserList_Databook> arrayList=new ArrayList<>();
    userlist_Adapter mAdapter;
    String key;
    int count=1;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d("SET","SET USER VISIBLE");
        if(isVisibleToUser)
        {   if(count%2==0)
            {getFragmentManager().beginTransaction().detach(this).attach(this).commit();
            }
            arrayList.clear();
            count=1;
        }
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("SET","onView CREATED");
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        count=count+1;
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new userlist_Adapter(arrayList,3,getContext());
        recyclerView.setAdapter(mAdapter);

        FirebaseDatabase.getInstance().getReference().child("User").child((FirebaseAuth.getInstance().getCurrentUser().getUid())).child("Friend").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    key = dataSnapshot1.getKey();
                    if (key != null) {
                        FirebaseDatabase.getInstance().getReference().child("User").child(key)
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        UserList_Databook databook = dataSnapshot.getValue(UserList_Databook.class);
                                        arrayList.add(databook);
                                        mAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chatfragment, container, false);
        Log.d("SET","ON CREATE VIEW");
        return view;
    }



}
