package ar.edu.itba.mygymapp;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.widget.SearchView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import ar.edu.itba.mygymapp.backend.App;
import ar.edu.itba.mygymapp.backend.models.Cycle;
import ar.edu.itba.mygymapp.backend.models.CycleExercise;
import ar.edu.itba.mygymapp.backend.models.Routine;
import ar.edu.itba.mygymapp.backend.repository.Resource;
import ar.edu.itba.mygymapp.backend.repository.Status;
import ar.edu.itba.mygymapp.backend.store.RoutineStore;
import ar.edu.itba.mygymapp.databinding.ActivityMainBinding;
import ar.edu.itba.mygymapp.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "AppBar";
    private App app;
    private AppBarConfiguration mAppBarConfiguration, tAppBarConfiguration;
    private ActivityMainBinding binding;
    private SharedPreferences sp;
    private ImageView userImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (App) this.getApplication();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBar.toolbar);

        sp = getSharedPreferences("login", MODE_PRIVATE);
        if (!sp.getBoolean("logged", false)) {
            goToLogin();
        }

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        View headerView = navigationView.getHeaderView(0);
        userImageView = headerView.findViewById(R.id.userImageView);
        TextView headerText = headerView.findViewById(R.id.usernameHeader);


        Glide.with(this).asBitmap().load(app.getUserRepository().getUser().getAvatarUrl()).placeholder(R.drawable.avatar).into(userImageView);
        headerText.setText(app.getUserRepository().getUser().getUsername());
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_myroutines, R.id.nav_favourites, R.id.nav_profile)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        binding.logoutBtn.setOnClickListener(view -> {
            logOut();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItem optionsItem = menu.findItem(R.id.action_filter);
        optionsItem.setVisible(false);
        searchItem.setVisible(false);

        MenuItem urlItem = menu.findItem(R.id.action_url);
        urlItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                showUrlDialog();
                return true;
            }
        });
        urlItem.setVisible(false);

        MenuItem scanQrItem = menu.findItem(R.id.action_scan_qr);
        scanQrItem.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    private void showUrlDialog() {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.url_layout);

        EditText url = dialog.findViewById(R.id.editUrl);
        MaterialButton playUrlBtn = dialog.findViewById(R.id.playUrlRoutine);
        playUrlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url.getText().toString()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("routineId", routines.get(holder.getAdapterPosition()).getId());
                intent.putExtra("routineImageUrl", "https://i.imgur.com/UHka8EZ.png");

                view.getContext().startActivity(intent);
            }
        });

        dialog.show();
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void goToRoutineActivity(View view) {

    }

    public void logOut(){


        app.getUserRepository().logout().observe(this, r -> {
            if (r.getStatus() == Status.SUCCESS) {
                sp.edit().putBoolean("logged",false).apply();
                goToLogin();
            } else {
                Resource.defaultResourceHandler(r);
            }
        });

    }

    private void goToLogin() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    public void setUserImage(Uri uri) {
        userImageView.setImageURI(uri);
    }
}