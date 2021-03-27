package com.example.jimmy.rockpaperscissors.ui.main;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jimmy.rockpaperscissors.NewWeaponDialogFragment;
import com.example.jimmy.rockpaperscissors.Player;
import com.example.jimmy.rockpaperscissors.WeaponViewAdapter;
import com.example.jimmy.rockpaperscissors.WeaponViewModel;
import com.example.jimmy.rockpaperscissors.R;
import com.example.jimmy.rockpaperscissors.RandomStrategy;
import com.example.jimmy.rockpaperscissors.Robot;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeaponsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeaponsFragment extends Fragment {
    private RecyclerView recyclerView;
    private WeaponViewAdapter weaponViewAdapter;
    int numPlayers = 0;


    public WeaponsFragment() {
        // Required empty public constructor
    }

    public static WeaponsFragment newInstance() {
        return new WeaponsFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weapons, container, false);
        Context context = view.getContext();

        // setup weapons
        WeaponViewModel weaponsModel = new ViewModelProvider(requireActivity()).get(WeaponViewModel.class);
        weaponsModel.getAllWeapons().observe(getViewLifecycleOwner(), list -> weaponViewAdapter.submitList(list));
        weaponViewAdapter = new WeaponViewAdapter(weaponsModel);

        // Set the adapter
        recyclerView = view.findViewById(R.id.weapon_recycler_view);
        recyclerView.setAdapter(weaponViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        // Setup new weapon dialog
        FloatingActionButton playerFab = view.findViewById(R.id.addWeaponFab);
        playerFab.setOnClickListener(fragView -> {
            DialogFragment dialog = new NewWeaponDialogFragment();
            Bundle args = new Bundle();
            args.putInt("ordinal",weaponsModel.getHighestOrdinal());
            dialog.setArguments(args);
            dialog.show(getParentFragmentManager(), "NewWeaponDialogFragment");
        });

        new ItemTouchHelper((new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                weaponsModel.deleteWeapon(weaponViewAdapter.getWeaponAtIndex(viewHolder.getAdapterPosition()));
                Player.MAX_HANDS--;
                Toast.makeText(view.getContext(),R.string.removed_player, Toast.LENGTH_SHORT).show();
            }

            @Override // make rock, paper, and scissors un-removable
            public int getSwipeDirs(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                if(weaponViewAdapter.getWeaponAtIndex(viewHolder.getAdapterPosition()).getOrdinal() <= 3){
                    return 0;
                }
                return super.getSwipeDirs(recyclerView, viewHolder);
            }
        })).attachToRecyclerView(recyclerView);

        return view;
    }



}