package com.example.classwork;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.ArraySet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import com.example.classwork.data.AppDatabase;
import com.example.classwork.databinding.ActivityAddStudentBinding;
import com.example.classwork.model.Grade;
import com.example.classwork.model.OptionalSubject;
import com.example.classwork.model.Student;
import com.example.classwork.model.StudentDao;
import com.example.classwork.model.Subject;
import com.example.classwork.model.SubjectDao;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AddStudentActivity
        extends AppCompatActivity
        implements View.OnClickListener,
        RadioGroup.OnCheckedChangeListener,
        CompoundButton.OnCheckedChangeListener,
        AdapterView.OnItemSelectedListener {

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
        setSupportActionBar(binding.myToolbar);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets)->{
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initializeView();
    }

    private void initializeView(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setTitle(getString(R.string.all_students));
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection.
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }

    void initializeGradeAdapter(){

        ArrayAdapter<Grade> gradeAdapter = new ArrayAdapter<Grade>(
                AddStudentActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                grades
        ){
            @Override
            public View getDropDownView(
                    int position,
                    @Nullable View convertView,
                    @NonNull ViewGroup parent
            ) {

                CheckedTextView dropDownView = (CheckedTextView) super.getDropDownView(position, convertView, parent);
                if(binding.gradeSpinnerItem.getSelectedItemPosition() == position){
                    dropDownView.setTextColor(getColor(R.color.green));
                }
                return dropDownView;
            }
        };
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
    //Task - validate all fields
    private boolean validateGender(){
        boolean isValid = true;

        if(selectedGender == null){
            isValid = false;
            showMessage(getString(R.string.gender_needed));
        }

        return isValid;
    }
    private boolean validateOptionalSubjects(){
        boolean isValid = true;
        
        if(selectedOptionalSubjects.isEmpty()){
            isValid = false;
            showMessage(getString(R.string.optional_subjects_needed));
        } else if (selectedOptionalSubjects.size() != 2) {
            isValid = false;
            showMessage(getString(R.string.optional_subjects_cannot_be_more_than_two));
        }

        return isValid;
    }

    // Validate that a grade has been selected
    private boolean validateGrade(){
        boolean isValid = true;

        if(selectedGrade == null) {
            isValid = false;
            showMessage(getString(R.string.grade_needed));
        }

        return isValid;
    }

    private boolean validate(){
        if(!validateStudentName() || !validateGender() || !validateOptionalSubjects() || !validateGrade()){
            return false;
        }
        return true;
    }


    public void onClick(View view){
        if(validate()) {
            addStudent();
        }
    }

    private void addStudent() {
        String name = binding.studentName.getText().toString();
        Gender gender = this.selectedGender;
        Grade grade = this.selectedGrade;
        boolean isEnrolled = this.isEnrolled;

        Student student = new Student(
                0,
                name,
                gender,
                grade,
                isEnrolled
        );
        AppDatabase appDatabase = AppDatabase.getInstance(AddStudentActivity.this);

        StudentDao studentDao = appDatabase.studentDao();
        SubjectDao subjectDao = appDatabase.subjectDao();

        Executors.newSingleThreadExecutor().execute(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            long studentId = studentDao.insertStudent(student);


                                                            //TODO save optional subjects as well.
                                                            for(OptionalSubject subject : selectedOptionalSubjects){
                                                                subjectDao.insertSubject(
                                                                        new Subject(
                                                                                0,
                                                                                subject.name(),
                                                                                (int) studentId
                                                                        ));
                                                            }

                                                            navigateToStudentList();
                                                        }
                                                    }
        );
    }

    private void navigateToStudentList(){
        Intent intent = new Intent(AddStudentActivity.this, StudentListActivity.class);
        startActivity(intent);
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
        //show toast
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


        //checking for isEnrolled
        else if(compoundButton.getId() == binding.isEnrolled.getId()) {
            isEnrolled = isChecked;

        }
        Log.i(TAG, "onCheckChange :" + selectedOptionalSubjects);
    }

}