package com.example.calorietracker;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.Update;

import java.util.Date;
import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface StepsDAO {
    @Query("SELECT * FROM stepdata")
    List<Stepdata> getAll();
    @Query("SELECT * FROM stepdata WHERE number = :numberI ")
    Stepdata findByID(int numberI);
    @Insert
    void insertAll(Stepdata... stepdata);
    @Insert
    long insert(Stepdata stepdata);
    @Delete
    void delete(Stepdata stepdata);
    @Update(onConflict = REPLACE)
    public void updateUsers(Stepdata...stepdatas);
    @Query("DELETE FROM stepdata WHERE steps = :stepsI ")
    void deleByID(int stepsI);
    @Query("SELECT sum(steps)as totalsteps FROM stepdata ")
    int sumAll();
    class Converters {
   @TypeConverter
   public static Date fromTimestamp(Long value) {
          return value == null ? null : new Date(value);
        }

  @TypeConverter
        public static Long dateToTimestamp(Date date) {
            return date == null ? null : date.getTime();
        }
}
}
