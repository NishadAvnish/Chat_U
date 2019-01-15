package com.example.avnish.whatsapp_clone;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.images.internal.ImageUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Setting_Activity extends AppCompatActivity {
    EditText name,status;
    Button update;
    ImageView face;
    DatabaseReference databaseRef;
    FirebaseAuth mAuth;
    String currentUserID;
   Integer count=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initialize();

        checkExistance();

    }

    private void checkExistance() {
        databaseRef.child("User").child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("Name")){
                    retrieve();
                }
                else feedData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void feedData() {

        databaseRef.child("User").child(currentUserID);

                    update.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if((TextUtils.isEmpty("name"))||(TextUtils.isEmpty("status"))){
                                Toast.makeText(Setting_Activity.this,"Enter both Name and status field",Toast.LENGTH_SHORT);

                            }
                            else{
                               String Username= name.getText().toString();
                                String Userstatus=status.getText().toString();
                                HashMap<String,String> map=new HashMap<>(2);
                                map.put("Name",Username);
                                map.put("Status",Userstatus);
                                databaseRef.child("User").child(currentUserID).setValue(map);
                            }

                        }
                    });
                }



    private void retrieve(){

        databaseRef.child("User").child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if(((dataSnapshot.exists())&& (dataSnapshot.hasChild("Name"))&&(dataSnapshot.hasChild("Status")))){
                    String mName=dataSnapshot.child("Name").getValue().toString();
                    String mStatus=dataSnapshot.child("Status").getValue().toString();
                    name.setText(mName);
                    status.setText(mStatus);

                }

                else if(((dataSnapshot.exists())&& (dataSnapshot.hasChild("Name")))){
                    String mName=dataSnapshot.child("Name").getValue().toString();
                    name.setText(mName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    public void initialize(){
       face=findViewById(R.id.image) ;
       status=findViewById(R.id.status);
       update=findViewById(R.id.update);
       name=findViewById(R.id.Name);
       databaseRef=FirebaseDatabase.getInstance().getReference();
       mAuth=FirebaseAuth.getInstance();
       currentUserID= mAuth.getCurrentUser().getUid();
    }
}
