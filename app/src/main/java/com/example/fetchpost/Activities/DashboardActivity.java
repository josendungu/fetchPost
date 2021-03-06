package com.example.fetchpost.Activities;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fetchpost.Classes.Member;
import com.example.fetchpost.Fragments.MembersFragment;
import com.example.fetchpost.Fragments.MessageFragment;
import com.example.fetchpost.Fragments.ProfileFragment;
import com.example.fetchpost.R;
import com.google.android.material.navigation.NavigationView;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final  String USERNAME_REF = "com.example.fetchpost.USERNAME_REF";

    private Context mContext;
    private RecyclerView mRecyclerItems;
    private DrawerLayout drawerLayout;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        TextView tvUsername = (TextView) findViewById(R.id.username);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBarLayout);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        String logged_username = getIntent().getStringExtra(USERNAME_REF);
        Member logged_member = new Member(logged_username);
        logged_member.setLoggedData();
        String welcome_text = "Welcome " + logged_member.getUsername().toUpperCase();
        tvUsername.setText(welcome_text);

        NavigationView navigationView = findViewById(R.id.nav_viewer);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle((Activity) mContext, drawerLayout, toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MembersFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_members);
        }

    }



    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

        }
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.nav_members:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MembersFragment()).commit();
                break;
            case R.id.nav_message:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MessageFragment()).commit();
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
                break;
            case R.id.nav_chat:
                Toast.makeText(mContext, "Chat", Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}
