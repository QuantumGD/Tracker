package com.example.calorietracker;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(entities = {Stepdata.class}, version = 2, exportSchema = false)
@TypeConverters({StepsDAO.Converters.class})
public abstract class Basesteps extends RoomDatabase {
    public abstract StepsDAO stepsDAO();
    private static volatile Basesteps INSTANCE;
    static Basesteps getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (Basesteps.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            Basesteps.class, "steps_base")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
