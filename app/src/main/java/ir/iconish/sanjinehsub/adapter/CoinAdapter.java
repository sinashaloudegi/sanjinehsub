package ir.iconish.sanjinehsub.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.adapter.listener.RecyclerIemListener;
import ir.iconish.sanjinehsub.adapter.viewholder.CoinViewHolder;
import ir.iconish.sanjinehsub.data.model.CoinPrice;

/**
 * @author s.shaloudegi
 * @date 8/13/2019
 */
public class CoinAdapter extends RecyclerView.Adapter<CoinViewHolder> {


    RecyclerIemListener recyclerIemListener;
    private List<CoinPrice> mCoinPrices;

    public CoinAdapter(List<CoinPrice> coinPrices, RecyclerIemListener recyclerIemListener) {
        this.mCoinPrices = coinPrices;

        this.recyclerIemListener = recyclerIemListener;

    }

    @NonNull
    @Override
    public CoinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.coin_item_row, parent, false);
        ButterKnife.bind(this, itemView);


        return new CoinViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final CoinViewHolder viewHolder, final int i) {


        final CoinPrice coinPrice = mCoinPrices.get(i);
        viewHolder.txtCoinName.setText(coinPrice.getName());
        viewHolder.txtCoinPrice.setText(coinPrice.getPrice()[0] + "");
        viewHolder.txtCoinRate.setText(coinPrice.getRate());
        viewHolder.txtCoinTime.setText(coinPrice.getDate()[0] + "");


        int[] x = {10, 20, 30, 40, 50, 60,};
        int[] y = {10, 5, 65, 20, 25, 0,};

        List<Entry> entries = new ArrayList<>();
        for (int j = 0; j < x.length; j++) {

            // turn your data into Entry objects
            entries.add(new Entry(x[j], y[j]));
        }
        LineDataSet dataSet = new LineDataSet(entries, "قیمت ارز"); // add entries to dataset
        LineData lineData = new LineData(dataSet);

        viewHolder.mChart.getXAxis().setDrawGridLines(false);
        viewHolder.mChart.getXAxis().setDrawGridLines(false);
        viewHolder.mChart.getAxisLeft().setDrawGridLines(false);
        viewHolder.mChart.getXAxis().setDrawGridLines(false);

        viewHolder.mChart.setGridBackgroundColor(R.color.bluish);
        viewHolder.mChart.setData(lineData);
        viewHolder.mChart.invalidate();

     /*   viewHolder.navItemIcon.setImageResource(otherServiceItem.getDrawbleId());
        viewHolder.txtTitle.setText(otherServiceItem.getTitle());
*/

        viewHolder.view.setOnClickListener(v -> recyclerIemListener.onTap(coinPrice));

    }

    @Override
    public int getItemCount() {
        return (null != mCoinPrices ? mCoinPrices.size() : 0);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
