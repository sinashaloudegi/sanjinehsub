package ir.iconish.sanjinehsub.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.iconish.sanjinehsub.R;


public class TerminateContractViewHolder extends RecyclerView.ViewHolder {
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


    @Nullable
    @BindView(R.id.txtVasiqehType)
    public  TextView txtVasiqehType;


    @Nullable
    @BindView(R.id.txtVasiqehValue)
    public  TextView txtVasiqehValue;


    @Nullable
    @BindView(R.id.txtMablaghSarresidShodePardakhtNashode)
    public  TextView txtMablaghSarresidShodePardakhtNashode;


    @Nullable
    @BindView(R.id.txtMablaghSarresidNashodeh)
    public  TextView txtMablaghSarresidNashodeh;


    @Nullable
    @BindView(R.id.txtTotalAmount)
    public  TextView txtTotalAmount;


    @Nullable
    @BindView(R.id.txtMablaghAqsat)
    public  TextView txtMablaghAqsat;


    @Nullable
    @BindView(R.id.txtAqsatNumber)
    public  TextView txtAqsatNumber;


    @Nullable
    @BindView(R.id.txtPaymentDuration)
    public  TextView txtPaymentDuration;


    @Nullable
    @BindView(R.id.txtPaymentType)
    public  TextView txtPaymentType;


    @Nullable
    @BindView(R.id.txtQestType)
    public  TextView txtQestType;


    public TerminateContractViewHolder(@NonNull View convertView) {
        super(convertView);
        ButterKnife.bind(this, convertView);
        this.view =convertView;


    }
}
