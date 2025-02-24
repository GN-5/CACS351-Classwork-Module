package com.example.classwork;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.ArraySet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import com.example.classwork.databinding.ActivityAddStudentBinding;
import com.example.classwork.model.Grade;
import com.example.classwork.model.OptionalSubject;

import java.util.ArrayList;
import java.util.Set;

public class AddStudentActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, CompoundButton.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {

    ActivityAddStudentBinding binding;

    Grade[] grades = {
            Grade.ONE,
            Grade.TWO,
            Grade.THREE,
            Grade.FOUR,
            Grade.FIVE,
            Grade.SIX,
            Grade.SEVEN,
            Grade.EIGHT,
            Grade.NINE,
            Grade.TEN,
    };
    Grade selectedGrade = grades[0];

    Gender selectedGender;

    Set<OptionalSubject> selectedOptionalSubjects = new ArraySet<>();
    private static final String TAG = "AddStudentActivity";

    boolean isEnrolled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityAddStudentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets)->{
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initializeView();
    }

    private void initializeView(){
        binding.addBtn.setOnClickListener(AddStudentActivity.this);
        binding.genderGroup.setOnCheckedChangeListener(AddStudentActivity.this);
        binding.gradeSpinnerItem.setOnItemSelectedListener(AddStudentActivity.this);
        binding.optSubjectAccounts.setOnCheckedChangeListener(AddStudentActivity.this);
        binding.optSubjectMath.setOnCheckedChangeListener(AddStudentActivity.this);
        binding.optSubjectComputer.setOnCheckedChangeListener(AddStudentActivity.this);
        binding.optSubjectEconomics.setOnCheckedChangeListener(AddStudentActivity.this);
        binding.isEnrolled.setOnCheckedChangeListener(AddStudentActivity.this);

        initializeGradeAdapter();

    }

    void initializeGradeAdapter(){

        ArrayAdapter<Grade> gradeAdapter = new ArrayAdapter<>(
                AddStudentActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                grades
        );
        binding.gradeSpinnerItem.setAdapter(gradeAdapter);
    }


    private void showMessage(String message){
        new AlertDialog.Builder(AddStudentActivity.this)
                .setTitle(getString(R.string.message))
                .setMessage(message)
                .show();
    }
    private boolean validateStudentName(){
        boolean isValid = true;

        if(binding.studentName.toString().isEmpty()){
            isValid = false;
            showMessage(getString(R.string.student_name_needed));

        }

        return isValid;
    }
    private  boolean validate(){
        if(!validateStudentName()){
            return false;
        }

        return true;
    }
    //Task - validate all fields

    public void onClick(View view){
        if(validate()){

        }
    }

    //for Gender
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if(binding.genderMale.isChecked()){
            selectedGender = Gender.MALE;
        }else if(binding.genderFemale.isChecked()){
            selectedGender = Gender.FEMALE;
        }else if(binding.genderOthers.isChecked()){
            selectedGender = Gender.OTHERS;
        }else {
            //will not reach here
            selectedGender = null;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedGrade = grades[i];

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    //for optional subjects checkbox, and isEnrolled switch
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        //if accounts has been checked
        if(compoundButton.getId() == binding.optSubjectAccounts.getId()) {
            if (isChecked) {
                selectedOptionalSubjects.add(OptionalSubject.ACCOUNTS);
            }else{
                selectedOptionalSubjects.remove(OptionalSubject.ACCOUNTS);
            }
        }

        //if economics has been checked
        else if(compoundButton.getId() == binding.optSubjectEconomics.getId()) {
            if (isChecked) {
                selectedOptionalSubjects.add(OptionalSubject.ECONOMICS);
            }else{
                selectedOptionalSubjects.remove(OptionalSubject.ECONOMICS);
            }
        }

        //if computer has been checked
        else if(compoundButton.getId() == binding.optSubjectComputer.getId()) {
            if (isChecked) {
                selectedOptionalSubjects.add(OptionalSubject.COMPUTER);
            }else{
                selectedOptionalSubjects.remove(OptionalSubject.COMPUTER);
            }
        }

        //if optional maths has been checked
        else if(compoundButton.getId() == binding.optSubjectMath.getId()) {
            if (isChecked) {
                selectedOptionalSubjects.add(OptionalSubject.OPTIONAL_MATH);
            }else{
                selectedOptionalSubjects.remove(OptionalSubject.OPTIONAL_MATH);
            }
        }

        Log.i(TAG, "onCheckChange :" +selectedOptionalSubjects);


        //checking for isEnrolled
        else if(compoundButton.getId() == binding.isEnrolled.getId()){
            isEnrolled = isChecked;
        }
    }

}