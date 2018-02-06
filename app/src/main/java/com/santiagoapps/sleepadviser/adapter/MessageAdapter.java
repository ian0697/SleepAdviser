package com.santiagoapps.sleepadviser.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.santiagoapps.sleepadviser.R;
import com.santiagoapps.sleepadviser.data.model.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IAN on 2/7/2018.
 */

public class MessageAdapter extends ArrayAdapter<Message> {
    private List<Message> chat_list = new ArrayList<>();
    private TextView chat_text;
    private Context context;
    private int res;

    public MessageAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
        res = resource;

    }

    @Override
    public void add(Message object) {
        chat_list.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return chat_list.size();
    }

    @Override
    public Message getItem(int position) {
        return chat_list.get(position);
    }

    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(res, parent,false);
        }
        //TextView message holder
        chat_text = convertView.findViewById(R.id.singleMessage);

        String message;
        boolean isDormie;

        Message message1 = getItem(position);
        message = message1.getMessage();
        isDormie = message1.isDormie();

        //set the message to the textview
        chat_text.setText(message);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        //if the message is dormie, bubble will placed right. else left
        if(isDormie) {
            params.gravity = Gravity.RIGHT;
        }
        else {
            params.gravity = Gravity.LEFT;
        }

        chat_text.setLayoutParams(params);
        return convertView;
    }
}
