package com.example.jimmy.rockpaperscissors;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {Weapon.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class WeaponRoomDatabase extends RoomDatabase {
    public abstract WeaponDao weaponDao();
    private static volatile WeaponRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static WeaponRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WeaponRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WeaponRoomDatabase.class, "weapon_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
