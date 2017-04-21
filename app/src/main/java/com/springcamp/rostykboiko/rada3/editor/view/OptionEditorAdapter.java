package com.springcamp.rostykboiko.rada3.editor.view;

import android.content.Context;
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
    private Context mContext;
    @NonNull
    private OptionItemsCallback callback;

    @NonNull
    private ArrayList<Option> optionsList;

    class ViewHolder extends RecyclerView.ViewHolder {
        private EditText optionItem;
        private ImageView closeIcon;

        ViewHolder(View view) {
            super(view);
            optionItem = (EditText) view.findViewById(R.id.optionItemDialog);
            closeIcon = (ImageView) view.findViewById(R.id.close_icon);

            optionItem.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    optionsList.get(getAdapterPosition()).setOptionTitle(optionItem.getText().toString());
                    optionsList.get(getAdapterPosition()).setOptionKey("option" + (getAdapterPosition() + 1));
                    System.out.println("optionsList adapter " + optionsList.get(getAdapterPosition()).getOptionTitle());
                    callback.onOptionChanged(optionsList);
                }
            });
        }

    }

    OptionEditorAdapter(Context mContext, @NonNull ArrayList<Option> optionsList, @NonNull OptionEditorAdapter.OptionItemsCallback callback) {
        this.mContext = mContext;
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
        holder.optionItem.setText(optionsList.get(position).getOptionTitle());

        holder.closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onOptionDeleted(position);
                notifyItemRemoved(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return optionsList.size();
    }

    interface OptionItemsCallback {

        void onOptionDeleted(int position);

        void onOptionChanged(@NonNull ArrayList<Option> options);
    }
}