package com.example.avnish.whatsapp_clone;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    EditText name,email,password;
    Button register;
    ProgressDialog progressBar;
    FirebaseAuth mauth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initialize();
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
                                currentUser=mauth.getCurrentUser();
                                Toast toast= Toast.makeText(RegisterActivity.this,"Account Successfully created",Toast.LENGTH_LONG);
                                toast.show();
                                login(Email,Password);
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
                startActivity(i);

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



    private void initialize() {
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        register=findViewById(R.id.register);
        progressBar=new ProgressDialog(this);
        mauth=FirebaseAuth.getInstance();
    }
}
