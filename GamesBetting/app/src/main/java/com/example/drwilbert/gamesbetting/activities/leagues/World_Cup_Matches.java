package com.example.drwilbert.gamesbetting.activities.leagues;

import android.content.Intent;
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
import com.example.drwilbert.gamesbetting.fragments.world_cup_fragments.WorlCup_MondayFragment;
import com.example.drwilbert.gamesbetting.fragments.world_cup_fragments.WorldCup_SaturdayFragment;
import com.example.drwilbert.gamesbetting.fragments.world_cup_fragments.WorldCup_SundayFragment;
import com.example.drwilbert.gamesbetting.fragments.world_cup_fragments.WorldCup_ThursdayFragment;
import com.example.drwilbert.gamesbetting.fragments.world_cup_fragments.WorldCup_TuesdayFragment;
import com.example.drwilbert.gamesbetting.fragments.world_cup_fragments.WorldCup_WednesdayFragment;


public class World_Cup_Matches extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    String email, league, client_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world__cup__matches);

        viewPager = (ViewPager) findViewById(R.id.viewpager_worldcup);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs_worldcup);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        WorlCup_MondayFragment monday = new WorlCup_MondayFragment();
        WorlCup_MondayFragment tuesday = new WorlCup_MondayFragment();
        WorlCup_MondayFragment wednesday = new WorlCup_MondayFragment();
        WorlCup_MondayFragment thursday = new WorlCup_MondayFragment();
        WorlCup_MondayFragment friday = new WorlCup_MondayFragment();
        WorlCup_MondayFragment saturday = new WorlCup_MondayFragment();
        WorlCup_MondayFragment sunday = new WorlCup_MondayFragment();

        Bundle bundle = new Bundle();
        Intent get_email = getIntent();
        email = get_email.getStringExtra("email");
        league = get_email.getStringExtra("league");
        client_id = get_email.getStringExtra("client_id");

        bundle.putString("email", email);
        bundle.putString("league",league);
        bundle.putString("client_id",client_id);

        monday.setArguments(bundle);
        tuesday.setArguments(bundle);
        wednesday.setArguments(bundle);
        thursday.setArguments(bundle);
        friday.setArguments(bundle);
        saturday.setArguments(bundle);
        sunday.setArguments(bundle);

        adapter.addFrag(monday, "Monday");
        adapter.addFrag(tuesday, "Tuesday");
        adapter.addFrag(wednesday, "Wednesday");
        adapter.addFrag(thursday, "Thursday");
        adapter.addFrag(friday, "Friday");
        adapter.addFrag(saturday, "Saturday");
        adapter.addFrag(sunday, "Sunday");

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