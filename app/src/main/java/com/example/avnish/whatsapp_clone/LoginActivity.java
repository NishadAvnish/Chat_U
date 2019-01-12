package com.example.avnish.whatsapp_clone;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    FirebaseUser currentUser;
    TextView register, forgetpassword;
    EditText email, password;
    Toolbar toolbar;
    Button login, phoneLogin;
    FirebaseAuth mAuth;
    ProgressDialog progressBar;
    String Email;
    Integer flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialize();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("LOGIN");


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });

        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

        @Override
        protected void onStart () {
            if (currentUser != null) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
            super.onStart();
        }


//LOGIN TO SAVE AND LOGIN EMAIL AND PASSWORD
        public void loginbutton(View view){

            Email = email.getText().toString();
            String Password = password.getText().toString();
            if (TextUtils.isEmpty(Email) || TextUtils.isEmpty(Password)) {

                Toast.makeText(this, "ALL FIELDS ARE REQUIRED", Toast.LENGTH_LONG).show();
            } else {
                progressBar.setTitle("LOGIN....");
                progressBar.setMessage("wait until we are LOGIN your account");
                progressBar.setCanceledOnTouchOutside(true);
                progressBar.show();

                mAuth.signInWithEmailAndPassword(Email, Password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    progressBar.dismiss();
                                    Toast.makeText(LoginActivity.this, "Account Login created", Toast.LENGTH_LONG).show();
                                    currentUser = mAuth.getCurrentUser();
                                    if(currentUser.isEmailVerified()){
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);}
                                } else {
                                    progressBar.dismiss();
                                    String msg = task.getException().toString();
                                    Toast toast = Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_LONG);
                                    toast.show();
                                }
                            }
                        });
            }


        }

    private void emailVerification() {
        if(flag==0){
        currentUser.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            AlertDialog.Builder alertdialog= new AlertDialog.Builder(LoginActivity.this);
                            alertdialog.setMessage("VERIFY YOUR EMAIL ADDRESS")
                            .setCancelable(true);
                            AlertDialog alertDialog= alertdialog.create();
                            alertDialog.show();
                            flag=1;

                        }
                    }
                });}
    }


    //INITIALIZE ALL THE VARIABLE
        private void initialize(){

            email = findViewById(R.id.email);
            password = findViewById(R.id.password);
            toolbar = findViewById(R.id.toolbar);
            register = findViewById(R.id.register);
            forgetpassword = findViewById(R.id.forgetpassword);
            login = findViewById(R.id.login);
            phoneLogin = findViewById(R.id.loginusingphone);
            mAuth = FirebaseAuth.getInstance();
            progressBar = new ProgressDialog(this);

        }

}