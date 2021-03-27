package com.example.jimmy.rockpaperscissors;

public interface WeaponStrategy {
    Weapon choose(Match match); // returns the next weapon choice using the match for any needed context
}
