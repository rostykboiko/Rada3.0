package com.springcamp.rostykboiko.rada3.answer.view;

import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.springcamp.rostykboiko.rada3.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

/**
 * Created by rostykboiko on 06.04.2017.
 */

public class AnswerViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.optionItemDialog)
    TextView optionItem;

    @BindView(R.id.checkbox)
    CheckBox checkBox;

    interface CheckAnswerCallback {
        void onAnswerChecked(int postition);
    }

    @NonNull
    private CheckAnswerCallback callback;

    AnswerViewHolder(View view, @NonNull CheckAnswerCallback callback) {
        super(view);
        ButterKnife.bind(this, itemView);
        this.callback = callback;
    }

    @OnCheckedChanged(R.id.checkbox)
    void checkAnswer(boolean checked){
        if(checked){
            callback.onAnswerChecked(getAdapterPosition());
        }
    }

    public void setOptionTitle(@NonNull String optionTitle){
        optionItem.setText(optionTitle);
    }
}

