package com.example.classwork;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.example.classwork.data.AppDatabase;
import com.example.classwork.databinding.ActivityAddStudentBinding;
import com.example.classwork.databinding.ActivityStudentListBinding;
import com.example.classwork.model.Student;
import com.example.classwork.model.StudentDao;
import com.example.classwork.model.StudentWithOptionalSubject;
import com.example.classwork.model.Subject;
import com.example.classwork.model.SubjectDao;

import java.util.List;
import java.util.concurrent.Executors;

public class StudentListActivity extends AppCompatActivity implements StudentMenuClickListener{

    ActivityResultLauncher<Intent> updateStudentResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            (result) ->{
                //TODO: will receive result here.
                if(result.getResultCode() == RESULT_OK){
                    Intent intent = result.getData();
                    if(intent != null && intent.hasExtra(AddStudentActivity.EXTRA_STUDENT_WITH_OPTIONAL_SUBJECTS)){
                        StudentWithOptionalSubject studentWithOptionalSubject =
                                (StudentWithOptionalSubject) intent.getSerializableExtra(AddStudentActivity.EXTRA_STUDENT_WITH_OPTIONAL_SUBJECTS);
                        Log.d("Gaurab", "Received Result: " + studentWithOptionalSubject);
                        updateIndividualStudent(studentWithOptionalSubject);
                    }
                }
            }
    );
    ActivityStudentListBinding binding;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        binding = ActivityStudentListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.myToolbar);
        initializeView();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.student_list_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection.
        if(item.getItemId() == R.id.addStudentMenu){
            //navigate to add student activity
            Intent intent = new Intent((StudentListActivity.this), AddStudentActivity.class);
            startActivity(intent);
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void initializeView(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setTitle(getString(R.string.all_students));
        }
        updateStudents();

    }



    private void updateStudents(){

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                // get list of students from databases.
                List<StudentWithOptionalSubject> students = AppDatabase.getInstance(StudentListActivity.this).studentDao().getAll();

                // initialize recycler view adapter
                StudentListAdapter studentListAdapter = new StudentListAdapter(students, StudentListActivity.this);

                 // pass adapter to recycler view
                runOnUiThread(()->{
                    binding.studentRV.setAdapter(studentListAdapter);
                    binding.studentRV.setLayoutManager(new LinearLayoutManager(StudentListActivity.this));
                });
            }
        }
        );

    }

    @Override
    public void onDeleteClicked(StudentWithOptionalSubject studentWithOptionalSubject) {
        new AlertDialog.Builder(StudentListActivity.this)
                .setTitle(R.string.delete)
                .setMessage(R.string.delete_student_confirmation)
                .setPositiveButton(R.string.delete, (dialog, which) -> {
                    Executors.newSingleThreadExecutor().execute(()->{
                        //TODO delete student
                        StudentDao studentDao = AppDatabase.getInstance(StudentListActivity.this).studentDao();
                        studentDao.deleteStudent(studentWithOptionalSubject.student);
                        updateStudents();


                    });
                    dialog.dismiss();
                })
                .setNegativeButton((R.string.cancel), (dialog, which)->{
                    dialog.dismiss();
                }).show();


    }
    @Override
    public void onEditClicked(StudentWithOptionalSubject studentWithOptionalSubject) {

        Executors.newSingleThreadExecutor().execute(()->{
            //TODO edit student
            Intent intent = new Intent(StudentListActivity.this, AddStudentActivity.class);
            intent.putExtra(AddStudentActivity.EXTRA_STUDENT_WITH_OPTIONAL_SUBJECTS, studentWithOptionalSubject);
            updateStudentResultLauncher.launch(intent);

        });
    }

    private void updateIndividualStudent(StudentWithOptionalSubject studentWithOptionalSubject){
        Executors.newSingleThreadScheduledExecutor().execute(()-> {
            AppDatabase appDatabase = AppDatabase.getInstance(StudentListActivity.this);
            SubjectDao subjectDao = appDatabase.subjectDao();
            StudentDao studentDao = appDatabase.studentDao();

            subjectDao.deleteSubjectHavingIds(studentWithOptionalSubject.student.getId());
            studentDao.updateStudent(studentWithOptionalSubject.student);

            for(Subject subject: studentWithOptionalSubject.subjects ){
                subjectDao.insertSubject(subject);
            }

            updateStudents();
        });
    }
}