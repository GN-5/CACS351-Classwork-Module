package com.example.classwork;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.classwork.databinding.ItemStudentBinding;
import com.example.classwork.model.Student;
import com.example.classwork.model.StudentWithOptionalSubject;
import com.example.classwork.model.Subject;

import java.util.List;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.ViewHolder>{

    private List<StudentWithOptionalSubject> StudentWithOptionalSubject;
    private StudentMenuClickListener studentMenuClickListener;

    public StudentListAdapter(List<StudentWithOptionalSubject> studentWithOptionalSubject, StudentMenuClickListener studentMenuClickListener) {
        this.StudentWithOptionalSubject = studentWithOptionalSubject;
        this.studentMenuClickListener = studentMenuClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemStudentBinding binding = ItemStudentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StudentWithOptionalSubject studentWithOptionalSubject = StudentWithOptionalSubject.get(position);
        holder.updateStudentDetails(studentWithOptionalSubject);
    }

    @Override
    public int getItemCount() {
        return StudentWithOptionalSubject.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ItemStudentBinding itemStudentBinding;

        public ViewHolder(@NonNull ItemStudentBinding itemView ) {
            super(itemView.getRoot());
            this.itemStudentBinding = itemView;
        }

        void updateStudentDetails(StudentWithOptionalSubject studentWithOptionalSubject){
            itemStudentBinding.studentName.setText(studentWithOptionalSubject.student.getName());
            itemStudentBinding.genderValue.setText(studentWithOptionalSubject.student.getGender().toString());
            itemStudentBinding.gradeValue.setText(studentWithOptionalSubject.student.getGrade().toString());
            itemStudentBinding.isEnrolledValue.setText(String.valueOf(studentWithOptionalSubject.student.isEnrolled()));

            //TODO task: add optional subjects.
            StringBuilder subjectsBuilder = new StringBuilder();
            if (studentWithOptionalSubject.subjects.isEmpty()) {
                subjectsBuilder.append("No subjects");
            } else {
                for (Subject subject : studentWithOptionalSubject.subjects) {
                    subjectsBuilder.append(subject.getSubjectName()).append(", ");
                }
                subjectsBuilder.setLength(subjectsBuilder.length() - 2);
            }
            itemStudentBinding.optionalSubjectValue.setText(subjectsBuilder.toString());
            itemStudentBinding.moreOptions.setOnClickListener((view)->{
                PopupMenu popup = new PopupMenu(this, v);

                // This activity implements OnMenuItemClickListener.
                popup.setOnMenuItemClickListener(item ->{
                    if(item.getItemId() == R.id.deleteStudent){
                        studentMenuClickListener.onDeleteClicked(studentWithOptionalSubject);
                        return true;
                    }
                    else if(item.getItemId() == R.id.editStudent) {
                        studentMenuClickListener.onEditClicked(studentWithOptionalSubject);
                    }
                    return false;
                });
                popup.inflate(R.menu.student_item_menu);
                popup.show();
            });
        }
    }
}

interface StudentMenuClickListener{
    void onDeleteClicked(StudentWithOptionalSubject studentWithOptionalSubject);
    void onEditClicked(StudentWithOptionalSubject studentWithOptionalSubject);
}
