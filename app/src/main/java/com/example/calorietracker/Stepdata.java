package com.example.calorietracker;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;


@Entity
public class Stepdata {
    @PrimaryKey(autoGenerate = true)
    public int number;
    @ColumnInfo(name = "Steps")
    public int steps;
    @ColumnInfo(name = "Time")
    public Date date;

    public Stepdata(int steps, Date date) {

        this.steps = steps;
        this.date = date;

    }
    public int getId() {
        return number;
    }
    public int getsteps() {
        return steps;
    }

    public Date getDate() {

        return date;
    }
    }
