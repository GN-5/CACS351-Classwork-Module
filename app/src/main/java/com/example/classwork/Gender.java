package com.example.classwork;

import androidx.annotation.NonNull;

public enum Gender {
    MALE, FEMALE, OTHERS;

    @NonNull
    @Override
    public String toString(){
        String theGender = "Unknown";

        switch (this){
            case MALE:
                    theGender = "Male";
                    break;
            case FEMALE:
                theGender = "Female";
                break;
            case OTHERS:
                theGender = "Others";
                break;
        }
        return theGender;
    }
}
