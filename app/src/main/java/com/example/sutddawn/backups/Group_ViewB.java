package com.example.sutddawn.backups;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sutddawn.R;
import com.example.sutddawn.ui.marketplace.MyExpandableListAdaptor;
import com.example.sutddawn.ui.groupprofile.GroupProfile;
import com.example.sutddawn.ui.marketplace.Individual_View;
import com.example.sutddawn.user_classes.Group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Group_ViewB extends AppCompatActivity {

    List<String> parentList; //cohort list
    List<String> childList; //group list in each cohort
    Map<String, List<String>> cohortCollection; //to link parent and child
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;

    Button createGroup;
    HashMap<String, ArrayList<Group>> list_of_groups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marketplace_group_view);

        list_of_groups = new HashMap<String,ArrayList<Group>>();

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
                            Intent i = new Intent(com.example.sutddawn.backups.Group_ViewB.this, Individual_View.class);
                            startActivity(i);
                        }
                    });
                } else {
                    //No Change
                }
            }
        });

        //get the handle of expandable list view
        expandableListView = findViewById(R.id.cohortList);
        //instantiate adapter and pass 3 things(context, list, map) into the constructor
        expandableListAdapter = new MyExpandableListAdaptor(this, parentList, cohortCollection);
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
                String selected = expandableListAdapter.getChild(i, j).toString();
                //toast message to show child element
                Toast.makeText(getApplicationContext(), "Selected: " + selected, Toast.LENGTH_SHORT).show();

                Intent toGroupProfile = new Intent(com.example.sutddawn.backups.Group_ViewB.this, GroupProfile.class);
                toGroupProfile.putExtra("groupName", selected);
                startActivityForResult(toGroupProfile,100);
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
//        String[] cohort1_grp = {"50.004\n2D\nGroup BST\nCapacity:5/7", "30.007\n2D\nCore Four\nCapacity:2/7"};
//        String[] cohort2_grp = {"50.004\n2D\nGroup Heapsort\nCapacity:7/7", "50.002\n2D\nFab Five\nCapacity:5/7", "50.001\n2D\nRangers\nCapacity:2/7"};
//        String[] cohort3_grp = {"50.004\nAI Trainer\nCapacity:1/7"};
//        String[] cohort4_grp = {"50.004\n1D\nGroup Java4Life\nCapacity:3/7", "50.001\n2D\nGirlGang\nCapacity:5/7"};
//        String[] cohort5_grp = {"50.004\n2D\nGroup Hope\nCapacity:4/7", "50.003\n2D\nCoven\nCapacity:2/7"};

        // DEBUGGING START
        ArrayList<Group> cohort1 = new ArrayList<>();
        cohort1.add(new Group(8, "Tic Tac Tones", "r"));
        cohort1.add(new Group(8, "Algo study group","r"));
        cohort1.add(new Group(8, "Disneyland","r"));
        cohort1.add(new Group(8, "Epic","r"));

        ArrayList<Group> cohort2 = new ArrayList<>();
        cohort2.add(new Group(8, "Space Ponies","r"));
        cohort2.add(new Group(7, "Engineered","r"));
        cohort2.add(new Group(8, "Dawners","r"));
        cohort2.add(new Group(8, "Kotlin","r"));

        list_of_groups.put("Cohort 1",cohort1);
        list_of_groups.put("Cohort 2",cohort2);
//        list_of_groups.put("Cohort 3",cohort3);
        // DEBUGGING END

        //instantiate
        cohortCollection = new HashMap<String, List<String>>();
        for(String group: parentList){
            //load each child in childList to their parentList
            if(group.equals("Cohort 1")){
                loadChild(list_of_groups.get("Cohort 1"));
            }
            else if(group.equals("Cohort 2")){
                loadChild(list_of_groups.get("Cohort 2"));
            }
            else if(group.equals("Cohort 3")){
//                loadChild(list_of_groups.get("Cohort 3"));
            }
            else if(group.equals("Cohort 4")){
//                loadChild((list_of_groups));
            }
            else{
//                loadChild((list_of_groups));
            }
            cohortCollection.put(group, childList);
        }

    }

    private void loadChild(ArrayList<Group> groups) {
        childList = new ArrayList<String>();
        for(Group g : groups){
            childList.add(g.getName());
        }
    }
}