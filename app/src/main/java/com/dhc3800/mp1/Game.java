package com.dhc3800.mp1;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Game extends AppCompatActivity {
    private TextView timeLeft;
    private int optionChosen;
    private ImageView image;

    private final String option1 = "choice1";
    private final String option2 = "choice2";
    private final String option3 = "choice3";
    private final String option4 = "choice4";
    private int score = 0;
    int prevScore = 0;
    int prevCount = 0;
    int count = 0;
    int index;
    private int correctChoice;
    String name;
    List<String> memberNames;
    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button endGame = findViewById(R.id.endButton);
        image = findViewById(R.id.imageView);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
                startActivity(intent);
            }
        });

        endGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog xd = new AlertDialog.Builder(Game.this).create();
                xd.setMessage("Do you want to leave?");
                xd.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Game.this, MainActivity.class);
                        startActivity(intent);
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                });
                xd.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                xd.show();



            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionChosen = 0;
                timer.cancel();
                showResult();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionChosen = 1;
                timer.cancel();
                showResult();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionChosen = 2;
                timer.cancel();
                showResult();
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionChosen = 3;
                timer.cancel();
                showResult();
            }
        });

        String[] memberName = getIntent().getStringArrayExtra("memberNames");
        memberNames = Arrays.asList(memberName);
        index = 0;
        showQuestion();

        /* shuffledDisplay(memberName); */
    }


    public void showQuestion() {
        displayPicture(memberNames.get(index));
        optionChosen = -1;
        correctChoice = generateChoices(memberNames, memberNames.get(index));
        name = memberNames.get(index);
        timer = new CountDownTimer(5000, 1000) {
            public void onTick(long milliLeft) {
                timeLeft = findViewById(R.id.timeLeft);
                timeLeft.setText(String.valueOf(milliLeft/1000));
            }
            public void onFinish() {
                showResult();
            }
        };
        timer.start();

    }

    public void showResult() {
        count += 1;
        TextView Count = findViewById(R.id.CountView);
        Count.setText("Total Count: " + String.valueOf(count));
        if (optionChosen == -1) {
            Toast.makeText(this, "Time out!", Toast.LENGTH_SHORT).show();
        } else if (optionChosen == correctChoice) {
            score += 1;
            TextView xd = findViewById(R.id.score);
            xd.setText("Score: " + String.valueOf(score));
            count += 1;
            Toast.makeText(this, "You are correct!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Incorrect, the correct answer is " + name, Toast.LENGTH_SHORT).show();
        }
        index += 1;
        if (index == memberNames.size() - 1) {
            Collections.shuffle(memberNames);
            index = 0;
        }
        showQuestion();

    }





    void displayPicture(String s) {
        String x = s.replaceAll(" ", "");
        String name = x.toLowerCase();
        ImageView picture = findViewById(R.id.imageView);
        Resources res = getResources();
        int resID = res.getIdentifier(name, "drawable", getPackageName());
        picture.setImageResource(resID);
    }

    int generateChoices(List<String> members, String name){
        ArrayList<String> choices = new ArrayList();
        Random random = new Random();
        int pIndex = random.nextInt(members.size());
        for (int i = 0; i < 3; i++) {
            while ((members.get(pIndex) == name) || (choices.contains(members.get(pIndex)))) {
                pIndex = random.nextInt(members.size());
            }
            choices.add(members.get(pIndex));
        }
        choices.add(name);
        Collections.shuffle(choices);
        setOptions(choices);
        return choices.indexOf(name);
    }

    void setOptions(ArrayList<String> choices) {
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        button1.setText(choices.get(0));
        button2.setText(choices.get(1));
        button3.setText(choices.get(2));
        button4.setText(choices.get(3));
    }
}
