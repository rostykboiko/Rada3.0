package com.springcamp.rostykboiko.rada3.main.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Survey;

class CardViewHolder extends RecyclerView.ViewHolder {
    private Context context;
    private OptionCardAdapter optionCardAdapter = new OptionCardAdapter();
    private TextView title;
    private ImageView deleteBtn;

    private QuestionCardCallback callback;

    CardViewHolder(final View view, @NonNull Context context, @NonNull QuestionCardCallback callback) {
        super(view);
        this.context = context;
        this.callback = callback;

        title = (TextView) view.findViewById(R.id.title);

        RecyclerView optionsRecycler = (RecyclerView) view.findViewById(R.id.card_option_recycler);
        deleteBtn = (ImageView) view.findViewById(R.id.close_icon);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(deleteBtn, getAdapterPosition());
            }
        });

        RecyclerView.LayoutManager mListManager = new LinearLayoutManager(context);
        optionsRecycler.setLayoutManager(mListManager);
        optionsRecycler.setItemAnimator(new DefaultItemAnimator());
        optionsRecycler.setAdapter(optionCardAdapter);
    }

    public void setSurvey(@NonNull Survey survey) {
        optionCardAdapter.setOnePositiveOption(survey.isSurveySingleOption());
        optionCardAdapter.setParticipantsCount(survey.getParticipantsCount());
        optionCardAdapter.setOptions(survey.getSurveyOptionList());
    }

    void setSurveyName(String surveyTitle) {
        title.setText(surveyTitle);
    }

    interface QuestionCardCallback {
        void onDeleteCard(int position);

        void onEditClick(int position);

    }

    private void showPopupMenu(View view, int position) {
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_card, popup.getMenu());
        popup.getMenu().findItem(R.id.action_edit).setVisible(true);
        popup.setOnMenuItemClickListener(new MenuItemClickListener(position, callback));
        popup.show();
    }
}
