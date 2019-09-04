package ir.iconish.sanjinehsub.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import ir.iconish.sanjinehsub.R;
import ir.iconish.sanjinehsub.data.model.SearchResult;

/**
 * @author s.shaloudegi
 * @date 8/31/2019
 */
public class SearchResultAdapter extends ArrayAdapter<SearchResult> {
    private Context context;
    private int resourceId;
    private List<SearchResult> items, tempItems, suggestions;
    private Filter searchResultFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            SearchResult searchResult = (SearchResult) resultValue;
            return searchResult.getTitle();
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null) {
                suggestions.clear();
                for (SearchResult searchResult : tempItems) {
                    if (searchResult.getTitle().toLowerCase().startsWith(charSequence.toString().toLowerCase())) {
                        suggestions.add(searchResult);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ArrayList<SearchResult> tempValues = (ArrayList<SearchResult>) filterResults.values;
            if (filterResults != null && filterResults.count > 0) {
                clear();
                for (SearchResult searchResultObj : tempValues) {
                    add(searchResultObj);
                    notifyDataSetChanged();
                }
            } else {
                clear();
                notifyDataSetChanged();
            }
        }
    };

    public SearchResultAdapter(@NonNull Context context, int resourceId, ArrayList<SearchResult> items) {
        super(context, resourceId, items);
        this.items = items;
        this.resourceId = resourceId;
        this.context = context;
        tempItems = new ArrayList<>(items);
        suggestions = new ArrayList<>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        try {
            if (convertView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                view = inflater.inflate(resourceId, parent, false);
            }
            SearchResult searchResult = getItem(position);
            TextView name = view.findViewById(R.id.search_result_text);
            name.setText(searchResult.getTitle());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @Nullable
    @Override
    public SearchResult getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return searchResultFilter;
    }
}
