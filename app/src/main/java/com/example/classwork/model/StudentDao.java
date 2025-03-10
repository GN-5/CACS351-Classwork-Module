package com.example.classwork.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface StudentDao {

    @Insert
    void insertStudent(Student... student);

    @Query("SELECT * FROM student")
    List<Student> getAll();
}
