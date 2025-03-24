package com.example.classwork.model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Relation;

import java.io.Serializable;
import java.util.List;

public class StudentWithOptionalSubject implements Serializable {
    @Embedded public Student student;
    @Relation(
            parentColumn = "id",
            entityColumn = "user_id"
    )
    public List<Subject> subjects;

}
