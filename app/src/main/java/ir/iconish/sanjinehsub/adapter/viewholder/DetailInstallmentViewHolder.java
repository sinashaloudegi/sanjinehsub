package ir.iconish.sanjinehsub.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.iconish.sanjinehsub.R;


public class DetailInstallmentViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.rootLayout) public View view;
    @BindView(R.id.txtMablaghSaresidPardakhtNashode) public TextView txtMablaghSaresidPardakhtNashode;
    @BindView(R.id.txtDate) public TextView txtDate;





    public DetailInstallmentViewHolder(View convertView ) {
        super(convertView);
        ButterKnife.bind(this, convertView);
        this.view =convertView;


    }
}
