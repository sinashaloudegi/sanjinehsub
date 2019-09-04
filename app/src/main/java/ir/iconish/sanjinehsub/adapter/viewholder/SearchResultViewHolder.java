package ir.iconish.sanjinehsub.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.iconish.sanjinehsub.R;

/**
 * @author s.shaloudegi
 * @date 8/31/2019
 */
public class SearchResultViewHolder extends RecyclerView.ViewHolder {
    @Nullable
    @BindView(R.id.search_result_root)
    public View view;


    @Nullable
    @BindView(R.id.search_result_text)
    public TextView searchResultText;


    public SearchResultViewHolder(@NonNull View convertView) {
        super(convertView);
        ButterKnife.bind(this, convertView);
        this.view = convertView;
    }
}
