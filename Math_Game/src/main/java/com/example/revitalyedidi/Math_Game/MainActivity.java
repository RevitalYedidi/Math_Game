/*Android assignment
Assignment 3 – Game 3
Android studio

Revital Yedidi and Naama Ohana
ID 208378752, ID 311144364
e-mail: revitalosh123@gmail.com ,naamab2606@gmail.com

Second Year
Computer Science Department- Ashqelon Academic College


Assignment

Get a program that runs, and make changes / additions according to the requirements.
Do a fun and interesting math game.

Goal

Work with an existing program.
Fix what is not work or add things to upgrade the game.

Classes

mainActivity.java
activity_main.xml
GameActivity.java
activity_game.xml

Other Files
The project contain 3 folders:
    -manifest
    -java
    -res
Images for the design:  (located in the folder 'drawable' inside res)
board.jpg
game_over_4.png
heart_2.png
level_up_2.png
notebook.jpg
notebook_darkred.jpg
notebook_green.jpg
notebook_red.jpg

Sounds: (located in the folder 'raw' inside res)
game_over_sound.wav
good_sound.wav
level_up_sound.wav
wrong_sound.wav


Running the program

To run the program follow these steps:
1. Open Google Drive using the URL in the mail.
2. Download the file to your computer
3. Open Android Studio and run the file you have downloaded

The operation

*Please turn on speakers !!*
After running the application a window will open.
Start a new game by click on the 'play' button.
Choose the answer from the 3 options below the math question.
You have 3 times to fail until the game will over.


Algorithms
None.


*/




package com.example.revitalyedidi.Math_Game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.revitalyedidi.game3.R;


public class MainActivity extends Activity implements View.OnClickListener{

    public int finalScores=0;
    public int finalLevel=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //play button
        Button buttonPlay = (Button)findViewById(R.id.buttonPlay);
        buttonPlay.setOnClickListener(this);


        //quit button
        Button buttonQuit = (Button) findViewById(R.id.buttonQuit);
        buttonQuit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        //final scores button
        Button buttonScores = (Button) findViewById(R.id.buttonScores);
        buttonScores.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                try {
                    finalScores = Integer.parseInt(getIntent().getStringExtra("final scores"));
                    finalLevel= Integer.parseInt(getIntent().getStringExtra("final level"));
                    Toast.makeText(getApplicationContext(),"Final Scores: "+ finalScores+" At Level: "+finalLevel ,Toast.LENGTH_LONG ).show();
                } catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Click on Play to start a game" ,Toast.LENGTH_LONG ).show();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent i;
        i = new Intent(this, GameActivity.class);
        startActivity(i);
    }

}





