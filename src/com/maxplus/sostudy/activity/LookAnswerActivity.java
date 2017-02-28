package com.maxplus.sostudy.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.maxplus.sostudy.R;

import java.util.ArrayList;

public class LookAnswerActivity extends Activity {

    private ImageButton backButton;
    private TextView yourAnswer, suecssAnswer;
    private ArrayList answer1, sucess1;
    ArrayList<String> answer = new ArrayList<>();
    ArrayList<String> sucess = new ArrayList<>();

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
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        answer1 = bundle.getParcelableArrayList("answer");
        sucess1 = bundle.getParcelableArrayList("sucess");
        yourAnswer.setText(answer1.toString().replaceAll("\\]", "").replaceAll("\\[", ""));
//        doSetSuecss(sucess1);
//        suecssAnswer.setText(sucess1.toString());
        String aa = sucess1.toString().replaceAll("0", "A");
        String bb = aa.replaceAll("1", "B");
        String cc = bb.replaceAll("2", "C");
        String dd = cc.replaceAll("3", "D");
        suecssAnswer.setText(dd.replaceAll("\\]", "").replaceAll("\\[", ""));
    }

    private void doSetSuecss(ArrayList sucess1) {
        for (int i = 0; i < sucess1.size(); i++) {
            if (sucess1.get(i).toString().equals("0")) {
                sucess1.set(i, "A");
            } else if (sucess1.get(i).toString().equals("1")) {
                sucess1.set(i, "B");
            } else if (sucess1.get(i).toString().equals("2")) {
                sucess1.set(i, "C");
            } else if (sucess1.get(i).toString().equals("3")) {
                sucess1.set(i, "D");
            }
            sucess1.add(i, sucess);
        }
    }

}
