package com.santiagoapps.sleepadviser;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SleepingActivity extends AppCompatActivity {
    Dialog myDialog;
    TextView txtTime;
    TextView txtAm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleeping);
        myDialog = new Dialog(this);

        txtTime = (TextView)findViewById(R.id.txtTime);
        txtAm = (TextView)findViewById(R.id.txtAm);





    }

    public void wakeUp(View v){
        TextView txtClose;
        CardView btnWake;
        CardView btnFlash;
        myDialog.setContentView(R.layout.unlocked_popup);

        txtClose = (TextView) myDialog.findViewById(R.id.txtClose);
        btnWake = (CardView) myDialog.findViewById(R.id.btnWake);
        btnFlash = (CardView) myDialog.findViewById(R.id.btnFlash);

        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });

        myDialog.show();


    }
}
