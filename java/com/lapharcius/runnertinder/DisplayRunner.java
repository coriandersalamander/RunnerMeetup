package com.lapharcius.runnertinder;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import static com.lapharcius.runnertinder.FlingDetector.flingDirection.DIRECTION_LEFT;
import static com.lapharcius.runnertinder.FlingDetector.flingDirection.DIRECTION_RIGHT;

public class DisplayRunner extends AppCompatActivity
        implements FlingDetector.OnGestureDetected,
        NavigationView.OnNavigationItemSelectedListener
{

    GestureDetector g;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent i = getIntent();
        String userName = getPreferences(MODE_PRIVATE).getString(
                getResources().getString(R.string.userName),null);

        if ((Intent.ACTION_MAIN.equals(i.getAction()) &&
                userName == null))
        {
            Intent userInfoScreen = new Intent(getApplicationContext(), UserInfo.class);
            startActivity(userInfoScreen);
        }

        g = new GestureDetector(this, new FlingDetector(this));


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        openOrCreateDatabase("matches.db", 0, null);

        // Do query

    }

    @Override
    public void onSwipe(FlingDetector f) {
        if (FlingDetector.currentFlingDirection == DIRECTION_LEFT)
        {
            getNextProfile(this.getCurrentFocus());
        }
        else if (FlingDetector.currentFlingDirection == DIRECTION_RIGHT)
        {
            getPreviousProfile(this.getCurrentFocus());
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return g.onTouchEvent(event);
    }

    public void getPreviousProfile(View v)
    {
        Toast.makeText(getApplicationContext(), "Christine -- this will get the previous " +
                        "match",
                Toast.LENGTH_LONG).show();

    }

    public void getNextProfile(View v)
    {
        Toast.makeText(getApplicationContext(), "Christine -- this will get the next match",
                Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.edit_criteria) {
            Intent i = new Intent(this, MatchCriteria.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.i("LOGMESSAGE", "On Navigation Item Selected ");
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_UALogin) {


        } else if (id == R.id.nav_UALogout) {


        } else if (id == R.id.nav_deleteDB) {
            Log.i("LOGMESSAGE", "Delete ");
//            deleteDatabase("matches.db");
            Toast.makeText(getApplicationContext(), "All Entries Deleted!", Toast.LENGTH_LONG).show();


        } else if (id == R.id.nav_viewConnections) {
            Log.i("LOGMESSAGE" , "View Connections Pressed");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
