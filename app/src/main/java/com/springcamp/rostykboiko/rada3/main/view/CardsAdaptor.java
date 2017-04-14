package com.springcamp.rostykboiko.rada3.main.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Survey;
import com.springcamp.rostykboiko.rada3.shared.utlils.GoogleAccountAdapter;

import java.util.ArrayList;
import java.util.List;

class CardsAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface QuestionsCardCallback {
        void onCardDeleted(@NonNull Survey survey);
    }

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
            }
        });
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        Survey survey = surveyList.get(position);
        CardViewHolder viewHolder = (CardViewHolder) holder;
        viewHolder.setSurvayName(survey.getSurveyTitle());
        viewHolder.setSurvey(survey);
    }

    private void removeSurvey(String surveyId) {
        System.out.println("SurveyID surveyList " + surveyId);

        FirebaseDatabase.getInstance().getReference()
                .child("Survey")
                .child(surveyId)
                .removeValue();
    }

    @Override
    public int getItemCount() {
        return surveyList.size();
    }

    public void setSurveyList(@NonNull List<Survey> surveyList) {
        //TODO check if list contains other elements
        if(!this.surveyList.containsAll(surveyList)) {
            this.surveyList.addAll(surveyList);
            notifyDataSetChanged();
        }
    }
}