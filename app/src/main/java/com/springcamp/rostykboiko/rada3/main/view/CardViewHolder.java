package com.springcamp.rostykboiko.rada3.main.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Survey;

import butterknife.ButterKnife;

/**
 * Created by rostykboiko on 13.04.2017.
 */

public class CardViewHolder extends RecyclerView.ViewHolder {

    public interface QuestionCardCallback {
        void onDeleteCard(int position);
    }

    private OptionCardAdapter optionCardAdapter = new OptionCardAdapter();
    private TextView title;
    private RecyclerView optionsRecycler;
    private ImageView deleteBtn;

    @NonNull
    private QuestionCardCallback callback;

    CardViewHolder(View view, @NonNull Context context, @NonNull QuestionCardCallback callback) {
        super(view);
        this.callback = callback;
        title = (TextView) view.findViewById(R.id.title);
        optionsRecycler = (RecyclerView) view.findViewById(R.id.card_option_recycler);
        deleteBtn = (ImageView) view.findViewById(R.id.close_icon);

        RecyclerView.LayoutManager mListManager = new LinearLayoutManager(context);
        optionsRecycler.setLayoutManager(mListManager);
        optionsRecycler.setItemAnimator(new DefaultItemAnimator());
        optionsRecycler.setAdapter(optionCardAdapter);
    }

    public void setSurvey(@NonNull Survey survey) {
        optionCardAdapter.setOnePositiveOption(true);
        optionCardAdapter.setParticipantsCount(survey.getParticipantsCount());
        optionCardAdapter.setOptions(survey.getSurveyOptionList());
    }

    public void setSurvayName(String surveyTitle) {
        title.setText(surveyTitle);
    }

}
