package com.coveracademy.app.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.coveracademy.app.R;
import com.coveracademy.app.fragment.AuditionsFragment;
import com.coveracademy.app.fragment.ContestsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends CoverAcademyActivity implements NavigationView.OnNavigationItemSelectedListener {

  private MainActivity instance;
  private DrawerToggle drawerToggle;
  private View drawerHeaderView;
  private TabsAdapter tabsAdapter;

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;
  @BindView(R.id.navigation_view) NavigationView navigationView;
  @BindView(R.id.pager) ViewPager tabsPager;
  @BindView(R.id.tabs) TabLayout tabs;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    instance = this;

    setupToolbar();
    setupDrawer();
    loadContent();
  }

  private void setupDrawer() {
    drawerToggle = new DrawerToggle();
    drawerLayout.addDrawerListener(drawerToggle);
    drawerToggle.syncState();
    drawerHeaderView = navigationView.inflateHeaderView(R.layout.drawer_header);
    navigationView.setNavigationItemSelectedListener(this);
  }

  private void setupDrawerContent() {
    navigationView.getMenu().setGroupVisible(R.id.items, true);
    navigationView.getMenu().setGroupVisible(R.id.extra, true);
    navigationView.getMenu().setGroupVisible(R.id.others, true);
  }

  private void setupToolbar() {
    setSupportActionBar(toolbar);
    setTitle(getString(R.string.title_activity_main));
  }

  private void setupTabs() {
    tabsAdapter = new TabsAdapter(getSupportFragmentManager());
    tabsPager.setAdapter(tabsAdapter);
    tabs.setupWithViewPager(tabsPager);
    tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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

  private void loadContent() {
    setupDrawerContent();
    setupTabs();
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    return false;
  }

  @Override
  public void onConfigurationChanged(Configuration config) {
    super.onConfigurationChanged(config);
    drawerToggle.onConfigurationChanged(config);
  }

  private class TabsAdapter extends FragmentPagerAdapter {

    private String[] titles;
    private Fragment[] contents;

    private TabsAdapter(FragmentManager fragmentManager) {
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

  private class DrawerToggle extends ActionBarDrawerToggle {

    private DrawerToggle() {
      super(instance, drawerLayout, toolbar, R.string.drawer_opened, R.string.drawer_closed);
    }

    @Override
    public void onDrawerOpened(View view) {
      super.onDrawerOpened(view);
      invalidateOptionsMenu();
      syncState();
    }

    @Override
    public void onDrawerClosed(View view) {
      super.onDrawerClosed(view);
      invalidateOptionsMenu();
      syncState();
    }
  }
}