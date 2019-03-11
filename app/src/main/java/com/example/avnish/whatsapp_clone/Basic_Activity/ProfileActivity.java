package com.example.avnish.whatsapp_clone.Basic_Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.avnish.whatsapp_clone.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView image;
    Button sendRequest, deleteRequest;
    TextView name;
    Intent i;
    String Uid;
    DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);


        //initialze
        {
            image = findViewById(R.id.ProfileImage);
            image.setImageResource(R.drawable.face);
            name = findViewById(R.id.ProfileName);
            sendRequest = findViewById(R.id.Profilesendrequest);
            deleteRequest = findViewById(R.id.Profiledeleterequest);
            databaseRef = FirebaseDatabase.getInstance().getReference();
            i = getIntent();


        }

        if (i.getExtras() != null) {
            Uid = i.getExtras().get("Tag").toString();
            retrieve(Uid);
        }


     //   to check if the request is already send or not
      databaseRef.child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("SendRequest").child(Uid).child("flag").addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
             if(dataSnapshot.exists()&& dataSnapshot.getValue().toString().equals("yes")|| Uid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                 sendRequest.setEnabled(false);
             }
          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      });


        // to chcek whether the user is already friend or not
        databaseRef.child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Friends").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()&& dataSnapshot.hasChild(Uid)){
                    sendRequest.setEnabled(false);
                    deleteRequest.setEnabled(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        sendRequest.setOnClickListener(this);
        deleteRequest.setOnClickListener(this);

    }


    //to show image name etc.
    private void retrieve(String Uid) {

        databaseRef.child("User").child(Uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if (((dataSnapshot.exists()) && (dataSnapshot.hasChild("Name")) && dataSnapshot.hasChild("Image"))) {
                    String mName = dataSnapshot.child("Name").getValue().toString();
                    String mStatus = dataSnapshot.child("Status").getValue().toString();
                    String mImage = dataSnapshot.child("Image").getValue().toString();
                    name.setText(mName);

                    try {
                        Picasso.get().load(mImage).into(image);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (((dataSnapshot.exists()) && (dataSnapshot.hasChild("Name")))) {
                    String mName = dataSnapshot.child("Name").getValue().toString();
                    String mStatus = dataSnapshot.child("Status").getValue().toString();
                    name.setText(mName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    // ---------------------------to send and delete the request------------------------------------//
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.Profilesendrequest: {

                databaseRef.child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("SendRequest").child(Uid).child("flag").setValue("yes")
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                sendRequest.setEnabled(false);
                                databaseRef.child("User").child(Uid).child("FriendRequest").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Flag").setValue("yes");
                                deleteRequest.setEnabled(true);
                            }
                        });
                break;
            }


            case R.id.Profiledeleterequest: {
                databaseRef.child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("SendRequest").child(Uid).setValue(null).
                        addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                databaseRef.child("User").child(Uid).child("FriendRequest").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(null);
                                sendRequest.setEnabled(true);
                            }
                        });

               break;}

        }
    }
}
