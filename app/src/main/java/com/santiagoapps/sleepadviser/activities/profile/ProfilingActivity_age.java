package com.santiagoapps.sleepadviser.activities.profile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.santiagoapps.sleepadviser.R;

public class ProfilingActivity_age extends AppCompatActivity {
    Button btnSubmit;
    EditText txtAge;
    String gender, name, occupation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiling_age);

        txtAge = findViewById(R.id.txtAge);
        btnSubmit = (Button)findViewById(R.id.BtnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmit();
            }
        });

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        gender = (String) bd.get("SESSION_GENDER");
        name = (String) bd.get("SESSION_NAME");
        occupation = (String) bd.get("SESSION_OCCUPATION");

    }

    private void onSubmit(){
        if(!txtAge.getText().equals("")){
            int age = Integer.parseInt(txtAge.getText().toString());

            Intent intent = new Intent(getBaseContext(), ProfilingActivity_sleep.class);
            intent.putExtra("SESSION_AGE", age);
            intent.putExtra("SESSION_GENDER", gender);
            intent.putExtra("SESSION_NAME",name);
            intent.putExtra("SESSION_OCCUPATION", occupation);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);


        }
    }
}
