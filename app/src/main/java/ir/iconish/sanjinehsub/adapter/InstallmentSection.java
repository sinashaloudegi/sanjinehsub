package ir.iconish.sanjinehsub.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;
import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.adapter.listener.RecyclerIemListener;
import ir.iconish.sanjinehsub.adapter.viewholder.DetailInstallmentViewHolder;
import ir.iconish.sanjinehsub.adapter.viewholder.InstallmentHeaderViewHolder;
import ir.iconish.sanjinehsub.data.model.DetailInstallment;
import ir.iconish.sanjinehsub.data.model.FullInstallment;
import ir.iconish.sanjinehsub.util.DateHepler;

public class InstallmentSection extends StatelessSection {


List<DetailInstallment> detailInstallments;

    int headerPosition;
    FullInstallment fullInstallment;
    RecyclerIemListener recyclerIemListener;
    public InstallmentSection(FullInstallment fullInstallment,int headerPosition, List<DetailInstallment> detailInstallments, RecyclerIemListener recyclerIemListener) {
        super(SectionParameters.builder()
                .itemResourceId(R.layout.installment_item_row_layout)
                .headerResourceId(R.layout.installment_header_row_layout)
                .build()


        );
        this.fullInstallment=fullInstallment;
        this.detailInstallments=detailInstallments;
        this.recyclerIemListener=recyclerIemListener;
        this.headerPosition=headerPosition;
    }

    @Override
    public int getContentItemsTotal() {
        return detailInstallments.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder getItemViewHolder(@NonNull View view) {
        return new DetailInstallmentViewHolder(view);

    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        final DetailInstallmentViewHolder viewHolder = (DetailInstallmentViewHolder) holder;

        final DetailInstallment detailInstallment = detailInstallments.get(position);

        viewHolder.txtDate.setText(detailInstallment.getReportsHistoricalCalendarYear()+"/"+detailInstallment.getReportsHistoricalCalendarYear());
        viewHolder.txtMablaghSaresidPardakhtNashode.setText(String.valueOf(detailInstallment.getTypesAmount()));



        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recyclerIemListener.onTap(detailInstallment);
            }
        });

















    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(@NonNull View view) {
        return new InstallmentHeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        InstallmentHeaderViewHolder headerHolder = (InstallmentHeaderViewHolder) holder;

        headerHolder.txtNegativeContractStatus.setText(fullInstallment.getNegativeContractStatus());



        headerHolder. txtBankContractType.setText(fullInstallment.getLookupsTypeOfFinancingInstalments());




        headerHolder.txtGoalOfCredit.setText(fullInstallment.getPurposeOfTheCredit());

        String startDate=DateHepler.convertTimeStampToPersianDate(fullInstallment.getTypesContractDatesStart());



        headerHolder. txtContractStart.setText(startDate);


        String endDate=DateHepler.convertTimeStampToPersianDate(fullInstallment.getTypesContractDatesExpectedEnd());


        headerHolder.txtContractEnd.setText(endDate);




        String statusDate=DateHepler.convertTimeStampToPersianDate(fullInstallment.getReportsLastUpdate());

        headerHolder.txtStatusDate.setText(statusDate);




        headerHolder. txtPaymentCurrency.setText(fullInstallment.getLookupsCurrencyCodes());







        headerHolder.txtPersonRole.setText(fullInstallment.getRoleOfConnectedSubject());






        headerHolder. txtCreditorName.setText(fullInstallment.getReportsContractDataCreditor());
        String title= headerHolder. txtHeaderTitle.getText().toString()+" "+headerPosition;
        headerHolder. txtHeaderTitle.setText(title);





    }


}