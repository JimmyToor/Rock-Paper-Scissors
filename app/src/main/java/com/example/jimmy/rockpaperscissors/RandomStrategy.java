package com.example.jimmy.rockpaperscissors;

import java.util.Random;

// Randomly decides the next choice
public class RandomStrategy implements WeaponStrategy {
    public int choose() {
        Random result = new Random();
        return result.nextInt(Player.MAX_HANDS);
    }
}
