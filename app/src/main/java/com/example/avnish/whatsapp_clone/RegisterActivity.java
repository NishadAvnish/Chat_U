package com.example.avnish.whatsapp_clone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    EditText name,email,password;
    Button register;
    ProgressDialog progressBar;
    FirebaseAuth mauth;

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

        String Name =name.getText().toString();
        String Email=email.getText().toString();
        String Password=password.getText().toString();
        if(TextUtils.isEmpty(Name) ||TextUtils.isEmpty(Email)||TextUtils.isEmpty(Password)){
           Toast toast= Toast.makeText(this,"ALL FIELDS ARE REQUIRED",Toast.LENGTH_LONG);}

        else{
            progressBar.setTitle("CREATING ACCOUNT");
            progressBar.setMessage("wait until we are creating your account");
            progressBar.setCanceledOnTouchOutside(true);
            progressBar.show();

            mauth.createUserWithEmailAndPassword(Email,Password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                progressBar.dismiss();
                               Toast toast= Toast.makeText(RegisterActivity.this,"Account Successfully created",Toast.LENGTH_LONG);
                                Intent intent= new Intent(RegisterActivity.this,LoginActivity.class);
                                startActivity(intent);
                            }
                            else{
                                progressBar.dismiss();
                                String msg= task.getException().toString();
                               Toast toast= Toast.makeText(RegisterActivity.this,msg,Toast.LENGTH_LONG);
                            }
                        }
                    });
        }


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
