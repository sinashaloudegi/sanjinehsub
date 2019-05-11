package ir.iconish.sanjinehsub.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.iconish.sanjinehsub.R;


public class SummaryContractViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.rootLayout) public View view;
    @BindView(R.id.txtCreditor) public TextView txtCreditor;
    @BindView(R.id.txtPersonNegativeState) public TextView txtPersonNegativeState;
    @BindView(R.id.txtAlertDate) public TextView txtAlertDate;




    public SummaryContractViewHolder(View convertView ) {
        super(convertView);
        ButterKnife.bind(this, convertView);
        this.view =convertView;


    }
}
