/**
 * com.sarbarg.viseo.adapter is SmsReceiver group of bar adapter for recyclerView.
 */
package ir.iconish.sanjinehsub.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.ButterKnife;
import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.adapter.listener.RecyclerIemListener;
import ir.iconish.sanjinehsub.adapter.viewholder.ContractViewHolder;
import ir.iconish.sanjinehsub.data.model.SummaryContract;


public class ContractAdapter extends RecyclerView.Adapter<ContractViewHolder> {

    private  List<SummaryContract> contracts;

    RecyclerIemListener recyclerIemListener ;

    public ContractAdapter(List<SummaryContract> contracts, RecyclerIemListener recyclerIemListener) {
        this.contracts = contracts;

        this.recyclerIemListener = recyclerIemListener;

    }
    @Override
    public ContractViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contract_row, parent, false);
        ButterKnife.bind(this, itemView);


        return new ContractViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final ContractViewHolder viewHolder, final int i) {


        final SummaryContract contract = contracts.get(i);
        viewHolder.txtAlertDate.setText(String.valueOf(contract.getReportsLastUpdate()));
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
