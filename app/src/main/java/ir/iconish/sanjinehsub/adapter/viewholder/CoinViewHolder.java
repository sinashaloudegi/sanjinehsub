package ir.iconish.sanjinehsub.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.iconish.sanjinehsub.R;

/**
 * @author s.shaloudegi
 * @date 8/13/2019
 */
public class CoinViewHolder extends RecyclerView.ViewHolder {


    @Nullable
    @BindView(R.id.rootLayout)
    public View view;
    @Nullable
    @BindView(R.id.txt_coin_rate)
    public TextView txtCoinRate;

    @Nullable
    @BindView(R.id.txt_coin_price)
    public TextView txtCoinPrice;
    @Nullable
    @BindView(R.id.txt_coin_time)
    public TextView txtCoinTime;

    @Nullable
    @BindView(R.id.txt_coin_name)
    public TextView txtCoinName;

    @Nullable
    @BindView(R.id.chart)
    public LineChart mChart;


    public CoinViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.view = itemView;

    }
}
