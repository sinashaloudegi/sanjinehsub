package ir.iconish.sanjinehsub.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.iconish.sanjinehsub.R;


public class DetailContractViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.rootLayout) public View view;
    @BindView(R.id.txtContractType) public TextView txtContractType;
    @BindView(R.id.txtCreditor) public TextView txtCreditor;
    @BindView(R.id.txtCurrentContract) public TextView txtCurrentContract;
    @BindView(R.id.txtTerminate) public TextView txtTerminate;
    @BindView(R.id.txtMablaghSarresidNashode) public TextView txtMablaghSarresidNashode;
    @BindView(R.id.txtMablaghSarresidShodePardakhtNashode) public TextView txtMablaghSarresidShodePardakhtNashode;
    @BindView(R.id.txtCurrency) public TextView txtCurrency;




    public DetailContractViewHolder(View convertView ) {
        super(convertView);
        ButterKnife.bind(this, convertView);
        this.view =convertView;


    }
}
