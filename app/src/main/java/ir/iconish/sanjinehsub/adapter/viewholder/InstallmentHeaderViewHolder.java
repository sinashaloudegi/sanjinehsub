package ir.iconish.sanjinehsub.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.iconish.sanjinehsub.R;


public class InstallmentHeaderViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.rootLayout) public View view;
    @BindView(R.id.txtHeaderTitle) public TextView txtHeaderTitle;



    @BindView(R.id.txtNegativeContractStatus)
    public TextView txtNegativeContractStatus;

    @BindView(R.id.txtBankContractType)
    public   TextView txtBankContractType;

    @BindView(R.id.txtGoalOfCredit)
    public  TextView txtGoalOfCredit;


    @BindView(R.id.txtContractStart)
    public  TextView txtContractStart;


    @BindView(R.id.txtContractEnd)
    public TextView txtContractEnd;

    @BindView(R.id.txtStatusDate)
    public  TextView txtStatusDate;

    @BindView(R.id.txtPaymentCurrency)
    public  TextView txtPaymentCurrency;

    @BindView(R.id.txtPersonRole)
    public  TextView txtPersonRole;


    @BindView(R.id.txtCreditorName)
    public  TextView txtCreditorName;








    public InstallmentHeaderViewHolder(View convertView ) {
        super(convertView);
        ButterKnife.bind(this, convertView);
        this.view =convertView;


    }
}
