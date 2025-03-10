package com.example.classwork.model;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface SubjectDao {
    @Insert
    public void insertSubject(Subject subject);
}
