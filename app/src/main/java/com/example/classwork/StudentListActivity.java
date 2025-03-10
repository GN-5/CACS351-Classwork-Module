package com.example.classwork;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.classwork.data.AppDatabase;
import com.example.classwork.databinding.ActivityAddStudentBinding;
import com.example.classwork.databinding.ActivityStudentListBinding;
import com.example.classwork.model.Student;

import java.util.List;
import java.util.concurrent.Executors;

public class StudentListActivity extends AppCompatActivity {
    ActivityStudentListBinding binding;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        binding = ActivityStudentListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets)->{
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initializeView();
    }

    private void initializeView(){
        updateStudents();
    }

    private void updateStudents(){

        Executors.newSingleThreadExecutor().execute(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            // get list of students from databases.
                                                            List<Student> students = AppDatabase.getInstance(StudentListActivity.this).studentDao().getAll();

                                                            // initialize recycler view adapter
                                                            StudentListAdapter studentListAdapter = new StudentListAdapter(students);

                                                            // pass adapter to recycler view
                                                            runOnUiThread(()->{
                                                                binding.studentRV.setAdapter(studentListAdapter);
                                                                binding.studentRV.setLayoutManager(new LinearLayoutManager(StudentListActivity.this));
                                                            });

                                                        }
                                                    }
        );

    }
}