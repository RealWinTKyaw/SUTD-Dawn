package com.example.sutddawn.ui.marketplace;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sutddawn.R;
import com.example.sutddawn.user_classes.Student;

import java.util.List;
import java.util.Map;

public class MyExpandableListAdaptorIndv extends BaseExpandableListAdapter {
    private Context context;
    private Map<String, List<Student>> cohortCollection;
    private List<String> parentList;

    public MyExpandableListAdaptorIndv(Context context, List<String> parentList, Map<String, List<Student>> cohortCollection){
        this.context = context;
        this.parentList = parentList;
        this.cohortCollection = cohortCollection;
    }

    @Override
    public int getGroupCount() {
        return cohortCollection.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return cohortCollection.get(parentList.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return parentList.get(i);
    }

    @Override
    public Object getChild(int i, int j) {
        return cohortCollection.get(parentList.get(i)).get(j);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int j) {
        return j;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String cohortName = parentList.get(i);
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.marketplace_cohort, null);
        }
        TextView item = view.findViewById(R.id.cohort);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(cohortName);
        return view;
    }

    @Override
    public View getChildView(int i, int j, boolean b, View view, ViewGroup viewGroup) {
        Student s = (Student) getChild(i, j);
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.marketplace_indv_child, null);
        }
        TextView item = view.findViewById(R.id.individuals);
        ImageView profile = view.findViewById(R.id.profile);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(s.getName());
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int j) {
        return true;
    }
}
