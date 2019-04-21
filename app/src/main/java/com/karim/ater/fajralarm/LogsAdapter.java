package com.karim.ater.fajralarm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class LogsAdapter extends RecyclerView.Adapter<LogsAdapter.LogViewHolder> {

    private Context context;
    private ArrayList<Contact> selectedContacts;

    LogsAdapter(Context context) {
        this.context = context;
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        this.selectedContacts = databaseHelper.loadContacts();
    }

    @NonNull
    @Override
    public LogViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.log_item, viewGroup, false);
        return new LogViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull LogViewHolder logViewHolder, int i) {
        CallDetails callDetails = Utils.extractCallDetail(selectedContacts.get(i).getContactLog());
        logViewHolder.logContactNameTv.setText(selectedContacts.get(i).getContactName());
        logViewHolder.finalStatusTv.setText(callDetails.getFinalStatus());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        logViewHolder.callTimesRv.setLayoutManager(linearLayoutManager);
        logViewHolder.callTimesRv.setAdapter(new CallTimesAdapter(callDetails.getCallTimes()));
        logViewHolder.callTimesRv.setHasFixedSize(true);
    }

    @Override
    public int getItemCount() {
        return selectedContacts.size();
    }

    class LogViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView logContactNameTv;
        TextView finalStatusTv;
        RecyclerView callTimesRv;

        LogViewHolder(View view) {
            super(view);
            mView = view;
            logContactNameTv = view.findViewById(R.id.logContactNameTv);
            finalStatusTv = view.findViewById(R.id.finalStatusTv);
            callTimesRv = view.findViewById(R.id.callTimesRv);

        }

    }
}