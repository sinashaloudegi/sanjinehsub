package ir.iconish.sanjinehsub.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.iconish.sanjinehsub.R;


public class NavigationViewHolder extends RecyclerView.ViewHolder {
    @Nullable
    @BindView(R.id.rootLayout) public View view;
    @Nullable
    @BindView(R.id.textNavItem) public TextView txtTitle;

    @Nullable
    @BindView(R.id.navItemIcon)  public ImageView navItemIcon;


    public NavigationViewHolder(@NonNull View convertView) {
        super(convertView);
        ButterKnife.bind(this, convertView);
        this.view =convertView;


    }
}
