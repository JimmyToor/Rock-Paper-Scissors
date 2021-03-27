package com.example.jimmy.rockpaperscissors;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WeaponDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Weapon weapon);

    @Query("DELETE FROM weapon_table")
    void deleteAll();

    @Query("SELECT * FROM weapon_table ORDER BY ordinal ASC ")
    LiveData<List<Weapon>> getAllWeapons();

    @Query("UPDATE weapon_table SET weapon = :newName WHERE weapon=:currName")
    void updateWeaponName(String currName, String newName);

    @Delete
    void deleteWeapon(Weapon weapon);
}
