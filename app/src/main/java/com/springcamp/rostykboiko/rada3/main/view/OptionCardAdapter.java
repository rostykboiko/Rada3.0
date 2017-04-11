package com.springcamp.rostykboiko.rada3.main.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Option;
import com.springcamp.rostykboiko.rada3.shared.utlils.Utils;

import java.util.ArrayList;

class OptionCardAdapter extends RecyclerView.Adapter<OptionCardAdapter.ViewHolder> {
    private int participantsCount;
    private boolean onePositiveOption;
    private ArrayList<Option> optionsList = new ArrayList<>();

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView optionItem;
        private TextView answerCount;
        private ProgressBar progressBar;
        private ImageView radioBtn;

        ViewHolder(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            optionItem = (TextView) view.findViewById(R.id.optionItemDialog);
            answerCount = (TextView) view.findViewById(R.id.percentTv);
            radioBtn = (ImageView) view.findViewById(R.id.radioBtn);
        }
    }

    OptionCardAdapter() {
    }

    OptionCardAdapter(ArrayList<Option> optionsList, int participantsCount, boolean onePositiveOption) {
        this.optionsList = optionsList;
        this.participantsCount = participantsCount;
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
        int result = (int) (answersNumber / participantsCount * 100);

        holder.optionItem.setText(option.getOptionTitle());
        holder.answerCount.setText(String.valueOf((int) answersNumber));
        holder.progressBar.setProgress(result);
        if (onePositiveOption){
            holder.radioBtn.setImageResource(R.drawable.ic_material_radio_blank);
        }else {
            holder.radioBtn.setImageResource(R.drawable.ic_material_checkbox_blank);
        }
    }

    @Override
    public int getItemCount() {
        return optionsList.size();
    }
}