package com.maxplus.sostudy.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.maxplus.sostudy.R;

import java.util.ArrayList;

public class LookAnswerActivity extends Activity {

    private ImageButton backButton;
    private TextView yourAnswer, suecssAnswer, right_rate;
    private ArrayList answer1, sucess1;
    double errorAnswer = 0;
    double rightAnswer = 0;
    double rightRate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_answer);
        backButton = (ImageButton) findViewById(R.id.back_Button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        yourAnswer = (TextView) findViewById(R.id.your_answer);
        suecssAnswer = (TextView) findViewById(R.id.suecss_answer);
        right_rate = (TextView) findViewById(R.id.right_rate);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        answer1 = bundle.getParcelableArrayList("answer");
        sucess1 = bundle.getParcelableArrayList("sucess");
        yourAnswer.setText(answer1.toString().replaceAll("\\]", "").replaceAll("\\[", ""));
        suecssAnswer.setText(sucess1.toString().replaceAll("\\[\"", "").replaceAll("\",\"", "").replaceAll("\"]", "").replaceAll("\\]", "").replaceAll("\\[", ""));
        doCompare(answer1, sucess1);
        rightRate = 100 * (rightAnswer / (rightAnswer + errorAnswer));
        right_rate.setText("" + rightRate + "%");
    }

    private void doCompare(ArrayList answer, ArrayList sucess) {
        String[] answerArray = answer.toString().replaceAll("\\]", "").replaceAll("\\[", "").split(",");
        String[] sucessArray = sucess.toString().replaceAll("\\]", "").replaceAll("\\[", "").split(",");
        Log.d("answer+sucess==>>>", answerArray.toString() + ":" + sucessArray.toString());
        for (int i = 0; i < answerArray.length; i++) {
            if (answerArray[i].equals(sucessArray[i])) {
                rightAnswer = rightAnswer + 1;
            } else {
                errorAnswer = errorAnswer + 1;
            }
        }

    }

}

//}
/**
 * answer1.toString().replaceAll("\\]", "").replaceAll("\\[", "").replaceAll(",", "").replaceAll(" ", "").trim(),
 * sucess1.toString().replaceAll("\\]", "").replaceAll("\\[", "").replaceAll(",", "").replaceAll(" ", "").trim());
 */