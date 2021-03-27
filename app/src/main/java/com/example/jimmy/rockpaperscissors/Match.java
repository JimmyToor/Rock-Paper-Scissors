package com.example.jimmy.rockpaperscissors;

import android.os.Build;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.List;
import java.util.Vector;

public class Match {
    private WeaponViewModel weaponsModel;
    private Vector<Weapon> playerHandHistory;
    private Vector<Weapon> opponentHandHistory;
    Human player;
    Robot opponent;
    TextView playerSelected; // ui element of the selected weapon
    TextView opponentSelected;
    int result;
    Outcome outcome;
    public enum Outcome {
        WIN,
        LOSS,
        TIE
    }

    public Match(WeaponViewModel weaponViewModel)
    {
        player = new Human();
        opponent = new Robot();
        weaponsModel = weaponViewModel;
        playerHandHistory = player.getWeaponHistory();
        opponentHandHistory = opponent.getWeaponHistory();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void startRound()
    {

        opponent.makeChoice(this);

        // compare weapon choices and determine outcome
        result = Math.floorMod(((player.getWeapon().getOrdinal()) - (opponent.getWeapon().getOrdinal())), Player.MAX_HANDS);
        if (player.getWeapon() == opponent.getWeapon())
        {
            // tie
            outcome = Outcome.TIE;
        }
        else if (result % 2 != 0) // outcome is odd
        {
            // player wins
            outcome = Outcome.WIN;

        }
        else { // outcome is even
            // opponent wins
            outcome = Outcome.LOSS;
        }

        playerHandHistory.add(player.getWeapon());
        opponentHandHistory.add(opponent.getWeapon());
    }

    public Human getPlayer()
    {
        return player;
    }

    public Robot getOpponent()
    {
        return opponent;
    }

    public List<Weapon> getWeapons()
    {
        return weaponsModel.getAllWeapons().getValue();
    }

    public Outcome getOutcome()
    {
        return outcome;
    }

    public void setWeaponStrategy(WeaponStrategy strategy)
    {
        opponent.setWeaponStrategy(strategy);
    }
}
