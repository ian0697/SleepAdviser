package com.santiagoapps.sleepadviser.class_library;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.santiagoapps.sleepadviser.R;
import com.santiagoapps.sleepadviser.class_library.Music;

import java.util.ArrayList;

/**
 * Created by Ian on 10/23/2017.
 */

public class MusicAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Music> arraylist;

    Boolean flag = true;
    private MediaPlayer player;

    public MusicAdapter(Context context, int layout, ArrayList<Music> arraylist) {
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

    private class ViewHolder{
        TextView txtName, txtCategory;
        ImageView ivPlay,ivPause;
        ListView songList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;

        if(convertView==null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(layout,null);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            viewHolder.txtCategory = (TextView) convertView.findViewById(R.id.txtCategory);
            viewHolder.ivPause = (ImageView) convertView.findViewById(R.id.pause);
            viewHolder.ivPlay = (ImageView) convertView.findViewById(R.id.play);
            viewHolder.songList = (ListView) convertView.findViewById(R.id.songList);
            convertView.setTag(viewHolder);
            Log.d("Message", "Convert view is null");

        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        final Music music = arraylist.get(position);
        viewHolder.txtName.setText(music.getMusic_name());
        viewHolder.txtCategory.setText(music.getCategory());




        viewHolder.ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flag){
                    player= MediaPlayer.create(context, music.getSong());
                    flag=false;
                }
                if(player.isPlaying()){
                    player.pause();
                    viewHolder.ivPlay.setImageResource(R.drawable.ic_play);
                    flag=true;
                } else{
                    player.start();
                    viewHolder.ivPlay.setImageResource(R.drawable.ic_pause);
                }

            }
        });

        viewHolder.ivPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!flag){
                    player.stop();
                    player.release();
                    flag=true;
                }
                viewHolder.ivPlay.setImageResource(R.drawable.ic_play);
            }
        });


        return convertView;
    }
}
