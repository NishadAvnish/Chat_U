package com.example.avnish.whatsapp_clone.chat_Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.avnish.whatsapp_clone.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Group_Chat extends AppCompatActivity {
    Intent i;
    String currentGroupName;
    Toolbar toolbar;
    EditText sendEdit;
    Button send_btn;
    DatabaseReference databaseRef;
    String currentUser;
    HashMap<String,String> map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group__chat);

        Initialize();

        if(i.getExtras()!=null){
            currentGroupName=i.getExtras().get("currentGroupName").toString();
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(currentGroupName);

          writeData();
        }


    }

    private void writeData() {



            send_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(sendEdit.getText().toString()!=null) {
                        Calendar calender = Calendar.getInstance();
                        String date = new SimpleDateFormat("EEE,MMM d,yyyy").format(calender.getTime());
                        String time = new SimpleDateFormat("h:mm a").format(calender.getTime());
                        databaseRef = FirebaseDatabase.getInstance().getReference("Group").child(currentGroupName);
                        map = new HashMap<>();
                        map.put("Msg", sendEdit.getText().toString().trim());
                        map.put("Date", date);
                        map.put("Time", time);
                        map.put("User", currentUser);}

                    databaseRef.push().setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            sendEdit.setText("");
                        }
                    });

                }
            });


        }



    private void Initialize() {
        i= getIntent();
        toolbar=findViewById(R.id.toolbar);
        sendEdit=findViewById(R.id.hello);
        send_btn=findViewById(R.id.sendbtn);
        currentUser=(FirebaseAuth.getInstance().getCurrentUser().getUid());



    }

}
