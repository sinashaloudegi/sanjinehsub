package ir.iconish.sanjinehsub.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.iconish.sanjinehsub.R;


public class TerminateContractViewHolder extends RecyclerView.ViewHolder {
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



    @BindView(R.id.txtVasiqehType)
    public  TextView txtVasiqehType;






    @BindView(R.id.txtVasiqehValue)
    public  TextView txtVasiqehValue;





    @BindView(R.id.txtMablaghSarresidShodePardakhtNashode)
    public  TextView txtMablaghSarresidShodePardakhtNashode;





    @BindView(R.id.txtMablaghSarresidNashodeh)
    public  TextView txtMablaghSarresidNashodeh;





    @BindView(R.id.txtTotalAmount)
    public  TextView txtTotalAmount;






    @BindView(R.id.txtMablaghAqsat)
    public  TextView txtMablaghAqsat;





    @BindView(R.id.txtAqsatNumber)
    public  TextView txtAqsatNumber;






    @BindView(R.id.txtPaymentDuration)
    public  TextView txtPaymentDuration;




    @BindView(R.id.txtPaymentType)
    public  TextView txtPaymentType;



    @BindView(R.id.txtQestType)
    public  TextView txtQestType;














    public TerminateContractViewHolder(View convertView ) {
        super(convertView);
        ButterKnife.bind(this, convertView);
        this.view =convertView;


    }
}
