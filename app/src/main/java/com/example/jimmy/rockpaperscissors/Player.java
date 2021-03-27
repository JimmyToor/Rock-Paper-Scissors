package com.example.jimmy.rockpaperscissors;

import java.util.Objects;
import java.util.Vector;

public abstract class Player {
    public static int MAX_HANDS = 5;
    public static String DEFAULT_WEAPON_NAME = "Rock";
    private Vector<Weapon> weaponHistory = new Vector<>();
    private String name = "DEFAULT_PLAYER";

    private Weapon weaponChoice = null;

    Player() { }

    Player(String name) {
        this.name = name;
    }

    public void setWeapon(Weapon newWeapon)
    {
        this.weaponChoice = newWeapon;
        Objects.requireNonNull(this.weaponChoice);
    }

    public Weapon getWeapon()
    {
        return this.weaponChoice;
    }

    public Vector<Weapon> getWeaponHistory()
    {
        return weaponHistory;
    }

}
