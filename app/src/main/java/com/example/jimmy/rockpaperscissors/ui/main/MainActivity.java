package com.example.jimmy.rockpaperscissors.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import com.example.jimmy.rockpaperscissors.R;
import com.example.jimmy.rockpaperscissors.TabAdapter;
import com.example.jimmy.rockpaperscissors.WeaponViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
    public static ViewPager2 viewPager;
    private TabAdapter tabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        WeaponViewModel weaponsModel = new ViewModelProvider(this).get(WeaponViewModel.class);
        weaponsModel.deleteAll();
        setContentView(R.layout.activity_main);
        Toolbar mainToolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(mainToolbar);

        // setup tabs
        viewPager = findViewById(R.id.viewPager);
        tabAdapter = new TabAdapter(this);
        viewPager.setAdapter(tabAdapter);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        new TabLayoutMediator(tabLayout, viewPager,((tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Play");
                    break;
                case 1:
                    tab.setText("Weapons");
                    break;
                default:
                    tab.setText("Tab " + position);
            }
        })).attach();
    }
}
