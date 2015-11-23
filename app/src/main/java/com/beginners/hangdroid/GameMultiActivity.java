package com.beginners.hangdroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class GameMultiActivity extends ActionBarActivity {

    String mWord ;

    int mFailCounter = 0;

    int mGuessedLetters = 0;

    int mPoints = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_game);

        String wordSent = getIntent().getStringExtra("WORD_IDENTIFIER");

        mWord = wordSent;

        createTextView(wordSent);


    }

    public void introduceLetter(View v){

        EditText myEditText = (EditText) findViewById(R.id.editTextLetter);

        String letter = myEditText.getText().toString();

        myEditText.setText("");

        Log.d("MYLOG", "The letter is " + letter);

        if(letter.length() > 0) {
            checkLetter(letter.toUpperCase());
        }else{
            Toast.makeText(this,"Please introduce a letter",Toast.LENGTH_SHORT).show();
        }

    }

    public void checkLetter(String introducedLetter){

        char charIntroduced = introducedLetter.charAt(0);

        Boolean letterGuessed = false;

        for( int i =0 ; i < mWord.length() ; i++){

            char charFromTheWord = mWord.charAt(i);

            if (charFromTheWord == charIntroduced){

                Log.d("MYLOG","There was one match");

                letterGuessed = true;

                showLettersAtIndex(i,charIntroduced);

                mGuessedLetters++;

            }
        }
        if (letterGuessed == false){
            letterFailed(Character.toString(charIntroduced));

        }

        if (mGuessedLetters == mWord.length()){
            finish();
        }
    }


    public void createTextView(String word){

        LinearLayout layoutLetters = (LinearLayout) findViewById(R.id.LayoutLetters);

        for (int i = 0; i < word.length(); i++){
            TextView newTextView = (TextView) getLayoutInflater().inflate(R.layout.textview,null);

            layoutLetters.addView(newTextView);
        }

    }


    public void clearScreen() {
        TextView textViewFailed = (TextView) findViewById(R.id.textView6);
        textViewFailed.setText("");

        mGuessedLetters = 0;
        mFailCounter = 0;

        LinearLayout layoutLetters = (LinearLayout) findViewById(R.id.LayoutLetters);

        for (int i=0; i < layoutLetters.getChildCount() ; i++ ){

            TextView currentTextView = (TextView) layoutLetters.getChildAt(i);
            currentTextView.setText("_");

        }

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.hangdroid_0);
    }

    public void letterFailed(String letterFailed){

        TextView textViewFailed = (TextView) findViewById(R.id.textView6);

        String previousFail = textViewFailed.getText().toString();

        textViewFailed.setText(previousFail+letterFailed);

        mFailCounter++;
        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        if(mFailCounter == 1){
            imageView.setImageResource(R.drawable.hangdroid_1);
        }else if (mFailCounter == 2){
            imageView.setImageResource(R.drawable.hangdroid_2);
        }else if (mFailCounter == 3){
            imageView.setImageResource(R.drawable.hangdroid_3);
        }else if (mFailCounter == 4){
            imageView.setImageResource(R.drawable.hangdroid_4);
        }else if (mFailCounter == 5){
            imageView.setImageResource(R.drawable.hangdroid_5);
        }else if (mFailCounter == 6){

            Intent gameOverIntent = new Intent(this,GameOverActivity.class);

            gameOverIntent.putExtra("POINTS_IDENTIFIER",mPoints);

            startActivity(gameOverIntent);
        }
    }

    public void showLettersAtIndex(int position, char letterGuessed){

        LinearLayout layoutLetter = (LinearLayout) findViewById(R.id.LayoutLetters);

        TextView textView = (TextView) layoutLetter.getChildAt(position);

        textView.setText(Character.toString(letterGuessed));

    }
}
