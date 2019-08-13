package ir.iconish.sanjinehsub.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.iconish.sanjinehsub.adapter.viewholder.ArchiveViewHolder;
import ir.iconish.sanjinehsub.data.model.CoinPrice;
import ir.iconish.sanjinehsub.ui.activity.MainActivity;

/**
 * @author s.shaloudegi
 * @date 8/13/2019
 */
public class CoinAdapter extends RecyclerView.Adapter<ArchiveViewHolder> {
    public CoinAdapter(List<CoinPrice> coinPriceList, MainActivity mainActivity) {
    }

    @NonNull
    @Override
    public ArchiveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ArchiveViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
