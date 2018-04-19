package com.santiagoapps.sleepadviser.activities.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.santiagoapps.sleepadviser.app.IOnStartup;
import com.santiagoapps.sleepadviser.R;
import com.santiagoapps.sleepadviser.activities.NavigationActivity;

public class ProfilingActivity_name extends AppCompatActivity implements IOnStartup {

    private EditText txtName;
    private RadioButton rb1, rb2;
    private RadioGroup radioGroup;
    private String name, gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiling_name);

        // if app already started previously
        if(!isFirstStart()){
            startNextActivity();
            finish();
        }

        txtName = (EditText) findViewById(R.id.input_name);
        Button BtnSubmit = findViewById(R.id.BtnSubmit);
        BtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = txtName.getText().toString();
                if (!txtName.getText().equals("")) {
                    Intent intent = new Intent(getBaseContext(), ProfilingActivity_occupation.class);
                    intent.putExtra("SESSION_NAME", name);
                    intent.putExtra("SESSION_GENDER", gender);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    setFirstTimeStatus(false);
                }

            }
        });


        rb1 = findViewById(R.id.rbMale);
        rb2 = findViewById(R.id.rbFemale);
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checked) {
                if(checked == R.id.rbMale){
                    gender = "Male";
                }
                else if (checked == R.id.rbFemale){
                    gender = "Female";
                }
            }
        });


    }

    public boolean isFirstStart(){
        SharedPreferences ref = getApplicationContext().getSharedPreferences("Dormie", Context.MODE_PRIVATE);
        return ref.getBoolean("FirstTimeProfile", true);
    }

    public void setFirstTimeStatus(boolean flag){
        SharedPreferences ref = getApplicationContext().getSharedPreferences("Dormie", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = ref.edit();
        editor.putBoolean("FirstTimeProfile", flag);
        editor.commit();
    }

    private void startNextActivity(){
        setFirstTimeStatus(false);
        startActivity(new Intent(ProfilingActivity_name.this, NavigationActivity.class));
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        finish();
    }

}
