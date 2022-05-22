package com.example.sutddawn.ui.marketplace;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sutddawn.R;

public class RecyclerViewFragment extends Fragment {

    private static final String TAG = "RecyclerViewFragment";

    protected RecyclerView mRecyclerView;
    protected CustomAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected String[] mDataset;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDataset();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recycle_view_frag, container, false);
        rootView.setTag(TAG);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        mAdapter = new CustomAdapter(mDataset);
        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        return rootView;
    }

    private void initDataset() {
        mDataset = new String[16];
        mDataset[0] = "Group1";
        mDataset[1] = "Group2";
        mDataset[2] = "Group3";
        mDataset[3] = "Group4";
        mDataset[4] = "Group5";
        mDataset[5] = "Group6";
        mDataset[6] = "Group7";
        mDataset[7] = "Group8";
        mDataset[8] = "Group9";
        mDataset[9] = "Group10";
        mDataset[10] = "Group11";
        mDataset[11] = "Group12";
        mDataset[12] = "Group13";
        mDataset[13] = "Group14";
        mDataset[14] = "Group15";
        mDataset[15] = "Group16";
    }

    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
        private static final String TAG = "CustomAdapter";

        private String[] mDataSet;

        public class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView textView;

            public ViewHolder(View v) {
                super(v);
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "Item " + getAdapterPosition() + " clicked.", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                    }
                });

                textView = (TextView) v.findViewById(R.id.textView);
            }

            public TextView getTextView() {
                return textView;
            }
        }

        public CustomAdapter(String[] dataSet) {
            mDataSet = dataSet;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.grp_row_list, viewGroup, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            Log.d(TAG, "Element " + position + " set.");

            viewHolder.getTextView().setText(mDataSet[position]);
        }

        @Override
        public int getItemCount() {
            return mDataSet.length;
        }
    }
}
