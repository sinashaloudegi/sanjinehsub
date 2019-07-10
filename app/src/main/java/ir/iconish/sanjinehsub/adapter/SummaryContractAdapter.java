/**
 * com.sarbarg.viseo.adapter is SmsReceiver group of bar adapter for recyclerView.
 */
package ir.iconish.sanjinehsub.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.ButterKnife;
import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.adapter.listener.RecyclerIemListener;
import ir.iconish.sanjinehsub.adapter.viewholder.SummaryContractViewHolder;
import ir.iconish.sanjinehsub.data.model.SummaryContract;
import ir.iconish.sanjinehsub.util.DateHepler;


public class SummaryContractAdapter extends RecyclerView.Adapter<SummaryContractViewHolder> {

    private  List<SummaryContract> contracts;

    RecyclerIemListener recyclerIemListener ;

    public SummaryContractAdapter(List<SummaryContract> contracts, RecyclerIemListener recyclerIemListener) {
        this.contracts = contracts;

        this.recyclerIemListener = recyclerIemListener;

    }

    @NonNull
    @Override
    public SummaryContractViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.summary_contract_row, parent, false);
        ButterKnife.bind(this, itemView);


        return new SummaryContractViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final SummaryContractViewHolder viewHolder, final int i) {


        final SummaryContract contract = contracts.get(i);
        String date=DateHepler.convertTimeStampToPersianDate(contract.getReportsLastUpdate());

        viewHolder.txtAlertDate.setText(date);
        viewHolder.txtCreditor.setText(contract.getReportsCreditor());
        viewHolder.txtPersonNegativeState.setText(contract.getNegativeSubjectStatus());





viewHolder.view.setOnClickListener(v -> {

recyclerIemListener.onTap(contract);
});





    }

    @Override
    public int getItemCount() {
        return (null != contracts ? contracts.size() : 0);

    }
    @Override public long getItemId(int position) { return position; }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
