package com.geektech.mynote;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.geektech.mynote.databinding.ActivityMainBinding;
import com.geektech.mynote.utils.PrefHelper;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            navController.navigate(R.id.authFragment);
            return;
        }
        if (!PrefHelper.getOnBoardIsShown()) {
            navController.navigate(R.id.onBoardFragment);
            return;
        }

        binding.appBarMain.fab.setOnClickListener(view -> {
            navController.navigate(R.id.noteFragment);
        });
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationChangedListener(navController);
    }

    private void navigationChangedListener(NavController navController) {
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (controller.getGraph().getStartDestination() == destination.getId()) {
                binding.appBarMain.fab.show();
            } else if (destination.getId() == R.id.onBoardFragment) {
                binding.appBarMain.fab.hide();
            } else {
                binding.appBarMain.fab.hide();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}