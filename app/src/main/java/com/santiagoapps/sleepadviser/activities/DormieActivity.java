package com.santiagoapps.sleepadviser.activities;

import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.santiagoapps.sleepadviser.R;

public class DormieActivity extends AppCompatActivity {

    private BottomSheetBehavior bottomSheetBehavior;
    private boolean isExpanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dormie);

        //final View bottomSheet = findViewById(R.id.bottomsheet);
//        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
//        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//
//        Button btn1 = (Button) findViewById(R.id.btn1);
//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(!isExpanded){
//                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//                } else{
//                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//                }
//
//                isExpanded = !isExpanded;
//
//            }
//        });


    }
}
