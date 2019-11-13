package com.fupn26.snake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;

import com.fupn26.snake.engine.GameEngine;
import com.fupn26.snake.enums.Direction;
import com.fupn26.snake.enums.GameState;
import com.fupn26.snake.views.SnakeView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private GameEngine gameEngine;
    private SnakeView snakeView;
    private final Handler handler = new Handler();
    private final long updateDelay = 250;
    private Runnable m_Runnable;

    private float prevX, prevY;

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(m_Runnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(m_Runnable, updateDelay);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);

        Button start = findViewById(R.id.startbutton);
        Button quit = findViewById(R.id.quitbutton);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_main);
                gameEngine = new GameEngine();
                gameEngine.initGame();

                snakeView = (SnakeView)findViewById(R.id.snakeView);
                CreateTouchListener();

                startUpdateHandler();

            }
        });

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
    }

    private void CreateTouchListener() {
        snakeView.setOnTouchListener(this);
    }

    private void startUpdateHandler(){
        m_Runnable = new Runnable() {
            @Override
            public void run() {
                gameEngine.Update();

                if (gameEngine.getCurrentState() == GameState.Running){
                    handler.postDelayed(this, updateDelay);
                }
                if (gameEngine.getCurrentState() == GameState.Lost) {
                    OnGameLost();
                }

                snakeView.setSnakeViewMap(gameEngine.getMap());
                snakeView.invalidate();
            }
        };
        handler.postDelayed(m_Runnable,updateDelay);
    }

    private void OnGameLost() {
        Toast.makeText(this, "Game Over", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_gameover);

        Button retry = findViewById(R.id.retrybutton);
        Button quit = findViewById(R.id.quitbutton_2);

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_main);
                gameEngine = new GameEngine();
                gameEngine.initGame();

                snakeView = (SnakeView)findViewById(R.id.snakeView);
                CreateTouchListener();

                startUpdateHandler();

            }
        });

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                prevX = event.getX();
                prevY = event.getY();

                break;
            case MotionEvent.ACTION_UP:
                float newX = event.getX();
                float newY = event.getY();


                if(Math.abs(newX - prevX) > Math.abs(newY - prevY)){
                    if ( newX > prevX){
                        //RIGHT
                        gameEngine.UpdateDirection(Direction.East);
                    } else {
                        //LEFT
                        gameEngine.UpdateDirection(Direction.West);
                    }
                }else{
                    if (newY > prevY){
                        //UP
                        gameEngine.UpdateDirection(Direction.South);
                    } else {
                        //DOWN
                        gameEngine.UpdateDirection(Direction.North);
                    }
                }
                break;
        }

        return true;
    }

}
