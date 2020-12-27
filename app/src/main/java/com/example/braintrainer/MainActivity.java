package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button mStartButton;
    private TextView mSumTextView, mResultTextView, mPointsTextView, mTimerView;
    private ArrayList<Integer> mAnswers = new ArrayList<Integer>();
    private Button mButton0, mButton1, mButton2, mButton3, mPlayAgainButton;
    private int mA;
    private int mB;
    private int mScore = 0;
    private int mQuestionCount = 0;
    private ConstraintLayout mGameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGameLayout = findViewById(R.id.game_layout);

        mStartButton = findViewById(R.id.start_button);

        mSumTextView = findViewById(R.id.sum_textView);

        mPointsTextView = findViewById(R.id.points_textView);

        mTimerView = findViewById(R.id.timer_textView);

        mButton0 = findViewById(R.id.button0);
        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);
        mButton3 = findViewById(R.id.button3);
        mPlayAgainButton = findViewById(R.id.play_again_button);

        mResultTextView = findViewById(R.id.result_textView);

    }

    private void startTimer() {
        CountDownTimer countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimerView.setText((int) millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                mPlayAgainButton.setVisibility(View.VISIBLE);
                mResultTextView.setText("Your score: " + mScore + "/" + mQuestionCount);
            }
        }.start();
    }

    public void start(View view) {

        mStartButton.setVisibility(View.INVISIBLE);
        mGameLayout.setVisibility(View.VISIBLE);
        playAgain(findViewById(R.id.play_again_button));

    }

    public void chooseAnswer(View view) {

        mQuestionCount++;
        int tag = Integer.parseInt((String) view.getTag());

        int answerSelected = mAnswers.get(tag);

        if (answerSelected == mA + mB) {
            mResultTextView.setText("Correct!");
            mScore++;
        } else {
            mResultTextView.setText("Wrong!");
        }

        updateScore();

        setNewQuestion();

    }

    private void setNewQuestion() {
        Random random = new Random();

        mA = random.nextInt(21);
        mB = random.nextInt(31);

        mSumTextView.setText(mA + " + " + mB);

        int locationOfCorrectAnswer = random.nextInt(4);

        mAnswers.clear();

        int incorrectAnswer;

        for (int i = 0; i < 4; i++) {
            if (i == locationOfCorrectAnswer) {
                mAnswers.add(mA + mB);
            } else {
                incorrectAnswer = random.nextInt(61);
                while (incorrectAnswer == mA + mB) {
                    incorrectAnswer = random.nextInt(61);
                }
                mAnswers.add(incorrectAnswer);
            }
        }

        mButton0.setText(Integer.toString(mAnswers.get(0)));
        mButton1.setText(Integer.toString(mAnswers.get(1)));
        mButton2.setText(Integer.toString(mAnswers.get(2)));
        mButton3.setText(Integer.toString(mAnswers.get(3)));
    }

    private void updateScore() {
        mPointsTextView.setText(mScore + "/" + mQuestionCount);
        Log.d("Points", "Score: " + mScore + " out of: " + mQuestionCount);

    }

    public void playAgain(View view) {
        mScore = 0;
        mQuestionCount = 0;
        mTimerView.setText("30s");
        mPointsTextView.setText("0/0");
        mResultTextView.setText("");
        startTimer();
        setNewQuestion();
        mPlayAgainButton.setVisibility(View.INVISIBLE);
    }
}