package com.santiagoapps.sleepadviser.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.santiagoapps.sleepadviser.R;
import com.santiagoapps.sleepadviser.activities.FaqActivity;
import com.santiagoapps.sleepadviser.activities.NavigationActivity;

public class QuestionAndAnswerActivity extends AppCompatActivity {


    private int question;
    private String[] answers = new String[]{
            "Sleep is a state of altered consciousness which is required by the body for rest and restorative functions. Sleep cycle is an alternate of NREM (non-rapid eye movement) and REM (rapid eye movement) which are different states of sleep.",
            "The circadian rhythm is the inner control of the body which consists of hormonal signals that regulates timing of internal activities like temperature, heart rate, blood pressure and including the signals which control initiation of sleep and duration. ",
            "No, there is no harm in sleeping more than the requirement.",
            "Since sleep is restorative for brain functions, during sleep deprivation, there may be slowing of processes like coordination, thinking, and memory retrieval.",
            "Fatigue is a general/broad term for being tired/overworked. Being sleepy can contribute to feeling of fatigue, but not all fatigued individuals are sleepy or will be resolved by sleeping if there are other underlying problems.",
            "Yes, anxiety and depression which are psychiatric/mood disorders are characterized also by changes in neurotransmitters and changes may suppress the initiation of sleep, or even depression can cause hypersomnia or excess of sleeping.",
            "Yes, there are studies that show that exercise, by stimulating hormones and improving blood flow to the brain may improve sleep. ",
            "Yes, stimulants such as caffeinated beverages (cola, coffee) may delay initiation of sleep, while downers, like alcohol may help with sleep.",
            "There are many factors that are associated with body weight, but a direct correlation may not be established at this time",
            "Caffeine can delay sleep initiation, alcohol can increase sleep or initiate sleepiness, and tobacco may also induce wakefulness and delay sleep.",
            "There is such a term as sleep paralysis, wherein a person is awake but there is inability to move voluntary muscles. This happens when one has not yet full transitioned from sleep to wakefulness, or even when a person is still transitioning to sleep.",
            "It depends on the physical illness. Some diseases have an effect on sleep and wakefulness, and some have no effect at all.",
            "Emotional problems are linked to physical manifestations due to the mind-body connection. Emotional problems release stress hormones and this can affect sleep.",
            "It may delay sleep initiation in the evening. For those with difficulty in sleeping in the evening, they are advised to limit daytime napping. For some, it does not affect at all."
    };

    private TextView tvQuestion, tvAnswer;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_and_answer);

        Intent intent = getIntent();
        if(intent != null) {
            Bundle bd = intent.getExtras();
            question = bd.getInt("question");
            tvQuestion = findViewById(R.id.tvQuestion);
            tvQuestion.setText(FaqActivity.values[question]);
            tvAnswer = findViewById(R.id.tvAnswer);
            tvAnswer.setText(answers[question]);
        } else {
            Toast.makeText(this, "Oops intent is null", Toast.LENGTH_SHORT).show();
        }

        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.action_faq:
                        finish();
                        break;
                    case R.id.action_home:
                        finish();
                        startActivity(new Intent(getBaseContext(), NavigationActivity.class));
                }

                return true;
            }
        });


    }
}
