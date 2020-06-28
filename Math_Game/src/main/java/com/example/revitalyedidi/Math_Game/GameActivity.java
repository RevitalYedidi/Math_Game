package com.example.revitalyedidi.Math_Game;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.revitalyedidi.game3.R;

import java.util.Random;

public class GameActivity extends Activity implements View.OnClickListener{

    int correctAnswer;
    Button buttonObjectChoice1;
    Button buttonObjectChoice2;
    Button buttonObjectChoice3;
    TextView textObjectPartA;
    TextView textObjectPartB;
    TextView textObjectScore;
    TextView textObjectLevel;
    TextView textOperator;
    TextView textView4;
    ImageView heart1, heart2, heart3;
    LinearLayout lives;
    ConstraintLayout  layout;

    int currentScore = 0;
    int currentLevel = 1;
    int countToUpLevel = 0;
    int countWrongs = 3;

    Toast toast;
    ImageView toastImage;
    MediaPlayer levelUpSound , wrongSound , goodSound, gameOverSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        textObjectPartA = findViewById(R.id.textPartA);
        textObjectPartB = findViewById(R.id.textPartB);
        textObjectScore = findViewById(R.id.textScore);
        textObjectLevel = findViewById(R.id.textLevel);
        textOperator = findViewById(R.id.textOperator);
        textView4 = findViewById(R.id.textView4);
        buttonObjectChoice1 = findViewById(R.id.buttonChoice1);
        buttonObjectChoice2 = findViewById(R.id.buttonChoice2);
        buttonObjectChoice3 = findViewById(R.id.buttonChoice3);

        layout = (ConstraintLayout) findViewById(R.id.viewId);

        buttonObjectChoice1.setOnClickListener(this);
        buttonObjectChoice2.setOnClickListener(this);
        buttonObjectChoice3.setOnClickListener(this);

        setQuestion();

