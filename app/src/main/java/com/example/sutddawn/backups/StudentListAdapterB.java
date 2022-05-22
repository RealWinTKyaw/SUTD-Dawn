package com.example.sutddawn.backups;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sutddawn.R;
import com.example.sutddawn.ui.userprofile.UserProfile;
import com.example.sutddawn.user_classes.Student;

import java.util.ArrayList;

public class StudentListAdapterB extends RecyclerView.Adapter<com.example.sutddawn.backups.StudentListAdapterB.StudentViewHolder> {

    Context context;
    LayoutInflater inflater;
    ArrayList<Student> students;

    public StudentListAdapterB(Context context, ArrayList<Student> students) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.students = students;
    }

    @NonNull
    @Override
    public com.example.sutddawn.backups.StudentListAdapterB.StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View studentList = inflater.inflate(R.layout.listitem_student, parent, false);
        return new com.example.sutddawn.backups.StudentListAdapterB.StudentViewHolder(studentList);
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.sutddawn.backups.StudentListAdapterB.StudentViewHolder holder, int position) {
        holder.s = students.get(position);
        holder.studentName.setText(holder.s.getName());
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {
        Student s;
        TextView studentName;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.groupStudentName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context,
                            "Viewing student "+studentName.getText(),
                            Toast.LENGTH_SHORT).show();

                    Intent toStudent = new Intent(context, UserProfile.class);
                    toStudent.putExtra("emailId",s.getEmail());
                    toStudent.putExtra("caller","group");
                    context.startActivity(toStudent);
                }
            });
        }
    }
}
