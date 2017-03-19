package com.springcamp.rostykboiko.rada3.editor.presenter;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.springcamp.rostykboiko.rada3.R;

import java.util.List;

public class OptionEditorAdapter extends RecyclerView.Adapter<OptionEditorAdapter.ViewHolder> {

    private List<String> optionsList;

    class ViewHolder extends RecyclerView.ViewHolder {
        private EditText optionItem;
        private ImageView closeIcon;

        ViewHolder(View view) {
            super(view);
            optionItem = (EditText) view.findViewById(R.id.optionItem);
            closeIcon = (ImageView) view.findViewById(R.id.close_icon);
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
    public void onBindViewHolder(final OptionEditorAdapter.ViewHolder holder, final int position) {
        String option = optionsList.get(position);
        holder.optionItem.setHint(option);

        holder.optionItem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                optionsList.set(position, holder.optionItem.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(position);
            }
        });
    }

    private void removeItem(int position) {
        optionsList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return optionsList.size();
    }
}