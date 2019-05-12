package com.visit24.therapist.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;
import com.visit24.therapist.R;
import com.visit24.therapist.adapter.listener.VisitItemListener;
import com.visit24.therapist.adapter.viewholder.ConfirmVisitViewHolder;
import com.visit24.therapist.adapter.viewholder.HeaderViewHolder;
import com.visit24.therapist.data.model.Address;
import com.visit24.therapist.data.model.Profile;
import com.visit24.therapist.data.model.Visit;
import com.visit24.therapist.util.DateHelper;

import java.util.List;

public class VisitSection extends StatelessSection {
String header;

List<Visit> visits;

    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }

    VisitItemListener visitItemListener;
    public VisitSection(String header, List<Visit> visitList, VisitItemListener visitItemListener) {
        super(SectionParameters.builder()
                .itemResourceId(R.layout.accepted_visit_row)
                .headerResourceId(R.layout.header)
                .build()


        );
        this.visits=visitList;
        this.header=header;
        this.visitItemListener=visitItemListener;
    }

    @Override
    public int getContentItemsTotal() {
        return visits.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ConfirmVisitViewHolder(view);

    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ConfirmVisitViewHolder viewHolder = (ConfirmVisitViewHolder) holder;

        final Visit visit = visits.get(position);
        Profile profile=visit.getProfile();
        Address address=visit.getAddress();
        viewHolder.txtUserName.setText(profile.getName()+'\n'+profile.getFamily());
        viewHolder.textAddress.setText(address.getBody());

        String[] date=   DateHelper.getDate(visit.getDate());
        viewHolder.textDate.setText(date[0]);
        viewHolder.textHour.setText(date[1]);

        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                visitItemListener.onTap(visit);
            }
        });

















    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder headerHolder = (HeaderViewHolder) holder;

        headerHolder.textTitle.setText(header);
        headerHolder.txtCount.setText(headerHolder.view.getContext().getText(R.string.count)+"("+visits.size()+")");
    }


}