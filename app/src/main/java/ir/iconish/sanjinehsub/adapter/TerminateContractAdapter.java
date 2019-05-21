/**
 * com.sarbarg.viseo.adapter is SmsReceiver group of bar adapter for recyclerView.
 */
package ir.iconish.sanjinehsub.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.adapter.listener.RecyclerIemListener;
import ir.iconish.sanjinehsub.adapter.viewholder.DetailContractViewHolder;
import ir.iconish.sanjinehsub.adapter.viewholder.TerminateContractViewHolder;
import ir.iconish.sanjinehsub.data.model.DetailContract;
import ir.iconish.sanjinehsub.data.model.TerminateContract;
import ir.iconish.sanjinehsub.util.DateHepler;
import ir.iconish.sanjinehsub.util.TextFormatter;


public class TerminateContractAdapter extends RecyclerView.Adapter<TerminateContractViewHolder> {

    private  List<TerminateContract> terminateContracts;

    RecyclerIemListener recyclerIemListener ;

    public TerminateContractAdapter(List<TerminateContract> terminateContracts, RecyclerIemListener recyclerIemListener) {
        this.terminateContracts = terminateContracts;

        this.recyclerIemListener = recyclerIemListener;

    }
    @Override
    public TerminateContractViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.terminate_contract_row_layout, parent, false);
        ButterKnife.bind(this, itemView);


        return new TerminateContractViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final TerminateContractViewHolder viewHolder, final int i) {


        final TerminateContract terminateContract = terminateContracts.get(i);


        viewHolder. txtNegativeContractStatus.setText(terminateContract.getNegativeContractStatus());

        viewHolder.  txtBankContractType.setText(terminateContract.getTypeOfFinancingInstalments());

        viewHolder. txtGoalOfCredit.setText(terminateContract.getPurposeOfTheCredit());


        String startDate= DateHepler.convertTimeStampToPersianDate(terminateContract.getTypesContractDatesStart());

        viewHolder. txtContractStart.setText(startDate);

        String endDate=DateHepler.convertTimeStampToPersianDate(terminateContract.getTypesContractDatesExpectedEnd());


        viewHolder. txtContractEnd.setText(endDate);


        String statusDate=DateHepler.convertTimeStampToPersianDate(terminateContract.getTypesContractDatesExpectedEnd());

        viewHolder.txtStatusDate.setText(statusDate);





        viewHolder.txtPaymentCurrency.setText(terminateContract.getCurrency());

        viewHolder.txtPersonRole.setText(terminateContract.getRoleOfConnectedSubject());

        viewHolder. txtCreditorName.setText(terminateContract.getReportsContractDataCreditor());

        viewHolder. txtVasiqehType.setText(terminateContract.getCollateralType());
        viewHolder. txtVasiqehValue.setText(TextFormatter.applyThousandSeparators(Integer.parseInt(terminateContract.getCollateralAmount())));

        viewHolder. txtMablaghSarresidShodePardakhtNashode.setText(TextFormatter.applyThousandSeparators(terminateContract.getRelationsAmountsOutstanding()));
        viewHolder. txtMablaghSarresidNashodeh.setText(TextFormatter.applyThousandSeparators(terminateContract.getRelationsAmountsOverdue()));


        viewHolder. txtTotalAmount.setText(String.valueOf(terminateContract.getRelationsAmountsTotalCredit()));

        viewHolder. txtMablaghAqsat.setText(TextFormatter.applyThousandSeparators(terminateContract.getRelationsAmountsStandardPeriodicalInstalment()));

        viewHolder. txtAqsatNumber.setText(String.valueOf(terminateContract.getContractNumberOfInstalments()));

        viewHolder. txtPaymentDuration.setText(terminateContract.getPeriodicityOfPayments());

        viewHolder. txtPaymentType.setText(terminateContract.getMethodOfPayment());

        viewHolder. txtQestType.setText(terminateContract.getTypeOfInstalments());

        viewHolder.view.setOnClickListener(v -> {

recyclerIemListener.onTap(terminateContract);
});



    }

    @Override
    public int getItemCount() {
        return (null != terminateContracts ? terminateContracts.size() : 0);

    }
    @Override public long getItemId(int position) { return position; }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
