package com.example.haitr.deliapp.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.haitr.deliapp.Fragment.ChatFragment;
import com.example.haitr.deliapp.Fragment.ContactFragment;
import com.example.haitr.deliapp.Fragment.SettingFragment;
import com.example.haitr.deliapp.Fragment.ShareLocationFragment;

import java.util.Locale;

/**
 * Created by Dr.h3cker on 14/03/2015.
 */
public class PageAdapter extends FragmentPagerAdapter {
    public PageAdapter(FragmentManager fm) {
        super(fm);
    }

    //Swipe
    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                ChatFragment chatFragment = new ChatFragment();
                return chatFragment;
            case 1:
                ContactFragment contactFragment = new ContactFragment();
                return contactFragment;
            case 2:
                ShareLocationFragment shareLocationFragment = new ShareLocationFragment();
                return shareLocationFragment;
            case 3:
                SettingFragment settingFragment = new SettingFragment();
                return settingFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }//set the number of tabs

    //Title
    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return "Chat";
            case 1:
                return "Contact";
            case 2:
                return "Wall";
            case 3:
                return "Setting";
        }
        return null;
    }


}
