package com.example.drwilbert.gamesbetting.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import com.example.drwilbert.gamesbetting.R;
import com.example.drwilbert.gamesbetting.fragments.MondayFragment;
import com.example.drwilbert.gamesbetting.fragments.TuesdayFragment;
import com.example.drwilbert.gamesbetting.fragments.WednesdayFragment;
import com.example.drwilbert.gamesbetting.fragments.ThursdayFragment;
import com.example.drwilbert.gamesbetting.fragments.FridayFragment;
import com.example.drwilbert.gamesbetting.fragments.SaturdayFragment;
import com.example.drwilbert.gamesbetting.fragments.SundayFragment;


// Created By Wilbert Mhlanga
//Skype: Wilbert Mhlanga
//Linkedin: https://www.linkedin.com/in/wilbert-mhlanga/


public class GamesToBET extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_to_bet);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new MondayFragment(), "Monday");
        adapter.addFrag(new TuesdayFragment(), "Tuesday");
        adapter.addFrag(new WednesdayFragment(), "Wednesday");
        adapter.addFrag(new ThursdayFragment(), "Thursday");
        adapter.addFrag(new FridayFragment(), "Friday");
        adapter.addFrag(new SaturdayFragment(), "Saturday");
        adapter.addFrag(new SundayFragment(), "Sunday");

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

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}