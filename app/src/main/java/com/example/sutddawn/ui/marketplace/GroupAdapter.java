package com.example.sutddawn.ui.marketplace;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sutddawn.R;
import com.example.sutddawn.ui.groupprofile.GroupProfile;
import com.example.sutddawn.user_classes.Group;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class GroupAdapter extends FirebaseRecyclerAdapter<Group, GroupAdapter.groupViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */

    public GroupAdapter(@NonNull FirebaseRecyclerOptions options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull groupViewHolder holder, int position, @NonNull Group model) {

        holder.Name.setText(model.getName());
        holder.Module.setText(model.getModule());
        holder.Description.setText(model.getDescription());

    }

    @NonNull
    @Override
    public groupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder, parent, false);
        return new GroupAdapter.groupViewHolder(view);

    }

    class groupViewHolder extends RecyclerView.ViewHolder { TextView Name, Module, Description;

        public groupViewHolder(@NonNull View itemView) {

            super(itemView);
            Name = itemView.findViewById(R.id.Name);
            Module = itemView.findViewById(R.id.Module);
            Description = itemView.findViewById(R.id.Description);
            Context context = itemView.getContext();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //passes reference of selected group to GroupProfile
                    int position = getAdapterPosition();
                    Group group = getItem(position);
                    final Intent intent = new Intent(context, GroupProfile.class);
                    intent.putExtra("selected", group.getName());
                    context.startActivity(intent);
                }
            });
        }
    }
}
