package com.example.haitr.deliapp.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.haitr.deliapp.Class.UserSession;
import com.example.haitr.deliapp.Fragment.ChatFragment;
import com.example.haitr.deliapp.Fragment.ContactFragment;
import com.example.haitr.deliapp.Fragment.SettingFragment;
import com.example.haitr.deliapp.Fragment.ShareLocationFragment;
import com.example.haitr.deliapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String username;
    private UserSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoadUi();
    }

    public void LoadUi() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        session = new UserSession(getApplicationContext());
        if (session.checkLogin()) finish();
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        username = user.get(UserSession.KEY_NAME);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ContactFragment(), "CONTACT");
        adapter.addFragment(new ChatFragment(), "CHAT");
        adapter.addFragment(new ShareLocationFragment(), "WALL");
        adapter.addFragment(new SettingFragment(), "SETTING");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Adding our menu to toolbar
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //int id = item.get();
        session = new UserSession(getApplicationContext());
        switch (item.getItemId()) {

            case R.id.menuLogout:
                Toast.makeText(MainActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                session.logoutUser();
                break;
            case R.id.menuShare:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
