package com.example.jimmy.rockpaperscissors.ui.main;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jimmy.rockpaperscissors.Human;
import com.example.jimmy.rockpaperscissors.Match;
import com.example.jimmy.rockpaperscissors.Player;
import com.example.jimmy.rockpaperscissors.R;
import com.example.jimmy.rockpaperscissors.Robot;
import com.example.jimmy.rockpaperscissors.Weapon;
import com.example.jimmy.rockpaperscissors.WeaponStrategy;
import com.example.jimmy.rockpaperscissors.WeaponViewModel;
import com.github.andreilisun.circular_layout.CircularLayout;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class GameFragment extends Fragment {
    Match match;
    CircularLayout circularLayout;
    View weaponView;
    Spinner robotStrat;
    ArrayAdapter<CharSequence> arrayAdapter; // for strategy spinner
    TextView playerSelected; // ui element of the selected weapon
    TextView opponentSelected;
    TextView explainText;
    TextView winnerText;
    BitmapDrawable bitmapDrawable;
    Bitmap bitmap;
    HashMap<String,Integer> weaponPosition; // track weapon indices in the layout
    private WeaponViewModel weaponsModel;
    static final String QUALIFIER = "com.example.jimmy.rockpaperscissors.";

    public GameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        weaponsModel = new ViewModelProvider(requireActivity()).get(WeaponViewModel.class);
        weaponsModel.init();
        match = new Match(weaponsModel);
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @SuppressLint("InflateParams")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        winnerText = getView().findViewById(R.id.winner_text);
        explainText = getView().findViewById(R.id.explain_text);

        circularLayout = view.findViewById(R.id.circular_layout);
        circularLayout.setCapacity(Player.MAX_HANDS);

        // setup spinner for CPU's strategies
        arrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.strategies_array, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        robotStrat = view.findViewById(R.id.strategy_spinner);
        robotStrat.setAdapter(arrayAdapter);
        robotStrat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    match.setWeaponStrategy((WeaponStrategy) Class.forName(QUALIFIER + parent.getItemAtPosition(position) + "Strategy").getConstructor().newInstance());
                } catch (IllegalAccessException | ClassNotFoundException | java.lang.InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        weaponsModel.getAllWeapons().observe(getViewLifecycleOwner(), list -> {
            weaponPosition = new HashMap<>();
            circularLayout.removeAllViews();
            Player.MAX_HANDS = list.size();
            for (Weapon weapon : list) // add weapons to radial
            {
                addWeaponToRadial(weapon);
            }
        });

        Button startButton = view.findViewById(R.id.button2);
        startButton.setOnClickListener((View v) -> {
            match.startRound();
            updateUI();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateUI() // update UI to reflect weapon choices and match outcome
    {
        Human player = match.getPlayer();
        Robot opponent = match.getOpponent();

        if (opponentSelected != null) // de-highlight opponent's previous selection
            opponentSelected.getCompoundDrawables()[3].clearColorFilter();

        opponentSelected = circularLayout.getChildAt(weaponPosition.get(opponent.getWeapon().getWeaponName())).findViewById(R.id.weapon_text);
        updateColours();

        switch(match.getOutcome()) {
            case TIE:
                winnerText.setText(R.string.tie_message);
                explainText.setText(getString(R.string.round_tie_message, player.getWeapon().getWeaponName()));
                break;
            case WIN:
                winnerText.setText(R.string.win_message);
                explainText.setText(getString(R.string.round_end_message, player.getWeapon().getWeaponName(), opponent.getWeapon().getWeaponName()));
                break;
            case LOSS:
                winnerText.setText(R.string.lose_message);
                explainText.setText(getString(R.string.round_end_message, opponent.getWeapon().getWeaponName(), player.getWeapon().getWeaponName()));
                break;
            default:
                break;
        }

    }

    void addWeaponToRadial(Weapon weapon)
    {
        Human player = match.getPlayer();
        Robot opponent = match.getOpponent();

        try { // scale the weapon image to the number of weapons
            bitmap = Bitmap.createScaledBitmap(MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),
                    Uri.parse(weapon.getImgUri().toString())),
                    1500/(Player.MAX_HANDS),
                    1200/(Player.MAX_HANDS),
                    false);
            bitmapDrawable = new BitmapDrawable(getResources(),bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // format view
        weaponView = LayoutInflater.from(getContext()).inflate(R.layout.weapon_view, circularLayout, false);
        circularLayout.setCapacity(Player.MAX_HANDS);
        TextView weaponText = weaponView.findViewById(R.id.weapon_text);
        weaponText.setTextSize(90/(float)Player.MAX_HANDS);
        weaponText.setText(weapon.getWeaponName());
        weaponText.setCompoundDrawablesWithIntrinsicBounds(null, null, null, bitmapDrawable);

        // highlight weapon when selected
        weaponView.setOnClickListener((View textView) -> {
            if (playerSelected != null)
                playerSelected.getCompoundDrawables()[3].clearColorFilter();
            player.setWeapon(weapon);
            playerSelected = (TextView)textView;
            updateColours();
        });

        circularLayout.addView(weaponView);
        weaponPosition.put(weapon.getWeaponName(),circularLayout.indexOfChild(weaponView));

        // highlight the default weapon to start
        if (weapon.getWeaponName().equals(Player.DEFAULT_WEAPON_NAME)) {
            player.setWeapon(weapon);
            opponent.setWeapon(weapon);
            playerSelected = circularLayout.getChildAt(weaponPosition.get(player.getWeapon().getWeaponName())).findViewById(R.id.weapon_text);
            updateColours();
        }
    }

    private void updateColours()
    {
        if (opponentSelected == playerSelected)
            opponentSelected.getCompoundDrawables()[3].setColorFilter(new PorterDuffColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN));
        else
        {
            if (opponentSelected != null)
                opponentSelected.getCompoundDrawables()[3].setColorFilter(new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.SRC_IN));
            if (playerSelected != null)
                playerSelected.getCompoundDrawables()[3].setColorFilter(new PorterDuffColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN));
        }
    }
}