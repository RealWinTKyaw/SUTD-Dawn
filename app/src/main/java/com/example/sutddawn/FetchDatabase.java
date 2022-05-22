package com.example.sutddawn;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sutddawn.user_classes.Group;
import com.example.sutddawn.user_classes.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FetchDatabase extends AppCompatActivity {

    private static FetchDatabase fetchDatabase;

    private FetchDatabase() {
    }

    public static FetchDatabase getInstance(){
        if(fetchDatabase == null){
            fetchDatabase = new FetchDatabase();
        }
        return fetchDatabase;
    }


    public static ArrayList<Student> getAllStudents() {
        ArrayList<Student> studentList = new ArrayList<>();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Users");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot datasnapshot : snapshot.getChildren()) {
                    Student studentInfo = datasnapshot.getValue(Student.class);
                    assert studentInfo != null;
                    studentList.add(studentInfo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return studentList;

    }

    public static ArrayList<Group> getAllGroups() {
        ArrayList<Group> groupList = new ArrayList<>();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Groups");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot datasnapshot : snapshot.getChildren()) {
                    Group groupInfo = datasnapshot.getValue(Group.class);
                    assert groupInfo != null;
                    groupList.add(groupInfo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return groupList;
    }

    public static HashMap<String, ArrayList<Student>> getCohortStudents() {
        HashMap<String, ArrayList<Student>> cohortStudents = new HashMap<>();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Users");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot datasnapshot : snapshot.getChildren()) {
                    Student studentInfo = datasnapshot.getValue(Student.class);
                    String cohortKey = studentInfo.getCohortClass();
                    if (cohortStudents.containsKey(cohortKey)) {
                        cohortStudents.get(cohortKey).add(studentInfo);
                    } else {
                        ArrayList<Student> studentList = new ArrayList<>();
                        //studentList.clear();
                        studentList.add(studentInfo);
                        cohortStudents.put(cohortKey, studentList);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return cohortStudents;
    }

    public static HashMap<String, ArrayList<Group>> getCohortGroup() {
        HashMap<String, ArrayList<Group>> cohortGroups = new HashMap<>();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Groups");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot datasnapshot : snapshot.getChildren()) {
                    Group groupInfo = datasnapshot.getValue(Group.class);
                    String groupKey = groupInfo.getCohortClass();
                    if (cohortGroups.containsKey(groupKey)) {
                        cohortGroups.get(groupKey).add(groupInfo);
                    } else {
                        ArrayList<Group> groupList = new ArrayList<>();
                        //groupList.clear();
                        groupList.add(groupInfo);
                        cohortGroups.put(groupKey, groupList);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return cohortGroups;
    }

    public static Student getUser(String firebaseID) {
        final Student[] result = new Student[1];
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Users");
        database.child(firebaseID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Student student = snapshot.getValue(Student.class);
                    result[0] = student;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return result[0];

    }

    public static Student getCurrentUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            final Student[] student = {new Student()};
            DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Users");
            database.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        student[0] = snapshot.getValue(Student.class);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
            return student[0];

        } else {
            // No user is signed in
            return null;
        }
    }


    public static void setCurrentUser(Student student) {

        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Users");
        database.child(student.getFirebaseID()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    database.child(student.getFirebaseID()).setValue(student);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }


}
