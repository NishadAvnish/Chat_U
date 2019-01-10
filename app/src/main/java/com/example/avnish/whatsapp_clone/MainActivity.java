package com.example.avnish.whatsapp_clone;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.avnish.whatsapp_clone.fragment.greenAdapter;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewPager myViewPager;
    TabLayout tabLayout;
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
}
