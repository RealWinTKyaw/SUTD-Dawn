package com.example.sutddawn.ui.marketplace;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sutddawn.R;
import com.example.sutddawn.ui.marketplace.Grid_Grp;
import com.example.sutddawn.ui.marketplace.RecyclerViewFragment;

import java.util.List;
import java.util.Map;

public class MyExpandableListAdaptor extends BaseExpandableListAdapter {

    private Context context;
    private Map<String, List<String>> cohortCollection;
    private List<String> parentList;
    protected RecyclerViewFragment.CustomAdapter mAdapter;

    public MyExpandableListAdaptor(Context context, List<String> parentList, Map<String, List<String>> cohortCollection){
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
        String groupName = getChild(i, j).toString();
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.marketplace_grp_child, null);
        }
//        RecyclerView item = view.findViewById(R.id.recyclerView);
//        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
//        llm.setOrientation(LinearLayoutManager.VERTICAL);
//        item.setLayoutManager(llm);

//        RecyclerView item = view.findViewById(R.id.recyclerView);
//        item.setAdapter(mAdapter);

        TextView item = view.findViewById(R.id.groups);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(groupName);

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int j) {
        return true;
    }
}
