package com.springcamp.rostykboiko.rada3.editor.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.User;

import java.util.ArrayList;

class ParticipantsSheetAdapter extends RecyclerView.Adapter<ParticipantsSheetAdapter.ViewHolder> {

    private ArrayList<User> userList = new ArrayList<>();

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView userEmailItem;

        ViewHolder(View view) {
            super(view);
            userEmailItem = (TextView) view.findViewById(R.id.userEmailItem);
        }
    }

    ParticipantsSheetAdapter(ArrayList<User> userList) {
        this.userList = userList;
    }

    @Override
    public ParticipantsSheetAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_participant, parent, false);

        return new ParticipantsSheetAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ParticipantsSheetAdapter.ViewHolder holder, int position) {
        holder.userEmailItem.setText(userList.get(position).getUserEmail());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}