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
public class TeachBourseViewHolder extends RecyclerView.ViewHolder {
    @Nullable
    @BindView(R.id.rootLayout)
    public View view;
    @Nullable
    @BindView(R.id.teach_bourse_text)
    public TextView teachBourseText;

    @Nullable
    @BindView(R.id.teach_bourse_logo)
    public ImageView teachBourseLogo;


    public TeachBourseViewHolder(@NonNull View convertView) {
        super(convertView);
        ButterKnife.bind(this, convertView);
        this.view = convertView;


    }
}