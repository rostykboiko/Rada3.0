package com.springcamp.rostykboiko.rada3.main.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.springcamp.rostykboiko.rada3.R;

import java.util.ArrayList;

class OptionCardAdapter extends RecyclerView.Adapter<OptionCardAdapter.ViewHolder> {

    private ArrayList<String> optionsList = new ArrayList<>();

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView optionItem;

        ViewHolder(View view) {
            super(view);
            optionItem = (TextView) view.findViewById(R.id.optionItem);
        }
    }

    OptionCardAdapter() {
    }

    OptionCardAdapter(ArrayList<String> optionsList) {
        this.optionsList = optionsList;
    }

    @Override
    public OptionCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.option_card_row, parent, false);

        return new OptionCardAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OptionCardAdapter.ViewHolder holder, int position) {
        String option = optionsList.get(position);
        holder.optionItem.setText(option);
    }

    @Override
    public int getItemCount() {
        return optionsList.size();
    }
}