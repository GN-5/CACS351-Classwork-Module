package com.example.classwork.data;


import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Database;

import com.example.classwork.model.Student;
import com.example.classwork.model.StudentDao;

@Database(
        entities = {
                Student.class
        },
        version = 1,
        exportSchema = false
)

public abstract class AppDatabase extends RoomDatabase {

    private volatile static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance != null) return instance;
        instance = Room.databaseBuilder(
                context,
                AppDatabase.class,
                "app_database"
        ).build();

        return instance;
    }

    public abstract StudentDao studentDao();
}
