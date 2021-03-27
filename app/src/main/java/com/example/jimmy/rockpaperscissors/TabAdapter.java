package com.example.jimmy.rockpaperscissors;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.jimmy.rockpaperscissors.ui.main.GameFragment;
import com.example.jimmy.rockpaperscissors.ui.main.AboutFragment;
import com.example.jimmy.rockpaperscissors.ui.main.WeaponsFragment;

public class TabAdapter extends FragmentStateAdapter {
    public TabAdapter(AppCompatActivity fragment)
    {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new GameFragment();
            case 1:
                return new WeaponsFragment();
            case 2:
                return new AboutFragment();
            default: return null;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
