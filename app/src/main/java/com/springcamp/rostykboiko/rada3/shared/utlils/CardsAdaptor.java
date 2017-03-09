package com.springcamp.rostykboiko.rada3.shared.utlils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.springcamp.rostykboiko.rada3.R;
import java.util.ArrayList;
import java.util.List;

public class CardsAdaptor extends RecyclerView.Adapter<CardsAdaptor.CardViewHolder> {
    private ArrayList<String> optionsList = new ArrayList<>();
    private List<Survey> surveyList;

    private OptionListAdapter optionListAdapter;

    class CardViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private RecyclerView optionsRecycler;

        CardViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.title);
            optionsRecycler = (RecyclerView) view.findViewById(R.id.card_option_recycler);
            optionListAdapter = new OptionListAdapter(optionsList);
        }
    }

    public CardsAdaptor(List<Survey> surveyList, OptionListAdapter optionListAdapter) {
        this.surveyList = surveyList;
        this.optionListAdapter = optionListAdapter;
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
        holder.optionsRecycler.setAdapter(optionListAdapter);
    }


    @Override
    public int getItemCount() {
        return surveyList.size();
    }
}