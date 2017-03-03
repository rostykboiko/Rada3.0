package com.springcamp.rostykboiko.rada3.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.springcamp.rostykboiko.rada3.R;

import java.util.List;

public class SurveyListAdapter extends RecyclerView.Adapter<SurveyListAdapter.ViewHolder>{

    private List<Survey> surveyList;

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;

        ViewHolder(View view) {
            super(view);
        }
    }

    public SurveyListAdapter(List<Survey> surveyList) {
        this.surveyList = surveyList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.option_list_row, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Survey survey = surveyList.get(position);
        holder.title.setText(survey.getSyrveyTitle());
    }

    @Override
    public int getItemCount() {
        return surveyList.size();
    }
}
