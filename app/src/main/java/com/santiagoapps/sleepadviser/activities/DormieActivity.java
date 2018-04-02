package com.santiagoapps.sleepadviser.activities;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Handler;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.santiagoapps.sleepadviser.R;
import com.santiagoapps.sleepadviser.adapter.MessageAdapter;
import com.santiagoapps.sleepadviser.data.model.Message;

/**
 * This class is a trial of the application
 * of bottomsheetview or Dormie's Conversation
 *
 */

public class DormieActivity extends AppCompatActivity {

    private BottomSheetBehavior bottomSheetBehavior;
    private ListView chat_list;
    private MessageAdapter mAdapter;
    private boolean isExpanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dormie);

        LinearLayout bottomsheet = (LinearLayout)findViewById(R.id.bottomsheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomsheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        Button btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isExpanded){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else{
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }

                isExpanded = !isExpanded;

            }
        });

        TextView tvProceed = bottomsheet.findViewById(R.id.choice_1);
        tvProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceed();
            }
        });

        TextView tvOther = bottomsheet.findViewById(R.id.choice_2);
        tvOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.add(new Message(false, "What can you do?"));
            }
        });


        mAdapter = new MessageAdapter(this,R.layout.single_message);
        chat_list = bottomsheet.findViewById(R.id.list_chat);
        chat_list.setAdapter(mAdapter);
        chat_list.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        chat_list.setDivider(null);
        chat_list.setDividerHeight(0);
        setInitialMessage();

        mAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                chat_list.setSelection(mAdapter.getCount()-1);
            }
        });
    }

    private void setInitialMessage(){
        mAdapter.add(new Message(true,"Hi, Ian Santiago! I'm Dormie. Your personal sleep assistant."));
        new Handler().postDelayed(new Runnable() {

        @Override
            public void run() {
                mAdapter.add(new Message(true,"I will guide you how to use this app. I wish you'll have a goodnight sleep!"));
            }
        }, 3000);

    }

    private void proceed(){
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        finish();

    }
}
