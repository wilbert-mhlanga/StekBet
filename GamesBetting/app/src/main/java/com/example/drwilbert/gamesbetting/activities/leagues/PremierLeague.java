package com.example.drwilbert.gamesbetting.activities.leagues;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import com.example.drwilbert.gamesbetting.R;
import com.example.drwilbert.gamesbetting.fragments.premier_league_fragments.PL_TuesdayFragment;
import com.example.drwilbert.gamesbetting.fragments.premier_league_fragments.PL_WednesdayFragment;
import com.example.drwilbert.gamesbetting.fragments.premier_league_fragments.PL_ThursdayFragment;
import com.example.drwilbert.gamesbetting.fragments.premier_league_fragments.PL_FridayFragment;
import com.example.drwilbert.gamesbetting.fragments.premier_league_fragments.PL_SaturdayFragment;
import com.example.drwilbert.gamesbetting.fragments.premier_league_fragments.PL_SundayFragment;
import com.example.drwilbert.gamesbetting.fragments.premier_league_fragments.PL_MondayFragment;


public class PremierLeague extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    String email, league, client_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premier_league);

        viewPager = (ViewPager) findViewById(R.id.viewpager_premierleague);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs_premierleague);
        tabLayout.setupWithViewPager(viewPager);
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        PL_MondayFragment monday = new PL_MondayFragment();
        PL_TuesdayFragment tuesday = new PL_TuesdayFragment();
        PL_WednesdayFragment wednesday = new PL_WednesdayFragment();
        PL_ThursdayFragment thursday = new PL_ThursdayFragment();
        PL_FridayFragment friday = new PL_FridayFragment();
        PL_SaturdayFragment saturday = new PL_SaturdayFragment();
        PL_SundayFragment sunday = new PL_SundayFragment();

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
        //Log.e("The league is", league);
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