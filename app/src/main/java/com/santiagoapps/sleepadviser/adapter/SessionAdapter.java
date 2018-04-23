package com.santiagoapps.sleepadviser.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.santiagoapps.sleepadviser.R;
import com.santiagoapps.sleepadviser.data.model.Music;
import com.santiagoapps.sleepadviser.data.model.Session;
import com.santiagoapps.sleepadviser.helpers.DateHelper;

import java.util.ArrayList;
import java.util.List;

public class SessionAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Session> arraylist;

    public SessionAdapter(Context context, int layout, ArrayList<Session> arraylist) {
        this.context = context;
        this.layout = layout;
        this.arraylist = arraylist;
    }

    @Override
    public int getCount() {
        return arraylist.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;



        if(convertView==null){
            viewHolder = new SessionAdapter.ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(layout, null);

            viewHolder.tvDate = convertView.findViewById(R.id.tvDate);
            viewHolder.tvDuration = convertView.findViewById(R.id.tvDuration);
            viewHolder.tvRating = convertView.findViewById(R.id.tvRating);
            viewHolder.tvSleep = convertView.findViewById(R.id.tvSleep);
            viewHolder.icCircle = convertView.findViewById(R.id.icCircle);
            viewHolder.listSession = convertView.findViewById(R.id.listSession);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (SessionAdapter.ViewHolder)convertView.getTag();
        }

        final Session session = arraylist.get(position);
        viewHolder.tvRating.setText(session.getSleepQualityDesc());
        viewHolder.tvDuration.setText(session.getSleep_duration());
        viewHolder.tvDate.setText(DateHelper.dateToDayMonth(session.getSleepDate()));
        viewHolder.tvSleep.setText(DateHelper.dateToTime(session.getSleepDate()));

        if(session.getDurationInMills() > 25200000){
            viewHolder.icCircle.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorGreen));
        } else {

            viewHolder.icCircle.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorRed));
        }

        return convertView;
    }


    private class ViewHolder{
        TextView tvDuration, tvDate, tvRating, tvSleep;
        ListView listSession;
        Button icCircle;
    }
}
