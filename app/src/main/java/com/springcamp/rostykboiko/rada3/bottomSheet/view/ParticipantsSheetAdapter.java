package com.springcamp.rostykboiko.rada3.bottomSheet.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;
import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.User;
import com.springcamp.rostykboiko.rada3.shared.utlils.GoogleAccountAdapter;

import java.util.ArrayList;

class ParticipantsSheetAdapter extends RecyclerView.Adapter<ParticipantsSheetAdapter.ViewHolder> {

    private ArrayList<User> userList = new ArrayList<>();

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView userEmailItem;
        private ImageView profileImage;

        ViewHolder(View view) {
            super(view);
            userEmailItem = (TextView) view.findViewById(R.id.userEmailItem);
            profileImage = (ImageView) view.findViewById(R.id.profile_img_view);
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
    public void onBindViewHolder(final ParticipantsSheetAdapter.ViewHolder holder, int position) {
        holder.userEmailItem.setText(userList.get(position).getUserEmail());
//        Glide.with(holder.profileImage.getContext())
//                .load(userList.get(position).getUserProfileIcon()).into(holder.profileImage);

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

    private void setProfileImage() {
        if (GoogleAccountAdapter.getProfileIcon() != null) {
            DrawerImageLoader.init(new AbstractDrawerImageLoader() {
                @Override
                public void set(ImageView imageView, Uri uri, Drawable placeholder, String tag) {
                    Glide.with(imageView.getContext()).load(GoogleAccountAdapter.getProfileIcon()).into(imageView);
                }

                @Override
                public void cancel(ImageView imageView) {
                    Glide.clear(imageView);
                }
            });
        }
    }
}