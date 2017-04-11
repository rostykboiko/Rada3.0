package com.springcamp.rostykboiko.rada3.main.view;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Survey;
import com.springcamp.rostykboiko.rada3.shared.utlils.GoogleAccountAdapter;

import java.util.ArrayList;

class CardsAdaptor extends RecyclerView.Adapter<CardsAdaptor.CardViewHolder> {
    private Context mContext;
    private ArrayList<Survey> surveyList = new ArrayList<>();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private OptionCardAdapter optionCardAdapter = new OptionCardAdapter();

    class CardViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private RecyclerView optionsRecycler;
        private ImageView deleteBtn;

        CardViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.title);
            optionsRecycler = (RecyclerView) view.findViewById(R.id.card_option_recycler);
            deleteBtn = (ImageView) view.findViewById(R.id.close_icon);

            RecyclerView.LayoutManager mListManager = new LinearLayoutManager(mContext.getApplicationContext());
            optionsRecycler.setLayoutManager(mListManager);
            optionsRecycler.setItemAnimator(new DefaultItemAnimator());
            optionsRecycler.setAdapter(optionCardAdapter);
        }
    }

    CardsAdaptor(Context mContext, ArrayList<Survey> surveyList) {
        this.mContext = mContext;
        this.surveyList = surveyList;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.survey_card, parent, false);

        return new CardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CardViewHolder holder, final int position) {
        Survey survey = surveyList.get(position);

        holder.title.setText(survey.getSurveyTitle());

        optionCardAdapter = new OptionCardAdapter(
                survey.getSurveyOptionList(),
                survey.getParticipantsCount());

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            final String surveyId = surveyList.get(position).getSurveyID();

            @Override
            public void onClick(View v) {

                database.getReference()
                        .child("User")
                        .child(GoogleAccountAdapter.getAccountID())
                        .child("Surveys")
                        .child(surveyList.get(position).getSurveyID())
                        .removeValue();

                DatabaseReference mCurrentSurvey = FirebaseDatabase
                        .getInstance()
                        .getReference()
                        .child("Survey")
                        .child(surveyList.get(position).getSurveyID())
                        .child("Creator");

                System.out.println("SurveyID surveyList " + surveyList);


                mCurrentSurvey.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        System.out.println("SurveyID getAccountID " + GoogleAccountAdapter
                                .getAccountID());
                        System.out.println("SurveyID getValue " + dataSnapshot.getValue(String.class));

                        if (dataSnapshot.getValue(String.class) != null && dataSnapshot.getValue(String.class)
                                .equals(GoogleAccountAdapter
                                        .getAccountID())) {

                            removeSurvey(surveyId);

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                surveyList.remove(position);
                notifyItemRemoved(position);
            }
        });

        holder.optionsRecycler.setAdapter(optionCardAdapter);
    }

    private void removeSurvey(String surveyId) {
        System.out.println("SurveyID surveyList " + surveyId);

        database.getReference()
                .child("Survey")
                .child(surveyId)
                .removeValue();
    }

    @Override
    public int getItemCount() {
        return surveyList.size();
    }
}