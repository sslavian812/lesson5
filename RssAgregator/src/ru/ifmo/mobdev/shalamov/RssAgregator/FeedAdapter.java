package ru.ifmo.mobdev.shalamov.RssAgregator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: slavian
 * Date: 14.10.13
 * Time: 0:07
 * To change this template use File | Settings | File Templates.
 */
public class FeedAdapter extends BaseAdapter {
    Context context;
    LayoutInflater lInflater;
    ArrayList<FeedItem> feed;

    FeedAdapter(Context _context, ArrayList<FeedItem> _feed) {
        context = _context;
        feed = _feed;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return feed.size();
    }

    @Override
    public Object getItem(int position) {
        return feed.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;  // reusing
        if (view == null)
            view = lInflater.inflate(R.layout.list_item, parent, false);

        FeedItem f = (FeedItem) getItem(position);

        ((TextView) view.findViewById(R.id.date)).setText(f.date);
        ((TextView) view.findViewById(R.id.title)).setText(f.title);
        ((TextView) view.findViewById(R.id.description)).setText(f.description);


        return view;
    }


}
