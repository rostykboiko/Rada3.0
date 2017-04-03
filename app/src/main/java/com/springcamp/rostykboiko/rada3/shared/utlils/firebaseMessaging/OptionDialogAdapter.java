package com.springcamp.rostykboiko.rada3.shared.utlils.firebaseMessaging;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Option;

import java.util.ArrayList;

class OptionDialogAdapter extends RecyclerView.Adapter<OptionDialogAdapter.ViewHolder> {

    private ArrayList<Option> optionsList = new ArrayList<>();

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView optionItem;
        private CheckBox checkBox;
        private Button okBtn;

        ViewHolder(View view) {
            super(view);
            optionItem = (TextView) view.findViewById(R.id.optionItemDialog);
            checkBox = (CheckBox) view.findViewById(R.id.checkbox);
            okBtn = (Button) view.findViewById(R.id.button_ok);
        }
    }

    OptionDialogAdapter(ArrayList<Option> optionsList) {
        this.optionsList = optionsList;
    }

    @Override
    public OptionDialogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_option_dialog, parent, false);

        return new OptionDialogAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final OptionDialogAdapter.ViewHolder holder, final int position) {
            holder.optionItem.setText(optionsList.get(position).getOptionTitle());

    }

    @Override
    public int getItemCount() {
        return optionsList.size();
    }

    public ArrayList<Option> getOptionsList(){return optionsList;
    }
}
