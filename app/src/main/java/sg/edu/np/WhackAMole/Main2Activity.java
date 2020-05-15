package sg.edu.np.WhackAMole;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class Main2Activity extends AppCompatActivity {
    TextView Score;
    int Count;
    Button B1; Button B2; Button B3; Button B4; Button B5; Button B6; Button B7; Button B8; Button B9;
    Button[] advBList;
    long millisUntilFinished;
    long interval;
    CountDownTimer myCountDown;
    CountDownTimer MoleCountDown;

    private void readyTimer(){
        millisUntilFinished = 5000;
        interval = 1000;
        myCountDown = new CountDownTimer(millisUntilFinished, interval) {

            @Override
            public void onTick(long millisUntilFinished) {
                Toast.makeText(Main2Activity.this,"Get Ready In " + millisUntilFinished/interval, Toast.LENGTH_SHORT).show();
                Log.d("#d", "Ready CountDown!" + millisUntilFinished/interval);
                //Score.setText("" + (millisUntilFinished/interval));
            }

            @Override
            public void onFinish() {
                Toast.makeText(Main2Activity.this,"GO!", Toast.LENGTH_SHORT).show();
                Log.d("#d","Ready Countdown Complete!");
                placeMoleTimer();
            }
        };
        myCountDown.start();
    }
    private void placeMoleTimer(){
        MoleCountDown = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                setNewMole();
                Log.d("#d", "New Mole Location!");
            }

            @Override
            public void onFinish() {
                MoleCountDown.start();
            }
        };
        MoleCountDown.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Score = findViewById(R.id.score);
        B1 = findViewById(R.id.b1);
        B2 = findViewById(R.id.b2);
        B3 = findViewById(R.id.b3);
        B4 = findViewById(R.id.b4);
        B5 = findViewById(R.id.b5);
        B6 = findViewById(R.id.b6);
        B7 = findViewById(R.id.b7);
        B8 = findViewById(R.id.b8);
        B9 = findViewById(R.id.b9);

        //Get the score from basic mode
        Intent receivingEnd = getIntent();
        Count = receivingEnd.getIntExtra("Count", 0);
        Log.d("#d", "Current User Score: " + Count);
    }

    @Override
    protected void onStart(){
        super.onStart();
        advBList = new Button[]{B1,B2,B3,B4,B5,B6,B7,B8,B9};
        Score.setText("" + (Count));
        readyTimer();
        for (final Button b : advBList) {
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    doCheck(b);
                }
            });
        }
    }

    private void doCheck(Button b)
    {
        if (b.getText() == "*"){
            Score.setText("" + (Count+=1));
            Log.v("#d", "Hit, score added!");
        }
        else {
            Score.setText("" + (Count-=1));
            Log.v("#d", "Missed, point deducted!");
        }
    }

    public void setNewMole()
    {
        Random ran = new Random();
        int randomLocation = ran.nextInt(9);
        for (int i = 0; i<advBList.length; i++){
            if(randomLocation==i){
                advBList[i].setText("*");
            }
            else{
                advBList[i].setText("O");
            }
        }
    }
}

