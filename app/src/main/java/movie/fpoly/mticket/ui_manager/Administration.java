package movie.fpoly.mticket.ui_manager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import movie.fpoly.mticket.R;
import movie.fpoly.mticket.fragment_manager.BookingFragment;
import movie.fpoly.mticket.fragment_manager.CategoryFragment;
import movie.fpoly.mticket.fragment_manager.CinemaFragment;
import movie.fpoly.mticket.fragment_manager.MovieFragment;
import movie.fpoly.mticket.fragment_manager.RoomFragment;
import movie.fpoly.mticket.fragment_manager.ScheduleFragment;
import movie.fpoly.mticket.fragment_manager.UserFragment;
import movie.fpoly.mticket.ui.Login;

public class Administration extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administration);

        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Quản lý người dùng");
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            replaceFragment(new UserFragment());
            navigationView.setCheckedItem(R.id.nav_users);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_users) {
            replaceFragment(new UserFragment());
            toolbar.setTitle("Quản lý người dùng");
            navigationView.setCheckedItem(R.id.nav_users);
        } else if (id == R.id.nav_booking) {
            replaceFragment(new BookingFragment());
            toolbar.setTitle("Quản lý vé");
            navigationView.setCheckedItem(R.id.nav_booking);
        } else if (id == R.id.nav_movies) {
            replaceFragment(new MovieFragment());
            toolbar.setTitle("Quản lý phim");
            navigationView.setCheckedItem(R.id.nav_movies);
        } else if (id == R.id.nav_categories) {
            replaceFragment(new CategoryFragment());
            toolbar.setTitle("Quản lý thể loại");
            navigationView.setCheckedItem(R.id.nav_categories);
        } else if (id == R.id.nav_schedule) {
            replaceFragment(new ScheduleFragment());
            toolbar.setTitle("Quản lý lịch chiếu");
            navigationView.setCheckedItem(R.id.nav_schedule);
        } else if (id == R.id.nav_cinemas) {
            replaceFragment(new CinemaFragment());
            toolbar.setTitle("Quản lý rạp");
            navigationView.setCheckedItem(R.id.nav_cinemas);
        } else if (id == R.id.nav_room) {
            replaceFragment(new RoomFragment());
            toolbar.setTitle("Quản lý phòng chiếu");
            navigationView.setCheckedItem(R.id.nav_room);
        } else if (id == R.id.nav_logout) {
            Toast.makeText(this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Login.class));
            finish();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment_content_main, fragment).commit();
    }
}