        // button end game
        Button buttonEndGame= (Button) findViewById(R.id.buttonEndGame);
        buttonEndGame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
            endGame();
            }
        });

        toast = new Toast(getApplicationContext());
        toastImage = new ImageView(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, -30, 0);
        toast.setDuration(Toast.LENGTH_SHORT);

        levelUpSound= MediaPlayer.create(this, R.raw.level_up_sound);
        gameOverSound= MediaPlayer.create(this, R.raw.game_over_sound);
        goodSound= MediaPlayer.create(this, R.raw.good_sound);
        wrongSound= MediaPlayer.create(this, R.raw.wrong_sound);

        lives= findViewById(R.id.lives);
        heart1= findViewById(R.id.heart1);
        heart2= findViewById(R.id.heart2);
        heart3= findViewById(R.id.heart3);

    }//onCreate ends here


    @Override
    public void onClick(View view) {
        int answerGiven=0;
        switch (view.getId()) {

            case R.id.buttonChoice1:
                //initialize a new int with the value contained in buttonObjectChoice1
                //Remember we put it there ourselves previously
                answerGiven = Integer.parseInt("" + buttonObjectChoice1.getText());
                break;

            case R.id.buttonChoice2:
                answerGiven = Integer.parseInt("" + buttonObjectChoice2.getText());
                break;

            case R.id.buttonChoice3:
                answerGiven = Integer.parseInt("" + buttonObjectChoice3.getText());
                break;

        }

        updateScoreAndLevel(answerGiven);

        if (countWrongs!=0) {
            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    layout.setBackgroundResource(R.drawable.notebook);
                    toast.cancel();
                    setQuestion();

                }

            }, 900);
        }
    }

    void setQuestion()
    {

        Random randInt = new Random();
        int partA=1;
        int partB=1;
        int wrongAnswer1=1;
        int wrongAnswer2=1;
        if(currentLevel==1)
        {
            partA = randInt.nextInt(10-2)+2;
            partB= randInt.nextInt(10-2)+2;
        }
        else
            if(currentLevel==2)
            {
                partA = randInt.nextInt(10-2)+2;
                partB= randInt.nextInt(20-11)+11;
            }
        else
            {
                partA = randInt.nextInt((currentLevel * 10)  - (11)) + (11);
                partB = randInt.nextInt((currentLevel * 10)  - ((currentLevel * 10)-10)) + (currentLevel * 10)-10 ;
            }


        correctAnswer = partA * partB;

        while (Math.abs(wrongAnswer2 % 10)!= Math.abs(correctAnswer % 10) || Math.abs(wrongAnswer1 % 10)!= Math.abs(correctAnswer % 10) ||wrongAnswer1==correctAnswer||(correctAnswer == wrongAnswer2) || (wrongAnswer1 == wrongAnswer2)
                ||wrongAnswer1>correctAnswer+40||wrongAnswer1<correctAnswer-40||wrongAnswer2>correctAnswer+40|| wrongAnswer2<correctAnswer-40
                ||wrongAnswer1==partA||wrongAnswer1==partB||wrongAnswer2==partA||wrongAnswer2==partB)
        {
        if (currentLevel==1)
            {
                wrongAnswer1=randInt.nextInt(100-4)+4;
                wrongAnswer2=randInt.nextInt(100-4)+4;
            }

        else
            if (currentLevel==2)
                {
                    wrongAnswer1=randInt.nextInt(200-22)+22;
                    wrongAnswer2=randInt.nextInt(200-22)+22;
                }

            else
                    {
                        wrongAnswer1 = randInt.nextInt((currentLevel * 10) * (currentLevel * 10) - (11 * 11)) + 11 * 11;
                        wrongAnswer2 = randInt.nextInt((currentLevel * 10) * (currentLevel * 10) - ((currentLevel * 10)-10)*(currentLevel * 10)-10) + ((currentLevel * 10)-10)*((currentLevel * 10)-10);
                    }
            }

        textObjectPartA.setText(""+partA);
        textObjectPartB.setText(""+partB);

        //set the multi choice buttons
        //A number between 0 and 2
        int buttonLayout = randInt.nextInt(3);
        switch (buttonLayout){
            case 0:
                buttonObjectChoice1.setText(""+correctAnswer);
                buttonObjectChoice2.setText(""+wrongAnswer1);
                buttonObjectChoice3.setText(""+wrongAnswer2);
                break;

            case 1:
                buttonObjectChoice2.setText(""+correctAnswer);
                buttonObjectChoice3.setText(""+wrongAnswer1);
                buttonObjectChoice1.setText(""+wrongAnswer2);
                break;

            case 2:
                buttonObjectChoice3.setText(""+correctAnswer);
                buttonObjectChoice1.setText(""+wrongAnswer1);
                buttonObjectChoice2.setText(""+wrongAnswer2);
                break;
        }
    }


    void updateScoreAndLevel(int answerGiven){

        if(isCorrect(answerGiven)){

            layout.setBackgroundResource(R.drawable.notebook_green);
            currentScore++;
            countToUpLevel++;

            if (countToUpLevel==currentLevel+3){
                toastImage.setImageResource(R.drawable.level_up_2);

                levelUpSound.start();
                currentLevel++;
                countToUpLevel=0;
                toast.setView(toastImage);
                toast.show();
            }
            else
                goodSound.start();
        }
        else{
            layout.setBackgroundResource(R.drawable.notebook_red);
            countWrongs--;

            //update the hearts view
            switch (countWrongs){
                case 2:
                    heart3.setVisibility(View.INVISIBLE);
                    lives.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    heart2.setVisibility(View.INVISIBLE);
                    heart1.setVisibility(View.VISIBLE);
                    lives.setVisibility(View.VISIBLE);
                    break;
                case 0:
                    heart1.setVisibility(View.INVISIBLE);
                    lives.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;


            }


            //game over
            if (countWrongs==0)
            {
                gameOver();


                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                        endGame();
                    }

                }, 1800);
            }
            else
                wrongSound.start();
        }

        textObjectScore.setText("Score: " + currentScore);
        textObjectLevel.setText("Level: " + currentLevel);


    }

    boolean isCorrect(int answerGiven){
        boolean correctTrueOrFalse;
        if(answerGiven == correctAnswer){
            correctTrueOrFalse=true;
        }else{//Uh-oh!
            correctTrueOrFalse=false;
        }

        return correctTrueOrFalse;
    }


    public void endGame(){
        Intent j;
        j = new Intent(this, MainActivity.class);
        j.putExtra("final scores", String.valueOf(currentScore));
        j.putExtra("final level", String.valueOf(currentLevel));
        j.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(j);
    }

    public void gameOver(){

        gameOverSound.start();
        layout.setBackgroundResource(R.drawable.notebook_darkred);

        textObjectPartA.setVisibility(View.INVISIBLE);
        textObjectPartB.setVisibility(View.INVISIBLE);
        textOperator.setVisibility(View.INVISIBLE);
        textView4.setVisibility(View.INVISIBLE);
        buttonObjectChoice1.setVisibility(View.INVISIBLE);
        buttonObjectChoice2.setVisibility(View.INVISIBLE);
        buttonObjectChoice3.setVisibility(View.INVISIBLE);

        toastImage.setImageResource(R.drawable.game_over_4);
        toast.setView(toastImage);
        toast.show();

    }
}