package com.santiagoapps.sleepadviser.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.santiagoapps.sleepadviser.R;

public class FaqActivity extends AppCompatActivity {

    private ListView list;
    private Toolbar toolbar;
    public static String[] values = new String[]{
            "What’s your definition of sleep and what is sleep cycle?",
            "How would you describe the circadian rhythm and why is it so important?",
            "Is it harmful if someone sleeps more than his or her usual requirement of sleep?",
            "What happens if a person is sleep-deprived?",
            "Is being fatigued the same as being sleepy?",
            "Can anxiety and depression cause sleep problems? Why?",
            "Is exercise helpful for sleep? ",
            "Is there a relationship between certain foods and drinks with sleep?",
            "Can the lack of sleep be associated with your body weight?",
            "What can caffeine, alcohol, and tobacco do to sleep? ",
            "Why do I sometimes feel paralyzed?",
            "How is sleep affected by physical illnesses?",
            "How can emotional problems affect sleep?",
            "How does napping affect sleep at night?"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        list = findViewById(R.id.list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(FaqActivity.this, "Position: " + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(), QuestionAndAnswerActivity.class);
                intent.putExtra("question", position);
                startActivity(intent);

            }
        });

        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Frequently Asked Questions");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
