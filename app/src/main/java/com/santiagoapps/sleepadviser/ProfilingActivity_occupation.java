package com.santiagoapps.sleepadviser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.santiagoapps.sleepadviser.activities.NavigationActivity;

public class ProfilingActivity_occupation extends AppCompatActivity {
    String occupation , name, gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiling_occupation);

        //get intents
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        gender = (String) bd.get("SESSION_GENDER");
        name = (String) bd.get("SESSION_NAME");

        Button btnChoice3 = findViewById(R.id.occ_choice3);
        btnChoice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                occupation = "Etc.";
                Intent intent = new Intent(getBaseContext(), ProfilingActivity_age.class);
                intent.putExtra("SESSION_OCCUPATION", occupation);
                intent.putExtra("SESSION_GENDER", gender);
                intent.putExtra("SESSION_NAME", name);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });

        Button btnChoice2 = findViewById(R.id.occ_choice2);
        btnChoice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                occupation = "Employee";
                Intent intent = new Intent(getBaseContext(), ProfilingActivity_age.class);
                intent.putExtra("SESSION_OCCUPATION", occupation);
                intent.putExtra("SESSION_GENDER", gender);
                intent.putExtra("SESSION_NAME", name);
                startActivity(intent);
            }
        });

        Button btnChoice1 = findViewById(R.id.occ_choice1);
        btnChoice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                occupation = "Student";
                Intent intent = new Intent(getBaseContext(), ProfilingActivity_age.class);
                intent.putExtra("SESSION_OCCUPATION", occupation);
                intent.putExtra("SESSION_GENDER", gender);
                intent.putExtra("SESSION_NAME", name);
                startActivity(intent);
            }
        });

    }
}
