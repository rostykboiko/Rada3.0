package com.springcamp.rostykboiko.rada3.answer.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Option;

import java.util.ArrayList;

class OptionDialogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface AnswerCheckCallback {
        void onAnswerChecked(@NonNull Option option);
    }

    @NonNull
    private AnswerCheckCallback callback;

    private ArrayList<Option> optionsList = new ArrayList<>();

    OptionDialogAdapter(ArrayList<Option> optionsList, @NonNull AnswerCheckCallback callback) {
        this.optionsList = optionsList;
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
        AnswerViewHolder viewHolder = (AnswerViewHolder) holder;
        viewHolder.setOptionTitle(optionsList.get(position).getOptionTitle());
//        holder.checkBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (holder.checkBox.isChecked())
//                    optionsList.get(position).setChecked(true);
//                else optionsList.get(position).setChecked(false);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return optionsList.size();
    }

    ArrayList<Option> getOptionsList() {
        return optionsList;
    }

}
