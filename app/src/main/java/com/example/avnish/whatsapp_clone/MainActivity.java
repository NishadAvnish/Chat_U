package com.example.avnish.whatsapp_clone;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.avnish.whatsapp_clone.Login.LoginActivity;
import com.example.avnish.whatsapp_clone.fragment.greenAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewPager myViewPager;
    TabLayout tabLayout;
    FirebaseUser currentUser;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialization
        {
            toolbar = findViewById(R.id.toolbar);
            tabLayout = findViewById(R.id.tablayout);
            myViewPager = findViewById(R.id.viewpager);
            databaseReference=FirebaseDatabase.getInstance().getReference();

        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Whatsapp");

        greenAdapter greenAdapter= new greenAdapter(getSupportFragmentManager());

        myViewPager.setAdapter(greenAdapter);

        tabLayout.setupWithViewPager(myViewPager);


    }

    @Override
    protected void onStart() {
        currentUser=(FirebaseAuth.getInstance()).getCurrentUser();
        if(currentUser==null){
            Intent intent = new Intent(this,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

        }
        super.onStart();
    }







    ////MENU INFLATOR
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu,menu);

       return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {

            case R.id.setting: {Intent intent = new Intent(MainActivity.this, Setting_Activity.class);
                                 startActivity(intent);
                                 break;
            }

            case R.id.menufindfriend: {
            }

            case R.id.menulogout:{mAuth.signOut();
                                 Intent intent= new Intent(MainActivity.this,LoginActivity.class);
                                 intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                 startActivity(intent);
                                 finish();}




            case R.id.creategroup: {
                AlertDialog.Builder builder= new AlertDialog.Builder(this);
                final EditText groupName=new EditText(this);
                builder.setView(groupName)
                        .setCancelable(true)
                        .setTitle("Enter the group name");
                builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String groupName1= groupName.getText().toString();
                        databaseReference.child("Group").child(groupName1).setValue("").addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this,"group created",Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();

                            }
                        });
                    }
                });
                alertDialog= builder.create();
                alertDialog.show();

            }




        }
        return true;
        }
}

