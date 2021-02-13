package com.example.jimmy.rockpaperscissors.ui.main;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import com.example.jimmy.rockpaperscissors.Human;
import com.example.jimmy.rockpaperscissors.Player;
import com.example.jimmy.rockpaperscissors.R;
import com.example.jimmy.rockpaperscissors.Robot;
import com.example.jimmy.rockpaperscissors.WeaponStrategy;
import com.github.andreilisun.circular_layout.CircularLayout;

import org.w3c.dom.Text;

public class GameFragment extends Fragment {
    Human player;
    Robot opponent;
    CircularLayout circularLayout;
    View weaponView;
    Spinner robotStrat;
    ArrayAdapter<CharSequence> arrayAdapter; // for strategy spinner
    TextView playerSelected; // ui element of the selected weapon
    TextView opponentSelected;
    TextView explainText;
    TextView winnerText;
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
        arrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.strategies_array, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        player = new Human();
        opponent = new Robot();

        // setup spinner for CPU's strategies
        robotStrat = view.findViewById(R.id.strategy_spinner);
        robotStrat.setAdapter(arrayAdapter);
        robotStrat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    opponent.setWeaponStrategy((WeaponStrategy) Class.forName(QUALIFIER + parent.getItemAtPosition(position) + "Strategy").newInstance());
                } catch (IllegalAccessException | ClassNotFoundException | java.lang.InstantiationException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        for (Player.Hand weapon : Player.Hand.values()) // add weapons to radial
        {
            weaponView = LayoutInflater.from(getContext()).inflate(R.layout.weapon_view, null);
            TextView weaponText = weaponView.findViewById(R.id.weapon_text);
            weaponText.setTextSize(100/(float)Player.MAX_HANDS);
            weaponText.setText(weapon.toString());
            weaponText.setCompoundDrawablesWithIntrinsicBounds(null, null, null, ContextCompat.getDrawable(getActivity(), weapon.imgID));

            weaponView.setOnClickListener((View textView) -> {
                if (playerSelected != null)
                    playerSelected.getCompoundDrawables()[3].clearColorFilter();
                player.setChoice(weapon.getWeapon());
                weaponText.getCompoundDrawables()[3].setColorFilter(new PorterDuffColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN));
                playerSelected = (TextView)textView;
            });
            circularLayout.addView(weaponView);
        }

        // highlight the default weapon to start
        playerSelected = circularLayout.getChildAt(player.getChoice().getWeapon()).findViewById(R.id.weapon_text);
        playerSelected.getCompoundDrawables()[3].setColorFilter(new PorterDuffColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN));

        Button startButton = view.findViewById(R.id.button2);
        startButton.setOnClickListener((View v) -> startRound());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void startRound()
    {

        if (opponentSelected != null)
        {
            opponentSelected.getCompoundDrawables()[3].clearColorFilter();
            if (opponentSelected == playerSelected) // retain highlight of player's selection
                opponentSelected.getCompoundDrawables()[3].setColorFilter(new PorterDuffColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN));
        }

        opponent.makeChoice();
        opponentSelected = circularLayout.getChildAt(opponent.getChoice().getWeapon()).findViewById(R.id.weapon_text);

        if (opponentSelected == playerSelected)
            opponentSelected.getCompoundDrawables()[3].setColorFilter(new PorterDuffColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN));
        else
            opponentSelected.getCompoundDrawables()[3].setColorFilter(new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.SRC_IN));

        int outcome = Math.floorMod(((player.getChoice().getWeapon()) - (opponent.getChoice().getWeapon())), Player.MAX_HANDS);
        if (player.getChoice() == opponent.getChoice())
        {
            // tie
            winnerText.setText(R.string.tie_message);
        }
        else if (outcome % 2 != 0)
        {
            // player wins
            winnerText.setText(R.string.win_message);
            explainText.setText(getString(R.string.round_end_message, player.getChoice().toString(),opponent.getChoice().toString()));

        }
        else if (outcome % 2 == 0)
        {
            // opponent wins
            winnerText.setText(R.string.lose_message);
            explainText.setText(getString(R.string.round_end_message, opponent.getChoice().toString(),player.getChoice().toString()));
        }

    }
}