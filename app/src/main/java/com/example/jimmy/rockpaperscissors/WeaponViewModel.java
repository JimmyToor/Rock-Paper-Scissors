package com.example.jimmy.rockpaperscissors;

import android.app.Application;
import android.content.ContentResolver;
import android.content.res.Resources;
import android.database.Observable;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

// Used to hold and share weapon information
public class WeaponViewModel extends AndroidViewModel {
    private WeaponRepository mRepository;
    private final LiveData<List<Weapon>> mAllWeapons;

    public WeaponViewModel (Application application)
    {
        super(application);
        mRepository = new WeaponRepository(application);
        mAllWeapons = mRepository.getAllWeapons();
    }

    public LiveData<List<Weapon>> getAllWeapons() {
        return mAllWeapons;
    }

    void insert(Weapon weapon) { mRepository.insert(weapon);}

    void setWeaponName(String currName, String newName){mRepository.updateWeaponName(currName,newName);}

    public void deleteAll(){mRepository.deleteAll();}

    public void deleteWeapon(Weapon weapon){mRepository.deleteWeapon(weapon);}

    public void init()
    {
        String qualifier = ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + BuildConfig.APPLICATION_ID + '/' + "drawable/";
        insert(new Weapon("Rock", Uri.parse(qualifier + "ic_rock"),1));
        insert(new Weapon("Paper",Uri.parse(qualifier + "ic_paper"),2));
        insert(new Weapon("Scissors",Uri.parse(qualifier + "ic_scissors"),3));
        insert(new Weapon("Spock",Uri.parse(qualifier + "ic_spock"),4));
        insert(new Weapon("Lizard",Uri.parse(qualifier + "ic_lizard"),5));
    }

    public int getHighestOrdinal()
    {
        return mAllWeapons.getValue().get(mAllWeapons.getValue().size()-1).getOrdinal();
    }

    public Weapon getWeaponAtIndex(int index)
    {
        return mAllWeapons.getValue().get(index);
    }

}