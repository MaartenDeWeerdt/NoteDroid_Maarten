package be.ehb.hellonavigationdrawer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import be.ehb.hellonavigationdrawer.R;
import be.ehb.hellonavigationdrawer.fragments.MainFragment;
import be.ehb.hellonavigationdrawer.fragments.NoteFragment;
import be.ehb.hellonavigationdrawer.fragments.SettingsFragment;
import be.ehb.hellonavigationdrawer.model.NoteDAO;
import be.ehb.hellonavigationdrawer.util.NoteAdapter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //backstack name, used for adding fragments to back button
    private final String FRAGMENT_BACKSTACK = "fragment_backstack";
    //reference to drawer (navigation)
    private DrawerLayout drawer;
    private NoteAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NoteDAO.INSTANCE.openConnection(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navigationHeader = navigationView.getHeaderView(0);

        getSupportFragmentManager().beginTransaction().add(R.id.container, new MainFragment()).commit();
    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        MainFragment mainFragment;
        SettingsFragment settingsFragment;

       switch (id)
       {
           case R.id.nav_main:
               mainFragment =  MainFragment.newInstance();
               getSupportFragmentManager().beginTransaction()
                       .replace(R.id.container, mainFragment)
                       .addToBackStack(FRAGMENT_BACKSTACK)
                       .commit();
               break;
           case R.id.nav_about:
               settingsFragment =  SettingsFragment.newInstance();
               getSupportFragmentManager().beginTransaction()
                       .replace(R.id.container, settingsFragment)
                       .addToBackStack(FRAGMENT_BACKSTACK)
                       .commit();
               break;
       }

        drawer.closeDrawer(Gravity.START);

        return true;
    }
}
