package com.example.avnish.whatsapp_clone;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.avnish.whatsapp_clone.Login.LoginActivity;
import com.example.avnish.whatsapp_clone.fragment.greenAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewPager myViewPager;
    TabLayout tabLayout;
    FirebaseUser currentUser;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.toolbar);
        tabLayout=findViewById(R.id.tablayout);
        myViewPager=findViewById(R.id.viewpager);


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
                                 finish();
            }




        }
        return true;
        }
}

