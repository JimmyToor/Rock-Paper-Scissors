package com.example.jimmy.rockpaperscissors;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Vector;

public class HandHistoryViewModel extends ViewModel {
    private MutableLiveData<Vector<Weapon>> handHistory;

    public MutableLiveData<Vector<Weapon>> getCurrentHistory() {
        if (handHistory == null) {
            handHistory = new MutableLiveData<>();
        }
        return handHistory;
    }
}
