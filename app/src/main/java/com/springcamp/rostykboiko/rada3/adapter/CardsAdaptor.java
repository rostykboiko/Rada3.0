package com.springcamp.rostykboiko.rada3.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.springcamp.rostykboiko.rada3.R;

import java.util.ArrayList;
import java.util.List;

public class CardsAdaptor extends RecyclerView.Adapter<CardsAdaptor.CardViewHolder> {
    private Context mContext;

    private ArrayList<String> optionsList = new ArrayList<>();
    private List<Survey> surveyList;


    class CardViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        private RecyclerView optionsRecycler;
        private OptionListAdapter optionListAdapter;

        CardViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            optionsRecycler  = (RecyclerView) view.findViewById(R.id.card_option_recycler);
            optionListAdapter = new OptionListAdapter(optionsList);
            prepareMovieData();
        }
    }


    public CardsAdaptor(Context mContext, List<Survey> surveyList, ArrayList<String> optionsList) {
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
        holder.title.setText(survey.getSyrveyTitle());
        holder.optionsRecycler.setAdapter(holder.optionListAdapter);
    }

    private void prepareMovieData() {
        optionsList.add("option1");
        optionsList.add("option2");
        optionsList.add("option3");
    }

    @Override
    public int getItemCount() {
        return surveyList.size();
    }
}