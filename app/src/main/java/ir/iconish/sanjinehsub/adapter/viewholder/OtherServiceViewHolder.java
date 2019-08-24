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

/**
 * @author s.shaloudegi
 * @date 8/19/2019
 */
public class OtherServiceViewHolder extends RecyclerView.ViewHolder {
    @Nullable
    @BindView(R.id.rootLayout)
    public View view;
    @Nullable
    @BindView(R.id.other_services_text)
    public TextView otherServicesText;

    @Nullable
    @BindView(R.id.other_services_logo)
    public ImageView otherServicesLogo;


    public OtherServiceViewHolder(@NonNull View convertView) {
        super(convertView);
        ButterKnife.bind(this, convertView);
        this.view = convertView;


    }
}