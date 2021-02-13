package com.example.jimmy.rockpaperscissors;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Human extends Player {

    public Human(){
        super("DEFAULT_HUMAN_NAME");
    }

    public Human(String name)
    {
        super(name);
        View.OnClickListener switchImageHandler = view -> changeChoice();
    }

    void changeChoice()
    {
        setChoice((getChoice().getWeapon()+1) % 3);
    }
}
