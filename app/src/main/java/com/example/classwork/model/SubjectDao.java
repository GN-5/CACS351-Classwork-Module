package com.example.classwork.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface SubjectDao {
    @Insert
    public void insertSubject(Subject subject);
    @Query("DELETE FROM Subject WHERE user_id= :id")
    void deleteSubjectHavingIds(int id);


}
