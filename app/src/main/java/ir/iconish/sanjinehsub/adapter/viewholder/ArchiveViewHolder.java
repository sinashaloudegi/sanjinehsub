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


public class ArchiveViewHolder extends RecyclerView.ViewHolder {
    @Nullable
    @BindView(R.id.rootLayout) public View view;
    @Nullable
    @BindView(R.id.textNum) public TextView txtNum;
    @Nullable
    @BindView(R.id.textDate) public TextView txtDate;
    @Nullable
    @BindView(R.id.imgVisit)  public ImageView imgVisit;
    @Nullable
    @BindView(R.id.imgDownload)  public ImageView imgDownload;


    public ArchiveViewHolder(@NonNull View convertView) {
        super(convertView);
        ButterKnife.bind(this, convertView);
        this.view =convertView;


    }
}
