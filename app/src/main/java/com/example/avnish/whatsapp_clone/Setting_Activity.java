package com.example.avnish.whatsapp_clone;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

public class Setting_Activity extends AppCompatActivity implements View.OnClickListener{
    EditText name,status;
    Button update;
    ImageView face,takeimage;
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

    public void takeImage(View view){
        ViewGroup viewGroup=findViewById(android.R.id.content);
        View view1= LayoutInflater.from(this).inflate(R.layout.custom_alert,viewGroup,false);

        AlertDialog.Builder builder= new AlertDialog.Builder(Setting_Activity.this);

        builder.setView(view1);

        Button camera= (Button)view1.findViewById(R.id.camera);
        Button gallery=(Button)view1.findViewById(R.id.gallery);

        builder.setCancelable(true);


        AlertDialog alertDialog= builder.create();
        alertDialog.show();

        gallery.setOnClickListener(this);
        camera.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.gallery: startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), 1);
                               break;
            case R.id.camera: Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
               startActivityForResult(takePicture, 0);
               break;

        }



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //for gallery
        if(resultCode==RESULT_OK && data!=null) {

            Uri imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setAspectRatio(1, 1)
                    .setMinCropWindowSize(500, 500)
                    .start(this);
        }
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);

            }


    }



    public void initialize(){
       face=findViewById(R.id.image) ;
       status=findViewById(R.id.status);
       update=findViewById(R.id.update);
       name=findViewById(R.id.Name);
       takeimage=findViewById(R.id.takeimage);
       databaseRef=FirebaseDatabase.getInstance().getReference();
       mAuth=FirebaseAuth.getInstance();
       currentUserID= mAuth.getCurrentUser().getUid();
    }


}
