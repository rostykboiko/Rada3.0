package com.springcamp.rostykboiko.rada3.main.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Survey;

import java.util.ArrayList;
import java.util.List;

class CardsAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @NonNull
    private QuestionsCardCallback callback;

    private Context mContext;

    private ArrayList<Survey> surveyList = new ArrayList<>();

    CardsAdaptor(Context mContext, @NonNull QuestionsCardCallback callback) {
        this.mContext = mContext;
        this.callback = callback;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.survey_card, parent, false);
        return new CardViewHolder(itemView, parent.getContext(), new CardViewHolder.QuestionCardCallback() {
            @Override
            public void onDeleteCard(int position) {
                callback.onCardDeleted(surveyList.get(position));
                surveyList.remove(position);
                notifyDataSetChanged();
            }

            @Override
            public void onCardClick(int position) {
                callback.onCardClick(surveyList.get(position));
                System.out.println("onCardClick position " + position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        Survey survey = surveyList.get(position);
        CardViewHolder viewHolder = (CardViewHolder) holder;
        viewHolder.setSurveyName(survey.getSurveyTitle());
        viewHolder.setSurvey(survey);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onCardClick(surveyList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return surveyList.size();
    }

    void setSurveyList(@NonNull List<Survey> surveyList) {
        if (!this.surveyList.containsAll(surveyList)) {
            this.surveyList.addAll(surveyList);
        }
    }

    interface QuestionsCardCallback {
        void onCardDeleted(@NonNull Survey survey);

        void onCardClick(@NonNull Survey survey);
    }
}