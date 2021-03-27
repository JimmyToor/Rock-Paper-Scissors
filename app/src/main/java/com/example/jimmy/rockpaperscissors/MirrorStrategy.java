package com.example.jimmy.rockpaperscissors;

import java.util.List;
import java.util.Vector;

// Mirrors the player's last weapon
public class MirrorStrategy implements WeaponStrategy {
    public MirrorStrategy(){}
    public Weapon choose(Match match) {
        if (match.getPlayer().getWeaponHistory().isEmpty()) // no last weapon to mirror, so just pick the default
            return match.getOpponent().getWeapon();
        else
            return match.getPlayer().getWeaponHistory().lastElement();
    }
}
