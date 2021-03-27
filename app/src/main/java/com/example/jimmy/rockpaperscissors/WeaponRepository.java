package com.example.jimmy.rockpaperscissors;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WeaponRepository {
    private WeaponDao mWeaponDao;
    private LiveData<List<Weapon>> mAllWords;

    WeaponRepository(Application application) {
        WeaponRoomDatabase db = WeaponRoomDatabase.getDatabase(application);
        mWeaponDao = db.weaponDao();
        mAllWords = mWeaponDao.getAllWeapons();
    }

    LiveData<List<Weapon>> getAllWeapons() {
        return mAllWords;
    }

    void insert(Weapon weapon) {
        WeaponRoomDatabase.databaseWriteExecutor.execute(() -> mWeaponDao.insert(weapon));
    }

    void updateWeaponName(String currName, String newName)
    {
        mWeaponDao.updateWeaponName(currName,newName);
    }

    void deleteAll() {
        WeaponRoomDatabase.databaseWriteExecutor.execute(() -> mWeaponDao.deleteAll());
    }

    void deleteWeapon(Weapon weapon){
        WeaponRoomDatabase.databaseWriteExecutor.execute(() -> mWeaponDao.deleteWeapon(weapon));
    }
}






