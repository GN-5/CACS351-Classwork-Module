package com.example.classwork;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.classwork.databinding.ItemStudentBinding;
import com.example.classwork.model.Student;
import com.example.classwork.model.StudentWithOptionalSubject;

import java.util.List;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.ViewHolder>{

    public StudentListAdapter(List<StudentWithOptionalSubject> studentWithOptionalSubject) {
        this.StudentWithOptionalSubject = studentWithOptionalSubject;
    }

    private List<StudentWithOptionalSubject> StudentWithOptionalSubject;

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
        }
    }
}
