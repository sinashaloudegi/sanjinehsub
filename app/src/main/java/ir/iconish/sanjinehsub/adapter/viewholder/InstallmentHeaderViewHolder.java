package ir.iconish.sanjinehsub.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.iconish.sanjinehsub.R;


public class InstallmentHeaderViewHolder extends RecyclerView.ViewHolder {
    @Nullable
    @BindView(R.id.rootLayout) public View view;
    @Nullable
    @BindView(R.id.txtHeaderTitle) public TextView txtHeaderTitle;


    @Nullable
    @BindView(R.id.txtNegativeContractStatus)
    public TextView txtNegativeContractStatus;

    @Nullable
    @BindView(R.id.txtBankContractType)
    public   TextView txtBankContractType;

    @Nullable
    @BindView(R.id.txtGoalOfCredit)
    public  TextView txtGoalOfCredit;


    @Nullable
    @BindView(R.id.txtContractStart)
    public  TextView txtContractStart;


    @Nullable
    @BindView(R.id.txtContractEnd)
    public TextView txtContractEnd;

    @Nullable
    @BindView(R.id.txtStatusDate)
    public  TextView txtStatusDate;

    @Nullable
    @BindView(R.id.txtPaymentCurrency)
    public  TextView txtPaymentCurrency;

    @Nullable
    @BindView(R.id.txtPersonRole)
    public  TextView txtPersonRole;


    @Nullable
    @BindView(R.id.txtCreditorName)
    public  TextView txtCreditorName;


    public InstallmentHeaderViewHolder(@NonNull View convertView) {
        super(convertView);
        ButterKnife.bind(this, convertView);
        this.view =convertView;


    }
}
