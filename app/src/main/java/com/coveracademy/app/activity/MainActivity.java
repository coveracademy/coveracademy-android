package com.coveracademy.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.coveracademy.app.R;
import com.coveracademy.app.fragment.AuditionsFragment;
import com.coveracademy.app.fragment.ContestsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

  private TabsAdapter tabsAdapter;

  @BindView(R.id.pager) ViewPager tabsPager;
  @BindView(R.id.tabs) TabLayout tabs;
  @BindView(R.id.toolbar) Toolbar toolbar;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    setupTabs();
    setSupportActionBar(toolbar);
  }

  private void setupTabs() {
    tabsAdapter = new TabsAdapter(getSupportFragmentManager());
    tabsPager.setAdapter(tabsAdapter);
    tabs.setupWithViewPager(tabsPager);
    tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        tabsPager.setCurrentItem(tab.getPosition());
      }

      @Override
      public void onTabUnselected(TabLayout.Tab tab) {

      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {

      }
    });
  }

  private class TabsAdapter extends FragmentPagerAdapter {

    private String[] titles;
    private Fragment[] contents;

    public TabsAdapter(FragmentManager fragmentManager) {
      super(fragmentManager);
      titles = new String[] {getString(R.string.activity_main_auditions_title), getString(R.string.activity_main_contests_title)};
      contents = new Fragment[] {new AuditionsFragment(), new ContestsFragment()};
    }

    @Override
    public Fragment getItem(int position) {
      return contents[position];
    }

    @Override
    public int getCount() {
      return contents.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return titles[position];
    }
  }
}