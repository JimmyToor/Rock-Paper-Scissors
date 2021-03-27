package com.example.jimmy.rockpaperscissors;

import java.util.Random;

// Randomly decides the next choice
public class RandomStrategy implements WeaponStrategy {
    public RandomStrategy(){}
    public Weapon choose(Match match) {
        Random result = new Random();
        return match.getWeapons().get(result.nextInt(Player.MAX_HANDS));
    }
}
