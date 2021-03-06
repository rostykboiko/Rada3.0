package com.springcamp.rostykboiko.rada3.bottomSheet.view;

import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Survey;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.User;

import java.util.ArrayList;

class ParticipantsSheetAdapter extends RecyclerView.Adapter<ParticipantsSheetAdapter.ViewHolder> {

    private ArrayList<User> userList = new ArrayList<>();
    private Survey survey;

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView userNameItem;
        private TextView userEmailItem;
        private ImageView profileImage;
        private RelativeLayout checked;

        ViewHolder(View view) {
            super(view);
            userNameItem = (TextView) view.findViewById(R.id.userNameItem);
            userEmailItem = (TextView) view.findViewById(R.id.userEmailItem);
            profileImage = (ImageView) view.findViewById(R.id.profile_img_view);
            checked = (RelativeLayout) view.findViewById(R.id.checked);
        }
    }

    ParticipantsSheetAdapter(Survey surveyId, ArrayList<User> userList) {
        this.survey = surveyId;
        this.userList = userList;
    }

    @Override
    public ParticipantsSheetAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_participant, parent, false);

        return new ParticipantsSheetAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ParticipantsSheetAdapter.ViewHolder holder, int position) {
        holder.userNameItem.setText(userList.get(position).getUserName());
        holder.userEmailItem.setText(userList.get(position).getUserEmail());

        Glide.with(holder.profileImage.getContext()).load(userList.get(position).getUserProfileIcon())
                .asBitmap().into(new BitmapImageViewTarget(holder.profileImage) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(holder.profileImage.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                holder.profileImage.setImageDrawable(circularBitmapDrawable);
            }
        });

        System.out.println("StateCheck accID " + userList.get(position).getAccountID()
                + " part " + survey.getParticipantsList());
        if (survey.getParticipantsList().contains(userList.get(position).getAccountID())) {
            holder.checked.setVisibility(View.VISIBLE);

        } else {
            holder.checked.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    void setFilter(ArrayList<User> newList) {
        userList = new ArrayList<>();
        userList.addAll(newList);
        notifyDataSetChanged();
    }
}