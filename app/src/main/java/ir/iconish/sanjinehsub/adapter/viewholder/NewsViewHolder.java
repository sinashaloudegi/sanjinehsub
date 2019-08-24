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
 * @date 8/21/2019
 */
public class NewsViewHolder extends RecyclerView.ViewHolder {
    @Nullable
    @BindView(R.id.rootLayout)
    public View view;


    @Nullable
    @BindView(R.id.news_text)
    public TextView newsText;

    @Nullable
    @BindView(R.id.news_image)
    public ImageView newsImage;

    @Nullable
    @BindView(R.id.news_description)
    public TextView newsDescription;

    public NewsViewHolder(@NonNull View convertView) {
        super(convertView);
        ButterKnife.bind(this, convertView);
        this.view = convertView;
    }

}
