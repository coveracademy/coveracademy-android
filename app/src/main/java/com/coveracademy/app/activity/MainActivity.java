package com.coveracademy.app.activity;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.coveracademy.api.model.User;
import com.coveracademy.app.R;
import com.coveracademy.app.fragment.AuditionsFragment;
import com.coveracademy.app.fragment.ContestsFragment;
import com.coveracademy.app.util.MediaUtils;
import com.coveracademy.app.util.component.ConfirmDialog;
import com.facebook.login.LoginManager;

import org.jdeferred.DoneCallback;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends CoverAcademyActivity implements NavigationView.OnNavigationItemSelectedListener {

  private MainActivity instance;
  private DrawerToggle drawerToggle;
  private View drawerHeaderView;

  private User user;

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
    ImageView avatarView = ButterKnife.findById(drawerHeaderView, R.id.picture);
    MediaUtils.setPicture(this, user, avatarView);

    TextView nameView = ButterKnife.findById(drawerHeaderView, R.id.name);
    nameView.setText(user.getFirstName());

    navigationView.getMenu().setGroupVisible(R.id.items, true);
    navigationView.getMenu().setGroupVisible(R.id.extra, true);
    navigationView.getMenu().setGroupVisible(R.id.others, true);
  }

  private void setupToolbar() {
    setSupportActionBar(toolbar);
    setTitle(getString(R.string.activity_title_main));
  }

  private void setupTabs() {
    TabsAdapter tabsAdapter = new TabsAdapter(getSupportFragmentManager());
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

  @Override
  public void onConfigurationChanged(Configuration config) {
    super.onConfigurationChanged(config);
    drawerToggle.onConfigurationChanged(config);
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    switch(item.getItemId()) {
      case R.id.edit_profile:
        onEditProfileClick();
        break;
      case R.id.share:
        onShareClick();
        break;
      case R.id.rate_us:
        onRateUsClick();
        break;
      case R.id.contact_us:
        onContactUsClick();
        break;
      case R.id.settings:
        onSettingsClick();
        break;
      case R.id.logout:
        onLogoutClick();
        break;
    }
    return true;
  }

  private void onEditProfileClick() {

  }

  private void onShareClick() {

  }

  private void onRateUsClick() {

  }

  private void onContactUsClick() {

  }

  private void onSettingsClick() {

  }

  private void onLogoutClick() {
    ConfirmDialog logoutConfirmation = new ConfirmDialog(this, getString(R.string.activity_main_dialog_logout_title), getString(R.string.activity_main_dialog_logout_message));
    logoutConfirmation.setOnPositiveClickListener(new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        logout();
      }
    });
    logoutConfirmation.show();
  }

  private void logout() {
    remoteService.getUserService().logout();
    LoginManager.getInstance().logOut();

    Intent intent = new Intent(this, SplashActivity.class);
    startActivity(intent);
    finish();
  }

  private void loadContent() {
    remoteService.getUserService().getAuthenticatedUser().then(new DoneCallback<User>() {
      @Override
      public void onDone(User user) {
        instance.user = user;
        application.setUser(user);
        setupDrawerContent();
        setupTabs();
      }
    });
  }

  private class TabsAdapter extends FragmentPagerAdapter {

    private String[] titles;
    private Fragment[] contents;

    private TabsAdapter(FragmentManager fragmentManager) {
      super(fragmentManager);
      titles = new String[] {getString(R.string.activity_main_tab_auditions), getString(R.string.activity_main_tab_contests)};
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