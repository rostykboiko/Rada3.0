package com.springcamp.rostykboiko.rada3.shared.utlils;

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
        private TextView optionItem;

        ViewHolder(View view) {
            super(view);
            optionItem = (TextView) view.findViewById(R.id.optionItem);
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
        holder.optionItem.setText(option);
    }

    @Override
    public int getItemCount() {
        return optionsList.size();
    }
}