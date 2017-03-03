package com.springcamp.rostykboiko.rada3.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.springcamp.rostykboiko.rada3.R;

import java.util.ArrayList;

public class OptionListAdapter extends RecyclerView.Adapter<OptionListAdapter.ViewHolder>{

    private ArrayList<String> optionsList;

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
        }
    }

    public OptionListAdapter(ArrayList<String> optionsList) {
        this.optionsList = optionsList;
    }

    @Override
    public OptionListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.option_list_row, parent, false);

        return new OptionListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OptionListAdapter.ViewHolder holder, int position) {
        String option = optionsList.get(position);
        holder.title.setText(option);
    }

    @Override
    public int getItemCount() {
        return optionsList.size();
    }
}