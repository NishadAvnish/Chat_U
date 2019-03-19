package com.example.avnish.whatsapp_clone.Basic_Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.avnish.whatsapp_clone.R;
import com.example.avnish.whatsapp_clone.UserListRecyclerView.UserList_Databook;
import com.example.avnish.whatsapp_clone.UserListRecyclerView.userlist_Adapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FindFriend extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ArrayList<UserList_Databook> arrayList=null;
    userlist_Adapter mAdapter;
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friend);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("FIND FRIEND");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        arrayList = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(FindFriend.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new userlist_Adapter(arrayList, 1, this);
        recyclerView.setAdapter(mAdapter);

        FirebaseDatabase.getInstance().getReference().child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                dataSnapshot.getKey();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    dataSnapshot1.getKey();
                    if(dataSnapshot1.getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){

                    }
                    else {
                        UserList_Databook databook = dataSnapshot1.getValue(UserList_Databook.class);
                        arrayList.add(databook);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}