package com.example.avnish.whatsapp_clone.fragment;


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
import android.widget.Toast;

import com.example.avnish.whatsapp_clone.R;
import com.example.avnish.whatsapp_clone.RecyclerView.GreenAdapter;
import com.example.avnish.whatsapp_clone.RecyclerView.databook;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class groupFragment extends Fragment {
    RecyclerView listView;
    Iterator iterator;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.fragment_group,container,false);

        listView=(RecyclerView) view.findViewById(R.id.listview);
        final ArrayList<databook> arrayList= new ArrayList<>();
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getActivity());
        listView.setLayoutManager(linearLayoutManager);
        listView.setHasFixedSize(true);



        (FirebaseDatabase.getInstance().getReference().child("Group")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot child: dataSnapshot.getChildren()){
                        arrayList.add(new databook(child.getKey()));

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        GreenAdapter greenAdapter = new GreenAdapter(arrayList);

        listView.setAdapter(greenAdapter);

     return view;



    }
}
