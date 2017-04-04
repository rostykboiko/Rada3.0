package com.springcamp.rostykboiko.rada3.main.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Option;

import java.util.ArrayList;

class OptionCardAdapter extends RecyclerView.Adapter<OptionCardAdapter.ViewHolder> {

    private ArrayList<Option> optionsList = new ArrayList<>();

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView optionItem;
        private ProgressBar progressBar;

        ViewHolder(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            optionItem = (TextView) view.findViewById(R.id.optionItemDialog);
        }
    }

    OptionCardAdapter() {
    }

    OptionCardAdapter(ArrayList<Option> optionsList) {
        this.optionsList = optionsList;
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
        holder.optionItem.setText(option.getOptionTitle());

        holder.progressBar.setProgress(longToInt(option.getAnswerCounter()));
    }

    private static int longToInt(long answerCountLong) {
        int answerCountInt = (int)answerCountLong;
        if ((long)answerCountInt != answerCountLong) {
            throw new IllegalArgumentException(answerCountLong + " cannot be cast to int without changing its value.");
        }
        return answerCountInt;
    }

    @Override
    public int getItemCount() {
        return optionsList.size();
    }
}