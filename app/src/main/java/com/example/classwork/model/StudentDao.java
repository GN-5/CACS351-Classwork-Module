package com.example.classwork.model;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface StudentDao {

    @Insert
    void insertStudent(Student... student);
}
