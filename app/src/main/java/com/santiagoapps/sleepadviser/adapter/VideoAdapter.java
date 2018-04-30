package com.santiagoapps.sleepadviser.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.santiagoapps.sleepadviser.R;
import com.santiagoapps.sleepadviser.activities.SleepVideoActivity;
import com.santiagoapps.sleepadviser.data.model.VideoSrc;

import java.util.ArrayList;

public class VideoAdapter extends BaseAdapter {

    private ArrayList<VideoSrc> arraylist;
    private Context context;
    private int layout;

    public VideoAdapter(Context context, int layout, ArrayList<VideoSrc> arraylist) {
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
    public View getView(final int pos, View convertView, ViewGroup viewGroup) {
        final VideoAdapter.ViewHolder viewHolder;


        if(convertView==null){
            viewHolder = new VideoAdapter.ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(layout, null);

            viewHolder.tvDescription = convertView.findViewById(R.id.tvDescription);
            viewHolder.tvTitle = convertView.findViewById(R.id.tvTitle);
            viewHolder.ivVideo = convertView.findViewById(R.id.ivVideo);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (VideoAdapter.ViewHolder)convertView.getTag();
        }

        final VideoSrc vid = arraylist.get(pos);
        viewHolder.tvTitle.setText(vid.getTitle());
        viewHolder.tvDescription.setText(vid.getDescription());
        viewHolder.ivVideo.setImageDrawable(context.getResources().getDrawable(vid.getPath()));
        viewHolder.ivVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SleepVideoActivity.class);
                intent.putExtra("position", pos);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    private class ViewHolder{
        TextView tvDescription, tvTitle;
        ImageView ivVideo;
    }
}
