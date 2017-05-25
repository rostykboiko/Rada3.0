package com.springcamp.rostykboiko.rada3.main.view;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Option;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.User;
import com.springcamp.rostykboiko.rada3.shared.utlils.Utils;

import java.util.ArrayList;
import java.util.List;

class OptionCardAdapter extends RecyclerView.Adapter<OptionCardAdapter.ViewHolder> {
    private int participantsCount;

    private boolean onePositiveOption;
    private ArrayList<Option> optionsList = new ArrayList<>();

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView optionItem;
        private TextView answerCount;
        private ProgressBar progressBar;
        private ImageView radioBtn;

        ImageView participantsIcon1;
        ImageView participantsIcon2;
        ImageView participantsIcon3;

        ViewHolder(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            optionItem = (TextView) view.findViewById(R.id.optionItemDialog);
            answerCount = (TextView) view.findViewById(R.id.percentTv);
            radioBtn = (ImageView) view.findViewById(R.id.checkBox);
            participantsIcon1 = (ImageView) view.findViewById(R.id.participants_icon1);
            participantsIcon2 = (ImageView) view.findViewById(R.id.participants_icon2);
            participantsIcon3 = (ImageView) view.findViewById(R.id.participants_icon3);
        }
    }

    OptionCardAdapter() {
    }

    void setOptions(@NonNull List<Option> optionsList) {
        this.optionsList.clear();
        this.optionsList.addAll(optionsList);
        notifyDataSetChanged();
    }

    void setParticipantsCount(int participantsCount) {
        this.participantsCount = participantsCount;
    }

    void setOnePositiveOption(boolean onePositiveOption) {
        this.onePositiveOption = onePositiveOption;
    }

    @Override
    public OptionCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_option_card, parent, false);

        return new OptionCardAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OptionCardAdapter.ViewHolder holder, int position) {
        Option option = optionsList.get(position);
        double answersNumber = Utils.longToInt(option.getAnswerCounter());

        //  System.out.println("participantsList " + option.getParticipantsList().get(position));

        setParticipantsIcons(holder, position);

        int result = (int) (answersNumber / participantsCount * 100);

        holder.optionItem.setText(option.getOptionTitle());
        holder.answerCount.setText(String.valueOf((int) answersNumber));
        holder.progressBar.setProgress(result);
        if (onePositiveOption) {
            holder.radioBtn.setImageResource(R.drawable.ic_material_radio_blank);
        } else {
            holder.radioBtn.setImageResource(R.drawable.ic_material_checkbox_blank);
        }
    }

    @Override
    public int getItemCount() {
        return optionsList.size();
    }


    private void setParticipantsIcons(final OptionCardAdapter.ViewHolder holder, int position) {
        holder.participantsIcon1.setVisibility(View.GONE);
        holder.participantsIcon2.setVisibility(View.GONE);
        holder.participantsIcon3.setVisibility(View.GONE);

        System.out.println("participantsList size " + optionsList.get(position).getParticipantsList());

        if (optionsList != null && optionsList.get(position) != null && optionsList.get(position).getParticipantsList().size() != 0) {
            holder.participantsIcon1.setVisibility(View.VISIBLE);
            Glide.with(holder.participantsIcon1.getContext()).load(
                    optionsList.get(position).getParticipantsList().get(0))
                    .asBitmap().into(new BitmapImageViewTarget(holder.participantsIcon1) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(holder.participantsIcon1.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    holder.participantsIcon1.setImageDrawable(circularBitmapDrawable);
                }
            });
        }

        if (optionsList != null && optionsList.get(position) != null && optionsList.get(position).getParticipantsList().size() == 2) {
            holder.participantsIcon2.setVisibility(View.VISIBLE);
            Glide.with(holder.participantsIcon2.getContext()).load(
                    optionsList.get(position).getParticipantsList().get(1))
                    .asBitmap().into(new BitmapImageViewTarget(holder.participantsIcon2) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(holder.participantsIcon2.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    holder.participantsIcon2.setImageDrawable(circularBitmapDrawable);
                }
            });
        }

        if (optionsList != null && optionsList.get(position) != null && optionsList.get(position).getParticipantsList().size() == 3) {
            holder.participantsIcon3.setVisibility(View.VISIBLE);
            Glide.with(holder.participantsIcon3.getContext()).load(
                    optionsList.get(position).getParticipantsList().get(2))
                    .asBitmap().into(new BitmapImageViewTarget(holder.participantsIcon3) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(holder.participantsIcon3.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    holder.participantsIcon3.setImageDrawable(circularBitmapDrawable);
                }
            });
        }
        if (optionsList.get(position).getParticipantsList().size() > 3) {
            String count = "+" + (optionsList.get(position).getParticipantsList().size() - 3);
            holder.answerCount.setText(count);
        }


    }
}