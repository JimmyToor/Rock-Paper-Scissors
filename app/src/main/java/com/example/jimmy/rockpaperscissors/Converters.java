package com.example.jimmy.rockpaperscissors;

import android.net.Uri;

import androidx.room.TypeConverter;

public class Converters {
    @TypeConverter
    public static Uri toUri(String value) {
        return value == null ? null : Uri.parse(value);
    }

    @TypeConverter
    public static String fromUri(Uri uri) {
        return uri == null ? null : uri.toString();
    }

}
