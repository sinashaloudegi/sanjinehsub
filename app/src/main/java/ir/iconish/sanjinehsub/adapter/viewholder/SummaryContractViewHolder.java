package ir.iconish.sanjinehsub.adapter.viewholder;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.iconish.sanjinehsub.R;


public class SummaryContractViewHolder extends RecyclerView.ViewHolder {
    @Nullable
    @BindView(R.id.rootLayout)
    public View view;
    @Nullable
    @BindView(R.id.txtCreditor)
    public TextView txtCreditor;
    @Nullable
    @BindView(R.id.txtPersonNegativeState)
    public TextView txtPersonNegativeState;
    @Nullable
    @BindView(R.id.txtAlertDate)
    public TextView txtAlertDate;


    public SummaryContractViewHolder(@NonNull View convertView) {
        super(convertView);
        ButterKnife.bind(this, convertView);
        this.view = convertView;


    }
}
