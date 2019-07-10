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
import ir.iconish.sanjinehsub.adapter.viewholder.DetailContractViewHolder;
import ir.iconish.sanjinehsub.data.model.DetailContract;
import ir.iconish.sanjinehsub.util.TextFormatter;


public class DetailContractAdapter extends RecyclerView.Adapter<DetailContractViewHolder> {

    private  List<DetailContract> detailContracts;

    RecyclerIemListener recyclerIemListener ;

    public DetailContractAdapter(List<DetailContract> detailContracts, RecyclerIemListener recyclerIemListener) {
        this.detailContracts = detailContracts;

        this.recyclerIemListener = recyclerIemListener;

    }

    @NonNull
    @Override
    public DetailContractViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detail_contract_row, parent, false);
        ButterKnife.bind(this, itemView);


        return new DetailContractViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final DetailContractViewHolder viewHolder, final int i) {


        final DetailContract contract = detailContracts.get(i);
        viewHolder.txtContractType.setText(contract.getContractType());
        viewHolder.txtCreditor.setText(contract.getCreditorName());
        viewHolder.txtCurrency.setText(contract.getCurrency());
        viewHolder.txtMablaghSarresidNashode.setText(TextFormatter.applyThousandSeparators(contract.getOutstandingAmount()));
        viewHolder.txtMablaghSarresidShodePardakhtNashode.setText(TextFormatter.applyThousandSeparators(contract.getOverdueAmount()));
        viewHolder.txtTerminate.setText(String.valueOf(contract.getTotalTerminateContract()));
        viewHolder.txtCurrentContract.setText(String.valueOf(contract.getTotalOpenConract()));






viewHolder.view.setOnClickListener(v -> {

recyclerIemListener.onTap(contract);
});





    }

    @Override
    public int getItemCount() {
        return (null != detailContracts ? detailContracts.size() : 0);

    }
    @Override public long getItemId(int position) { return position; }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
