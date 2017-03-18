package com.springcamp.rostykboiko.rada3.main.presenter;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Survey;

import java.util.ArrayList;
import java.util.List;

public class CardsAdaptor extends RecyclerView.Adapter<CardsAdaptor.CardViewHolder> {
    private List<String> optionsList = new ArrayList<>();
    private List<Survey> surveyList;
    private Context mContext;

    private OptionCardAdapter optionCardAdapter;

    class CardViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private RecyclerView optionsRecycler;

        CardViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.title);
            optionsRecycler = (RecyclerView) view.findViewById(R.id.card_option_recycler);
            optionCardAdapter = new OptionCardAdapter(optionsList);

            RecyclerView.LayoutManager mListManager = new LinearLayoutManager(mContext.getApplicationContext());
            optionsRecycler.setLayoutManager(mListManager);
            optionsRecycler.setItemAnimator(new DefaultItemAnimator());
            optionsRecycler.setAdapter(optionCardAdapter);
        }
    }

    public CardsAdaptor(Context mContext, List<Survey> surveyList, List<String> optionsList) {
        this.mContext = mContext;
        this.surveyList = surveyList;
        this.optionsList = optionsList;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.survey_card, parent, false);

        return new CardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CardViewHolder holder, int position) {
        Survey survey = surveyList.get(position);

        holder.title.setText(survey.getSurveyTitle());
        holder.optionsRecycler.setAdapter(optionCardAdapter);
    }

    @Override
    public int getItemCount() {
        return surveyList.size();
    }
}