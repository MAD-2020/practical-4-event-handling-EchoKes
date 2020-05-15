package sg.edu.np.WhackAMole;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button B1;
    Button B2;
    Button B3;
    TextView Score;
    Button[] bList;
    String[] lrm;
    int Count = 0;
    int Count2 = 1;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        B1 = findViewById(R.id.b1);
        B2 = findViewById(R.id.b2);
        B3 = findViewById(R.id.b3);
        Score = findViewById(R.id.score);

        Log.d("#d", "Finished Pre-Initialisation!");
    }
    @Override
    protected void onStart(){
        super.onStart();
        bList = new Button[]{B1, B2, B3};
        lrm = new String[]{"Button Left Clicked!","Button Middle Clicked!","Button Right Clicked!"};
        Score.setText("0");
        setNewMole();
        Log.d("#d", "Starting GUI!");

        for(int i=0; i<bList.length; i++){
            final Button checkButton = bList[i];
            final int I = i;
            bList[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("#d",lrm[I]);
                    if (doCheck(checkButton)){
                        Count2 += 1;
                        Score.setText("" + (Count+=1));
                    }
                    else {
                        Count2 -= 1;
                        Score.setText("" + (Count-=1));
                    }
                    setNewMole();
                }
            });
        }
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.d("d", "Paused Whack-A-Mole!");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d("#d", "Stopped Whack-A-Mole!");
        finish();
    }

    private boolean doCheck(Button checkButton) {
        boolean check;

        if(checkButton.getText() == "*") {
            check = true;
        }
        else{
            check = false;
        }
        if(Count2==10){
            nextLevelQuery();
            Count2 = 0;
        }
        return check;
    }

    private void nextLevelQuery(){
        builder = new AlertDialog.Builder(this);
        builder.setMessage("Would you like to advanced to advanced mode?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("#d","User accepts!");
                        nextLevel();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                        Log.d("#d","User decline!");
                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle("Warning! Insane Whack-A-Mole incoming!");
        alert.show();
        Log.d("#d","Advance option given to user!");
    }

    private void nextLevel(){
        //Launch advanced page
        Intent intent = new Intent(this, Main2Activity.class);
        intent.putExtra("Count", Count);
        startActivity(intent);
    }

    private void setNewMole() {
        Random ran = new Random();
        int randomLocation = ran.nextInt(3);
        for(int i = 0; i<bList.length; i++){
            if(randomLocation == i){
                bList[i].setText("*");
            }
            else{
                bList[i].setText("O");
            }
        }
    }


}