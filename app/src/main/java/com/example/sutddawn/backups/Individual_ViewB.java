package com.example.sutddawn.backups;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sutddawn.R;
import com.example.sutddawn.ui.marketplace.MyExpandableListAdaptorIndv;
import com.example.sutddawn.ui.marketplace.Group_View;
import com.example.sutddawn.ui.userprofile.UserProfile;
import com.example.sutddawn.user_classes.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Individual_ViewB extends AppCompatActivity {

    List<String> parentList; //cohort list
    List<Student> childList; //student list in each cohort
    Map<String, List<Student>> studentCollection; //to link parent and child
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;

    HashMap<String, ArrayList<Student>> list_of_stud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marketplace_individual_view);

        list_of_stud = new HashMap<String, ArrayList<Student>>();

        createParentList();
        createCollection(); //to populate the map
        ToggleButton toggle = (ToggleButton) findViewById(R.id.toggleButton);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    toggle.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view){
                            //TODO: Set intent go to Individual
                            Intent i = new Intent(com.example.sutddawn.backups.Individual_ViewB.this, Group_View.class);
                            startActivity(i);
                        }
                    });
                } else {
                    //No Change
                }
            }
        });

        //get the handle of expandable list view
        expandableListView = findViewById(R.id.studentList);
        //instantiate adapter and pass 3 things(context, list, map) into the constructor
        expandableListAdapter = new MyExpandableListAdaptorIndv(this, parentList, studentCollection);
        //use the adapter to expand the list view
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            //have the current expanded group position store in a variable
            int lastExpandedPosition = -1;

            @Override
            public void onGroupExpand(int i) {
                if (lastExpandedPosition != -1 && i != lastExpandedPosition) {
                    expandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = i;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int j, long l) {
                //use adaptor to pass child and parent position
                Student selected = (Student) expandableListAdapter.getChild(i, j);
                //toast message to show child element
                Toast.makeText(getApplicationContext(), "Selected: " + selected.getName(), Toast.LENGTH_SHORT).show();

                //intent to user profile on click
                Intent toUserProfile = new Intent(com.example.sutddawn.backups.Individual_ViewB.this, UserProfile.class);
                getIntent().putExtra("emailId", selected.getEmail());
                startActivityForResult(toUserProfile, 100);
                return true;
            }
        });
    }

    private void createParentList() {
        parentList = new ArrayList<>();
        parentList.add("Cohort 1");
        parentList.add("Cohort 2");
        parentList.add("Cohort 3");
        parentList.add("Cohort 4");
        parentList.add("Cohort 5");
    }

    private void createCollection() {
        //data of each group
//        String[] cohort1_stud = {"Michelle", "Albert", "Jessie"};
//        String[] cohort2_stud = {"Joey", "Helen", "Philip"};
//        String[] cohort3_stud = {"Robert", "Belle", "Xin"};
//        String[] cohort4_stud = {"John", "Agnes", "May"};
//        String[] cohort5_stud = {"Yi Ting", "Rain", "Hash"};

        // DEBUGGING START
        ArrayList<Student> cohort1 = new ArrayList<>();
        cohort1.add(new Student("1005555","Pumpkin Koh","pumpkinkoh@sutd.edu.sg",""));
        cohort1.add(new Student("1005556","Trent Koh","trentkoh@sutd.edu.sg",""));
        cohort1.add(new Student("1005557","Trish Koh","trishkoh@sutd.edu.sg",""));

        ArrayList<Student> cohort2 = new ArrayList<>();
        cohort2.add(new Student("1006555","Majestic","majestic@sutd.edu.sg",""));
        cohort2.add(new Student("1006556","Tranquil","tranquil@sutd.edu.sg",""));
        cohort2.add(new Student("1006557","Openness","openness@sutd.edu.sg",""));
        // DEBUGGING END

        //leave data as variable?
//        String[] list_of_stud = new String[0];
        list_of_stud.put("Cohort 1",cohort1);
        list_of_stud.put("Cohort 2",cohort2);

        //instantiate
        studentCollection = new HashMap<String, List<Student>>();
        for(String student: parentList){
            //load each child in childList to their parentList
            if(student.equals("Cohort 1")){
                loadChild(list_of_stud.get("Cohort 1"));
//                loadChild(cohort1_stud);
            }
            else if(student.equals("Cohort 2")){
                loadChild(list_of_stud.get("Cohort 2"));
//                loadChild((cohort2_stud));
            }
            else if(student.equals("Cohort 3")){
//                loadChild((cohort3_stud));
            }
            else if(student.equals("Cohort 4")){
//                loadChild((cohort4_stud));
            }
            else{
//                loadChild((cohort5_stud));
            }
            studentCollection.put(student, childList);
        }

    }

    private void loadChild(ArrayList<Student> students) {
        childList = new ArrayList<Student>();
        for(Student s : students){
            childList.add(s);
        }
    }
}
