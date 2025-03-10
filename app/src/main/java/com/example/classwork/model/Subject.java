package com.example.classwork.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Subject{
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "subject_name")
    String subjectName;

    @ColumnInfo(name = "user_id")
    int userId;

    public Subject(int id, String subjectName, int userId) {
        this.id = id;
        this.subjectName = subjectName;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}