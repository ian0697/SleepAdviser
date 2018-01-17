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
import android.widget.Toast;

import com.santiagoapps.sleepadviser.R;

import java.util.ArrayList;

/**
 * Created by Ian on 10/23/2017.
 */

public class MusicAdapter extends BaseAdapter {

    private Context context;

    private int layout;
    private ArrayList<Music> arraylist;
    private MediaPlayer player;
    private View currentView;
    private ViewHolder newView;
    private int currentPos;
    private Music[] musicList;



    Boolean isPaused = true;
    int PAUSE_VALUE = 0;

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
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
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





//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               if(isPaused){
//                    player= MediaPlayer.create(context, music.getSong());
//                    isPaused = false;
//                    currentPos = position;
//                    currentView = view;
//                    //currentViewHolder = viewHolder;
//                    //currentPos = 1
//                }
//
//                if(player.isPlaying()){
//
//                    if(currentPos!=position && currentView!=view){
//                        player.stop();
//                        player.release();
//                        player = MediaPlayer.create(context, music.getSong());
//                        currentPos = position;
//                        player.start();
//
//
//                    }
//                    else{
//                        player.pause();
//                        PAUSE_VALUE = player.getCurrentPosition();
//                        isPaused=true;
//
//
//                        viewHolder.ivPlay.setImageResource(R.drawable.play);
//                    }
//
//
//
//                } else{
//                    //Continue
//                    if(player!=null) {
//                        player.seekTo(PAUSE_VALUE);
//                    }
//                    player.start();
//                    Toast.makeText(context, "you reached this context", Toast.LENGTH_SHORT).show();
//
//                    viewHolder.ivPlay.setImageResource(R.drawable.pause);
//                }
//
//
//            }
//        });

        viewHolder.ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(isPaused){
                    player= MediaPlayer.create(context, music.getSong());
                    isPaused = false;
                    currentPos = position;
                    currentView = view;
                    //currentViewHolder = viewHolder;
                    //currentPos = 1
                }

                if(player.isPlaying()){

                    if(currentPos!=position && currentView!=view){
                        player.stop();
                        player.release();
                        player = MediaPlayer.create(context, music.getSong());
                        currentPos = position;
                        player.start();
                    }
                    else{
                        player.pause();
                        PAUSE_VALUE = player.getCurrentPosition();
                        isPaused=true;
                        viewHolder.ivPlay.setImageResource(R.drawable.play);
                    }
                } else{
                    //Continue
                    if(player!=null) {
                        player.seekTo(PAUSE_VALUE);
                    }
                    player.start();
                    Toast.makeText(context, "you reached this context", Toast.LENGTH_SHORT).show();

                    viewHolder.ivPlay.setImageResource(R.drawable.pause);
                }
            }
        });

        viewHolder.ivPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isPaused){
                    player.stop();
                    player.release();
                    PAUSE_VALUE = 0;
                    isPaused =true;
                }
                viewHolder.ivPlay.setImageResource(R.drawable.ic_play);
            }
        });


        return convertView;
    }



}
