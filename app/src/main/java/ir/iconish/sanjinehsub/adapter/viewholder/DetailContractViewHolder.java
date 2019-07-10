package ir.iconish.sanjinehsub.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.iconish.sanjinehsub.R;


public class DetailContractViewHolder extends RecyclerView.ViewHolder {
    @Nullable
    @BindView(R.id.rootLayout) public View view;
    @Nullable
    @BindView(R.id.txtContractType) public TextView txtContractType;
    @Nullable
    @BindView(R.id.txtCreditor) public TextView txtCreditor;
    @Nullable
    @BindView(R.id.txtCurrentContract) public TextView txtCurrentContract;
    @Nullable
    @BindView(R.id.txtTerminate) public TextView txtTerminate;
    @Nullable
    @BindView(R.id.txtMablaghSarresidNashode) public TextView txtMablaghSarresidNashode;
    @Nullable
    @BindView(R.id.txtMablaghSarresidShodePardakhtNashode) public TextView txtMablaghSarresidShodePardakhtNashode;
    @Nullable
    @BindView(R.id.txtCurrency) public TextView txtCurrency;


    public DetailContractViewHolder(@NonNull View convertView) {
        super(convertView);
        ButterKnife.bind(this, convertView);
        this.view =convertView;


    }
}
