package ir.iconish.sanjinehsub.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import butterknife.BindView;
import butterknife.ButterKnife;
import ir.iconish.sanjinehsub.R;


public class NavigationViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.rootLayout) public View view;
    @BindView(R.id.textNavItem) public TextView txtTitle;

    @BindView(R.id.navItemIcon)  public ImageView navItemIcon;


    public NavigationViewHolder(View convertView ) {
        super(convertView);
        ButterKnife.bind(this, convertView);
        this.view =convertView;


    }
}
