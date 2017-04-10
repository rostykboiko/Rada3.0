package com.springcamp.rostykboiko.rada3.answer.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.springcamp.rostykboiko.rada3.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

class AnswerViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.optionItemDialog)
    TextView optionItem;

    @BindView(R.id.checkbox)
    CheckBox checkBox;

    interface CheckAnswerCallback {
        void onAnswerChecked(int position);
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
}

