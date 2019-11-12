package ir.iconish.sanjinehsub.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.ButterKnife;
import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.adapter.listener.RecyclerIemListener;
import ir.iconish.sanjinehsub.adapter.viewholder.NewsViewHolder;
import ir.iconish.sanjinehsub.data.model.NewsItem;

/**
 * @author s.shaloudegi
 * @date 8/21/2019
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {


    RecyclerIemListener recyclerIemListener;
    private List<NewsItem> newsItems;
    Context mContext;

    public NewsAdapter(Context context, List<NewsItem> newsItems, RecyclerIemListener recyclerIemListener) {
        this.newsItems = newsItems;

        mContext = context;
        this.recyclerIemListener = recyclerIemListener;

    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item_row, parent, false);
        ButterKnife.bind(this, itemView);


        return new NewsViewHolder(itemView);

    }

    private static final String TAG = "NewsAdapter";

    @Override
    public void onBindViewHolder(@NonNull final NewsViewHolder viewHolder, final int i) {


        final NewsItem newsItem = newsItems.get(i);
        Glide.with(mContext).load(newsItem.getImgUrl()).into(viewHolder.newsImage);
        viewHolder.newsImage.setImageResource(newsItem.getDrawbleId());
        viewHolder.newsText.setText(newsItem.getTitle());
        String tmpDescription = newsItem.getDecribtion();
        String pattern = "(?i)(<p.*?>)(.+?)()";
        String updated = tmpDescription.replaceAll(pattern, "$2");
        viewHolder.newsDescription.setText(updated);
        Log.d(TAG, "updated: " + updated);
     /*   viewHolder.navItemIcon.setImageResource(otherServiceItem.getDrawbleId());
        viewHolder.txtTitle.setText(otherServiceItem.getTitle());
     */

        viewHolder.view.setOnClickListener(v -> recyclerIemListener.onTap(newsItem));

    }


    @Override
    public int getItemCount() {
        return (null != newsItems ? newsItems.size() : 0);

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
