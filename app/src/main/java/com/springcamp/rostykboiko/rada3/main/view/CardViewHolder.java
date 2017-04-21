package com.springcamp.rostykboiko.rada3.main.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Survey;

class CardViewHolder extends RecyclerView.ViewHolder {

    private OptionCardAdapter optionCardAdapter = new OptionCardAdapter();
    private TextView title;

    CardViewHolder(View view, @NonNull Context context, @NonNull final QuestionCardCallback callback) {
        super(view);
        title = (TextView) view.findViewById(R.id.title);
        RecyclerView optionsRecycler = (RecyclerView) view.findViewById(R.id.card_option_recycler);
        ImageView deleteBtn = (ImageView) view.findViewById(R.id.close_icon);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onDeleteCard(getAdapterPosition());
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onCardClick(getAdapterPosition());
            }
        });

        RecyclerView.LayoutManager mListManager = new LinearLayoutManager(context);
        optionsRecycler.setLayoutManager(mListManager);
        optionsRecycler.setItemAnimator(new DefaultItemAnimator());
        optionsRecycler.setAdapter(optionCardAdapter);
    }

    public void setSurvey(@NonNull Survey survey) {
        optionCardAdapter.setOnePositiveOption(survey.isSurveySingleOption());
        optionCardAdapter.setParticipantsCount(survey.getParticipantsCount());
        optionCardAdapter.setOptions(survey.getSurveyOptionList());
    }

    void setSurveyName(String surveyTitle) {
        title.setText(surveyTitle);
    }

    interface QuestionCardCallback {
        void onDeleteCard(int position);

        void onCardClick(int position);

    }

}
