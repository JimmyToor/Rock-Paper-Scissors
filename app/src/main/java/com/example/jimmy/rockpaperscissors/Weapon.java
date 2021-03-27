package com.example.jimmy.rockpaperscissors;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "weapon_table")
public class Weapon {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "weapon")
    private final String mWeaponName;
    private int ordinal; // used to determine what the weapon loses to and wins against
    @Ignore
    private static final Uri DEFAULT_IMG = Uri.parse("android.resource://com.example.jimmy.rockpaperscissors/drawable/ic_rock");
    @NonNull
    private Uri mImgUri;
    public Weapon(@NonNull String mWeaponName, @NonNull Uri mImgUri, int ordinal) {
        this.mWeaponName = mWeaponName;
        this.mImgUri = mImgUri;
        this.ordinal = ordinal;
    }
    @Ignore
    public Weapon(@NonNull String mWeaponName, int ordinal) {
        this.mWeaponName = mWeaponName;
        this.mImgUri = DEFAULT_IMG;
        this.ordinal = ordinal;
    }
    public String getWeaponName(){return this.mWeaponName;}
    public Uri getImgUri(){return this.mImgUri;}
    public int getOrdinal(){return this.ordinal;}
}
