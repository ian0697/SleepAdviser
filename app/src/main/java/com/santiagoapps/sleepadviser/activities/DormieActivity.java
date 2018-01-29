package com.santiagoapps.sleepadviser.activities;

import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.santiagoapps.sleepadviser.R;

import org.w3c.dom.Text;

public class DormieActivity extends AppCompatActivity {

    private BottomSheetBehavior bottomSheetBehavior;
    private LinearLayout linearLayout;
    private boolean isExpanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dormie);
        linearLayout = (LinearLayout)findViewById(R.id.bottomsheet);

        bottomSheetBehavior = BottomSheetBehavior.from(linearLayout);
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

        TextView tvProceed = linearLayout.findViewById(R.id.btnProceed);
        tvProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceed();
            }
        });




    }

    private void proceed(){
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        finish();

    }
}
