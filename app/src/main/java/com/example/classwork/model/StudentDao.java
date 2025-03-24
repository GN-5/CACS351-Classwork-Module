package com.example.classwork.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface StudentDao {

    @Insert
    long insertStudent(Student student);

    @Query("SELECT * FROM student")
    List<StudentWithOptionalSubject> getAll();

    @Delete
    void deleteStudent(Student student);


}
