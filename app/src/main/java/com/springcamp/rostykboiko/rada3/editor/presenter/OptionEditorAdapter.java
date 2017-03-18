package com.springcamp.rostykboiko.rada3.editor.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.springcamp.rostykboiko.rada3.R;

import java.util.List;

public class OptionEditorAdapter extends RecyclerView.Adapter<OptionEditorAdapter.ViewHolder> {

    private List<String> optionsList;

    class ViewHolder extends RecyclerView.ViewHolder {
        private EditText optionItem;

        ViewHolder(View view) {
            super(view);
            optionItem = (EditText) view.findViewById(R.id.optionItem);
        }
    }

    public OptionEditorAdapter(List<String> optionsList) {
        this.optionsList = optionsList;
    }

    @Override
    public OptionEditorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.option_ed_row, parent, false);

        return new OptionEditorAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OptionEditorAdapter.ViewHolder holder, int position) {
        String option = optionsList.get(position);
        holder.optionItem.setHint(option);
    }



    @Override
    public int getItemCount() {
        return optionsList.size();
    }
}