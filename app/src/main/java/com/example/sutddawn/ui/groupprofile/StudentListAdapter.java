package com.example.sutddawn.ui.groupprofile;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sutddawn.R;
import com.example.sutddawn.ui.userprofile.SkillListAdapter;
import com.example.sutddawn.ui.userprofile.UserProfile;
import com.example.sutddawn.user_classes.Group;
import com.example.sutddawn.user_classes.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.StudentViewHolder> {

    Context context;
    LayoutInflater inflater;
    ArrayList<String> students;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference().child("Users");

    public StudentListAdapter(Context context, ArrayList<String> students) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.students = students;
    }

    @NonNull
    @Override
    public StudentListAdapter.StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View studentList = inflater.inflate(R.layout.listitem_student, parent, false);
        return new StudentViewHolder(studentList);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentListAdapter.StudentViewHolder holder, int position) {
        //retrieves Student data from Firebase via "pointers" in the Group object
        String firebaseID = students.get(position);
        databaseReference
                .child(firebaseID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Student s = null;
                        if (snapshot.exists()) {
                            s = snapshot.getValue(Student.class);
                        }
                        holder.studentName.setText(s.getName());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {

        TextView studentName;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.groupStudentName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    String firebaseID = students.get(position);
                    Toast.makeText(context,
                            "Viewing student "+studentName.getText(),
                            Toast.LENGTH_SHORT).show();
                    final Intent toStudent = new Intent(context, UserProfile.class);
                    toStudent.putExtra("selected", firebaseID);
                    context.startActivity(toStudent);
                }
            });
        }
    }
}
