package com.example.classwork.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.classwork.Gender;

@Entity
public class Student {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "gender")
    private Gender gender;

    @ColumnInfo(name = "grade")
     private Grade grade;

    @ColumnInfo(name = "is_enrolled")
     private boolean isEnrolled;

    public Student(int id, String name, Gender gender, Grade grade, boolean isEnrolled) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.grade = grade;
        this.isEnrolled = isEnrolled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public boolean isEnrolled() {
        return isEnrolled;
    }

    public void setEnrolled(boolean enrolled) {
        isEnrolled = enrolled;
    }
}
