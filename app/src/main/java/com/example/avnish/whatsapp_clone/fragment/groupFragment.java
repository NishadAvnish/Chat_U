package com.example.avnish.whatsapp_clone.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.avnish.whatsapp_clone.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class groupFragment extends Fragment {

    ListView listview;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> arrayList= new ArrayList<String>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.fragment_group,container,false);
        initialize(view);
        retriveAndDisplay();
        return view;



    }


    private void initialize(View view) {
        listview=(ListView)view.findViewById(R.id.listview);
        arrayAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,arrayList);
        listview.setAdapter(arrayAdapter);


    }
    private void retriveAndDisplay() {
        FirebaseDatabase.getInstance().getReference().child("Group").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    arrayList.clear();
                    Iterator iterator= dataSnapshot.getChildren().iterator();
                    while(iterator.hasNext()){
                         arrayList.add(((DataSnapshot)iterator.next()).getKey());
                         arrayAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



}
