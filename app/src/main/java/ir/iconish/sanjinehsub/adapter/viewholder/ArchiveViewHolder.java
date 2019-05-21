package ir.iconish.sanjinehsub.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.iconish.sanjinehsub.R;


public class ArchiveViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.rootLayout) public View view;
    @BindView(R.id.textNum) public TextView txtNum;
    @BindView(R.id.textDate) public TextView txtDate;

    @BindView(R.id.imgVisit)  public ImageView imgVisit;
    @BindView(R.id.imgDownload)  public ImageView imgDownload;


    public ArchiveViewHolder(View convertView ) {
        super(convertView);
        ButterKnife.bind(this, convertView);
        this.view =convertView;


    }
}
