package com.londonappbrewery.quizzler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    // TODO: Declare member variables here:
    Button mTrueButton;
    Button mFalseButton;
    TextView mQuestionTextView;
    int mIndex; //to track the question number //default start at 0
    int mQuestion;
    int mScore; //To track the score
    TextView mScoreTextView;
    ProgressBar mProgressBar;

    //to quit after dialog box appears
    int mQuit=0;
    // TODO: Uncomment to create question bank
    private TrueFalse[] mQuestionBank = new TrueFalse[] {
            new TrueFalse(R.string.question_1, true),
            new TrueFalse(R.string.question_2, true),
            new TrueFalse(R.string.question_3, true),
            new TrueFalse(R.string.question_4, true),
            new TrueFalse(R.string.question_5, true),
            new TrueFalse(R.string.question_6, false),
            new TrueFalse(R.string.question_7, true),
            new TrueFalse(R.string.question_8, false),
            new TrueFalse(R.string.question_9, true),
            new TrueFalse(R.string.question_10, true),
            new TrueFalse(R.string.question_11, false),
            new TrueFalse(R.string.question_12, false),
            new TrueFalse(R.string.question_13,true)
    };


    // TODO: Declare constants here
    final int PROGRESS_BAR_INCREMENT = (int) Math.ceil(100.0 / mQuestionBank.length); //updating each time

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            mScore = savedInstanceState.getInt("ScoreKey");
            mIndex = savedInstanceState.getInt("IndexKey");
            mQuit = savedInstanceState.getInt("mQuit");
        } else {
            mScore = 0;
            mIndex = 0;
        }

        // If screen has been rotated after the game ends
        if(mIndex == 0 && mQuit == 1){
            //AlertDialog.Builder alert = new AlertDialog.Builder(getApplicationContext());
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            // This refers to the current object, which here is our main activity
            alert.setTitle("Well Played! Game Over");
            alert.setCancelable(false);
            alert.setMessage("You scored "+mScore+ " points! Congrats!");
            alert.setPositiveButton("Close Application", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish(); //close app
                }
            });
            alert.show();
        }

        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mScoreTextView = (TextView) findViewById(R.id.score);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        // User defined method in the class TrueFalse
        mQuestion = mQuestionBank[mIndex].getQuestionId();
        //the question id could be used to set text
        mQuestionTextView.setText(mQuestion);

        //updating the view, especially after the screen rotate
        mScoreTextView.setText("Score "+mScore+ "/"+mQuestionBank.length);

        mTrueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d("Quizzler", "Button clicked");
                //Toast myToast = Toast.makeText(getApplicationContext(),
                //        "True Clicked", Toast.LENGTH_SHORT);
                checkAnswer(true);
                updateQuestion();

            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "False Pressed",
                 //       Toast.LENGTH_SHORT);
                checkAnswer(false);
                updateQuestion();
            }
        });


    }

    // Updating to the next questions
    private void updateQuestion() {
        mIndex = (mIndex + 1) % mQuestionBank.length; //number of question -> mQuestionBank.length

        if(mIndex == 0){
            //AlertDialog.Builder alert = new AlertDialog.Builder(getApplicationContext());
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            // This refers to the current object, which here is our main activity
            alert.setTitle("Well Played! Game Over");
            alert.setCancelable(false);
            alert.setMessage("You scored "+mScore+ " points! Congrats!");
            mQuit = 1;
            alert.setPositiveButton("Close Application", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish(); //close app
                }
            });
            alert.show();
        }

        mQuestion = mQuestionBank[mIndex].getQuestionId();
        mQuestionTextView.setText(mQuestion);
        mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
        mScoreTextView.setText("Score " + mScore + "/" + mQuestionBank.length);
    }

    private void checkAnswer(boolean userSelection) {
        boolean correctAnswer = mQuestionBank[mIndex].isAnswer();

        if(correctAnswer == userSelection) {
            Toast.makeText(getApplicationContext(), R.string.correct_toast, Toast.LENGTH_SHORT).show();
            mScore+=1;

        } else {
            Toast.makeText(getApplicationContext(), R.string.incorrect_toast, Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("ScoreKey", mScore);
        outState.putInt("IndexKey", mIndex);
        outState.putInt("mQuit", mQuit);
    }
}
