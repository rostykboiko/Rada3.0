package com.springcamp.rostykboiko.rada3.luckyWheel.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Option;

import java.util.ArrayList;

class OptionEditorAdapter extends RecyclerView.Adapter<OptionEditorAdapter.ViewHolder> {

    @NonNull
    private OptionItemsCallback callback;

    @NonNull
    private ArrayList<String> optionsList;

    class ViewHolder extends RecyclerView.ViewHolder {
        private EditText optionItem;
        private ImageView closeIcon;
        private ImageView checkIcon;

        ViewHolder(View view) {
            super(view);
            closeIcon = (ImageView) view.findViewById(R.id.close_icon);
            checkIcon = (ImageView) view.findViewById(R.id.checkBox);
            optionItem = (EditText) view.findViewById(R.id.optionItemDialog);

            optionItem.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    optionsList.add(s.toString());
                    callback.onOptionChanged(optionsList);
                }
            });
        }

    }

    OptionEditorAdapter(@NonNull ArrayList<String> optionsList,
                        @NonNull OptionEditorAdapter.OptionItemsCallback callback) {
        this.optionsList = optionsList;
        this.callback = callback;
    }

    @Override
    public OptionEditorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_option_ed, parent, false);

        return new OptionEditorAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final OptionEditorAdapter.ViewHolder holder, final int position) {
        holder.optionItem.setText(optionsList.get(position));

        if (optionsList.size() != 1 && holder.optionItem.getText().toString().equals("")) {
            holder.optionItem.setFocusable(true);
            holder.optionItem.requestFocus();
        }

        holder.closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onOptionDeleted(position);
                notifyItemRemoved(position);
            }
        });

        if (!callback.onOneOption()){
            holder.checkIcon.setImageResource(R.drawable.ic_material_checkbox_blank);
        } else {
            holder.checkIcon.setImageResource(R.drawable.ic_material_radio_blank);
        }

    }

    @Override
    public int getItemCount() {
        return optionsList.size();
    }

    interface OptionItemsCallback {

        boolean onOneOption();

        void onOptionDeleted(int position);

        void onOptionChanged(@NonNull ArrayList<String> options);
    }
}