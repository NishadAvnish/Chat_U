package com.example.avnish.whatsapp_clone.Login;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.avnish.whatsapp_clone.Basic_Activity.Setting_Activity;
import com.example.avnish.whatsapp_clone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.IOException;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText name,email,password;
    Button register;
    ProgressDialog progressBar;
    FirebaseAuth mauth;
    FirebaseUser currentUser;
    DatabaseReference databaseRef;
    Toolbar toolbar;

    ImageView face,takeimage;
    StorageReference firebaseStorageref;
    String currentuserId;
    AlertDialog alertDialog;
    Uri resulturi;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initialize();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("REGISTER");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });

    }



    private void createAccount() {

       final String Name =name.getText().toString();
       final String Email=email.getText().toString();
       final String Password=password.getText().toString();

        if(TextUtils.isEmpty(Name) ||TextUtils.isEmpty(Email)||TextUtils.isEmpty(Password)){
           Toast toast= Toast.makeText(this,"ALL FIELDS ARE REQUIRED",Toast.LENGTH_LONG);}

        else{
            progressBar.setTitle("CREATING ACCOUNT");
            progressBar.setMessage("wait until we are creating your account");
            progressBar.setCanceledOnTouchOutside(true);
            progressBar.show();

            mauth.createUserWithEmailAndPassword(Email,Password)
                    .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){

                                progressBar.dismiss();
                                currentuserId= mauth.getCurrentUser().getUid();
                                databaseRef.child("User").child(currentuserId).setValue("");
                                Toast toast= Toast.makeText(RegisterActivity.this,"Account Successfully created",Toast.LENGTH_LONG);
                                toast.show();

                                final StorageReference fs=firebaseStorageref.child(currentuserId + ".jpg");
                                (fs).putFile(resulturi).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                        if(task.isSuccessful()){
                                            // final String downloadUrl=task.getResult().getMetadata().getReference().getDownloadUrl().toString();

                                            Toast.makeText(RegisterActivity.this,"Successfully uploaded",Toast.LENGTH_SHORT).show();
                                            fs.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    String Url=uri.toString();
                                                    databaseRef.child("User").child(currentuserId).child("Image").setValue(Url);
                                                    databaseRef.child("User").child(currentuserId).child("Name").setValue(name.getText().toString());
                                                    databaseRef.child("User").child(currentuserId).child("Uid").setValue(currentuserId);
                                                    progressDialog.dismiss();
                                                    login(Email,Password);
                                                }
                                            });
                                        } }
                                });
                            }
                            else{
                                progressBar.dismiss();
                                String msg= task.getException().toString();
                               Toast toast= Toast.makeText(RegisterActivity.this,msg,Toast.LENGTH_LONG);
                               toast.show();
                            }
                        }
                    });
        }


    }

    private void login(String email, String password){
        mauth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                currentUser=mauth.getCurrentUser();
                Intent i= new Intent(RegisterActivity.this,LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();

            }
        });
    }


    //emailverification
    private void emailVerification() {
        currentUser.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            AlertDialog.Builder alertdialog= new AlertDialog.Builder(RegisterActivity.this);
                            alertdialog.setMessage("VERIFY YOUR EMAIL ADDRESS")
                                    .setCancelable(true);
                            AlertDialog alertDialog= alertdialog.create();
                            alertDialog.show();

                        }
                    }
                });
    }



    // Use gallery and camera for profile image

    public void takeImage(View view){
        ViewGroup viewGroup=findViewById(android.R.id.content);
        View view1= LayoutInflater.from(this).inflate(R.layout.custom_alert,viewGroup,false);

        AlertDialog.Builder builder= new AlertDialog.Builder(RegisterActivity.this);

        builder.setView(view1);

        Button camera= (Button)view1.findViewById(R.id.camera);
        Button gallery=(Button)view1.findViewById(R.id.gallery);

        builder.setCancelable(true);


        alertDialog= builder.create();
        alertDialog.show();

        gallery.setOnClickListener(this);
        camera.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.gallery: startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), 1);
                return;
            case R.id.camera: Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, 0);
                return;

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
            alertDialog.cancel();
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode==RESULT_OK){
                resulturi=result.getUri();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resulturi);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //progress dialog
                progressDialog= new ProgressDialog(this);
                progressDialog.setMessage("wait until we work for you");
                progressDialog.show();

               face.setImageBitmap(bitmap);

                progressDialog.dismiss();
            }
        }}



    private void initialize() {
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        register=findViewById(R.id.register);
        progressBar=new ProgressDialog(this);
        mauth=FirebaseAuth.getInstance();
        databaseRef=FirebaseDatabase.getInstance().getReference();
        toolbar=findViewById(R.id.toolbar);


        takeimage=findViewById(R.id.takeimage);
        firebaseStorageref= FirebaseStorage.getInstance().getReference("profilepic");
        face=findViewById(R.id.image);
    }
}
