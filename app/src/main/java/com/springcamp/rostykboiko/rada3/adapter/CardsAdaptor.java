package com.springcamp.rostykboiko.rada3.adapter;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.view.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class CardsAdaptor extends RecyclerView.Adapter<CardsAdaptor.CardViewHolder> {
    private Context mContext;
    private ArrayList<String> optionsList = new ArrayList<>();
    private List<Survey> surveyList;
    private RecyclerView optionsRecycler;
    private OptionListAdapter optionListAdapter;

    class CardViewHolder extends RecyclerView.ViewHolder {
        private TextView title;

        CardViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.title);
            optionsRecycler = (RecyclerView) view.findViewById(R.id.card_option_recycler);
            optionListAdapter = new OptionListAdapter(optionsList);


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

        prepareMovieData();

        holder.title.setText(survey.getSyrveyTitle());
        optionsRecycler.setAdapter(optionListAdapter);
    }

    private void prepareMovieData() {
        optionsList.add("option1");
        optionsList.add("option2");
        optionsList.add("option3");

        optionListAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return surveyList.size();
    }
}