package com.example.jimmy.rockpaperscissors;

import java.util.Objects;
import java.util.Vector;

public abstract class Player {
    public static final int MAX_HANDS = 5;
    private final int DEFAULT_CHOICE = 0;
    public Vector<Hand> handHistory = new Vector<>();
    public String name = "DEFAULT_PLAYER";

    public enum Hand { //
        Rock(0),
        Paper(1),
        Scissors(2),
        Spock(3),
        Lizard(4);

        private final int weapon;
        public int imgID;

        Hand(int weapon) {
            this.weapon = weapon;
        }

        public int getWeapon() { return weapon; }

        public static Hand fromInt(int i) {
            for (Hand hand : Hand .values()) {
                if (hand.getWeapon() == i) { return hand; }
            }
            return null;
        }

        static {
            Rock.imgID = R.drawable.ic_rock;
            Paper.imgID = R.drawable.ic_paper;
            Scissors.imgID = R.drawable.ic_scissors;
            Spock.imgID = R.drawable.ic_spock;
            Lizard.imgID = R.drawable.ic_rock;
        }

    }

    private Hand choice = Hand.fromInt(DEFAULT_CHOICE);

    Player()
    {
        setChoice(DEFAULT_CHOICE);
    }

    Player(String name) {
        setChoice(DEFAULT_CHOICE);
        this.name = name;
    }

    public void setChoice(int newChoice)
    {
        this.choice = Hand.fromInt(newChoice);
        Objects.requireNonNull(this.choice);
        handHistory.add(Hand.fromInt(newChoice));
    }

    public Hand getChoice()
    {
        return this.choice;
    }

}
