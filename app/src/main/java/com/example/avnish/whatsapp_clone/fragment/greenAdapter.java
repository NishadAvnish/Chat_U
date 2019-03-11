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

            case 1: RequestList requestList = new RequestList();
                return requestList;

            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0: return "CHAT";
            case 1: return  "REQUEST";
            default: return null;
        }
    }
}
