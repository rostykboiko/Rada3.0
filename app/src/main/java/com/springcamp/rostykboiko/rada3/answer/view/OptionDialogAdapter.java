package com.springcamp.rostykboiko.rada3.answer.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Option;

import java.util.ArrayList;
import java.util.List;

class OptionDialogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    interface AnswerCheckCallback {
        void onAnswerChecked(@NonNull Option option);
    }

    @NonNull
    private AnswerCheckCallback callback;

    private ArrayList<Option> optionsList = new ArrayList<>();

    OptionDialogAdapter(@NonNull AnswerCheckCallback callback) {
        this.callback = callback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_option_dialog, parent, false);
        return new AnswerViewHolder(itemView, new AnswerViewHolder.CheckAnswerCallback() {
            @Override
            public void onAnswerChecked(int postition) {
                callback.onAnswerChecked(optionsList.get(postition));
            }
        });
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((AnswerViewHolder) holder).optionItem.setText(optionsList.get(position).getOptionTitle());
        System.out.println("json answer adapter " + optionsList.get(position).getOptionTitle());
    }

    @Override
    public int getItemCount() {
        return optionsList.size();
    }

    ArrayList<Option> getOptionsList() {
        return optionsList;
    }

    public void setOptionsList(@NonNull List<Option> options) {
        this.optionsList.addAll(options);
        notifyDataSetChanged();
    }
}
