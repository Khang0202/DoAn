package com.doannganh.warningmap.Activity.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doannganh.warningmap.Activity.Admin.ChangeRoleActivity;
import com.doannganh.warningmap.Object.StaticClass;
import com.doannganh.warningmap.Object.User;
import com.doannganh.warningmap.R;

import java.util.ArrayList;

public class UserChangeRoleAdapter extends RecyclerView.Adapter<UserChangeRoleAdapter.Viewholder> {
    ArrayList<User> items;
    Context context;
    public UserChangeRoleAdapter(ArrayList<User> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_user_change, parent, false);
        context = parent.getContext();
        return new Viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.textUserId.setText(String.valueOf(items.get(position).getId()));
        holder.textName.setText(items.get(position).getLastName());
        holder.textUsername.setText(items.get(position).getUsername());
        holder.textEmail.setText(items.get(position).getEmail());
        if (items.get(position).getRole().getId() == 2) {
            holder.textRole.setText("USER");
        }
        else if (items.get(position).getRole().getId() == 3) {
            holder.textRole.setText("COLLABORATOR");
        }

        holder.itemView.setOnClickListener(v -> {
            StaticClass.userChangeRole = items.get(position);
            holder.itemView.getContext().startActivity(new Intent(holder.itemView.getContext(),
                    ChangeRoleActivity.class));
        });

    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {


        TextView textUserId, textName, textUsername, textEmail, textRole, txtLock;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            textUserId = itemView.findViewById(R.id.textUserId);
            textName = itemView.findViewById(R.id.textName);
            textUsername = itemView.findViewById(R.id.textUsername);
            textEmail = itemView.findViewById(R.id.textEmail);
            textRole = itemView.findViewById(R.id.textRole);
        }
    }
}
