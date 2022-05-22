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
import com.example.sutddawn.ui.userprofile.UserProfile;
import com.example.sutddawn.user_classes.Student;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class StudentAdapter extends FirebaseRecyclerAdapter<Student, StudentAdapter.studentViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */

    public StudentAdapter(@NonNull FirebaseRecyclerOptions options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull studentViewHolder holder, int position, @NonNull Student model) {

        holder.Name.setText(model.getName());
        holder.Module.setText(model.modulesToString(model.getModules()));
        holder.Description.setText(model.getStudentBio());
    }

    @NonNull
    @Override
    public studentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder, parent, false);
        return new StudentAdapter.studentViewHolder(view);

    }

    class studentViewHolder extends RecyclerView.ViewHolder { TextView Name, Module, Description;

        public studentViewHolder(@NonNull View itemView) {

            super(itemView);
            Name = itemView.findViewById(R.id.Name);
            Module = itemView.findViewById(R.id.Module);
            Description = itemView.findViewById(R.id.Description);
            Context context = itemView.getContext();
            itemView.setOnClickListener(view -> {
                //passes a reference to selected student to UserProfile
                int position = getAdapterPosition();
                Student student = getItem(position);
                final Intent intent = new Intent(context, UserProfile.class);
                intent.putExtra("selected", student.getFirebaseID());
                context.startActivity(intent);
            });
        }
    }
}
