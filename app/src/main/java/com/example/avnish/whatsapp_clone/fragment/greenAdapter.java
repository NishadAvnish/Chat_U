package com.example.avnish.whatsapp_clone.fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class greenAdapter extends FragmentStatePagerAdapter {
    public greenAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int i) {
        switch(i){
            case 0: chatfragment chatFragment=new chatfragment();
                     return chatFragment;

            case 1:groupFragment groupFragment= new groupFragment();
                    return groupFragment;

            case 2: Request request = new Request();
                return request;

            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0: return "CHAT";
            case 1: return "GROUP";
            case 2: return  "REQUEST";
            default: return null;
        }
    }
}
