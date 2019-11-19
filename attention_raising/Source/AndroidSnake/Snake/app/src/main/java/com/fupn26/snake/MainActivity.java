package com.fupn26.snake;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.Button;

import com.fupn26.snake.classes.Coordinate;
import com.fupn26.snake.engine.GameEngine;
import com.fupn26.snake.enums.CurrentView;
import com.fupn26.snake.enums.Direction;
import com.fupn26.snake.enums.GameState;
import com.fupn26.snake.enums.Orientation;
import com.fupn26.snake.views.SnakeView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private GameEngine gameEngine;
    private SnakeView snakeView;
    private Handler handler = new Handler();
    private final long updateDelay = 250;
    private Runnable m_Runnable;
    private CurrentView currentView;
    private Orientation orientation;
    private Orientation prevOrientation;

    private float prevX, prevY;

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(m_Runnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("OnResume", Integer.toString(count++));
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(m_Runnable, updateDelay);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        handler.removeCallbacksAndMessages(null);

        Gson gson = new Gson();
        String view = gson.toJson(currentView);
        savedInstanceState.putString("View", view);

        if(currentView == CurrentView.MAIN) {
            String walls = gson.toJson(gameEngine.getWalls());
            String snake = gson.toJson(gameEngine.getSnake());
            String apples = gson.toJson(gameEngine.getApples());
            String state = gson.toJson(gameEngine.getCurrentState());
            String direction = gson.toJson(gameEngine.getDirection());

            savedInstanceState.putString("Walls", walls);
            savedInstanceState.putString("Snake", snake);
            savedInstanceState.putString("Apples", apples);
            savedInstanceState.putString("State", state);
            savedInstanceState.putString("Direction", direction);
            savedInstanceState.putInt("Orientation", getWindowManager().getDefaultDisplay().getRotation());
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);



//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        int orientationINT = getWindowManager().getDefaultDisplay().getRotation();//this.getResources().getConfiguration().orientation;
        switch (orientationINT){
            case Surface.ROTATION_0:
                this.orientation = Orientation.PORTRAIT;
                break;
            case Surface.ROTATION_90:
                this.orientation = Orientation.LANDSCAPE;
                break;
            case Surface.ROTATION_180:
                this.orientation = Orientation.REVERSEPORTRAIT;
                break;
            case Surface.ROTATION_270:
                this.orientation = Orientation.REVERSELANDSCAPE;
                break;
        }

        if (savedInstanceState == null) {
            setContentView(R.layout.activity_starting);
            currentView = CurrentView.START;

            Button start = findViewById(R.id.startbutton);
            Button quit = findViewById(R.id.quitbutton);

            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setContentView(R.layout.activity_main);
                    currentView = CurrentView.MAIN;
                    gameEngine = new GameEngine(orientation);

                    snakeView = (SnakeView) findViewById(R.id.snakeView);
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
        } else {
            Gson gson = new Gson();
            String view = savedInstanceState.getString("View");
            if (gson.fromJson(view, CurrentView.class) == CurrentView.MAIN) {
                setContentView(R.layout.activity_main);
                currentView = CurrentView.MAIN;
                String walls = savedInstanceState.getString("Walls");
                String snake = savedInstanceState.getString("Snake");
                String apples = savedInstanceState.getString("Apples");
                String state = savedInstanceState.getString("State");
                String direction = savedInstanceState.getString("Direction");
                int prevOrient = savedInstanceState.getInt("Orientation");

                if (!walls.isEmpty() && !snake.isEmpty() && !apples.isEmpty() && !state.isEmpty() && !direction.isEmpty()) {
                    Type coordinateArrayType = new TypeToken<ArrayList<Coordinate>>() {
                    }.getType();
                    switch (prevOrient){
                        case Surface.ROTATION_0:
                            this.prevOrientation = Orientation.PORTRAIT;
                            break;
                        case Surface.ROTATION_90:
                            this.prevOrientation = Orientation.LANDSCAPE;
                            break;
                        case Surface.ROTATION_180:
                            this.prevOrientation = Orientation.REVERSEPORTRAIT;
                            break;
                        case Surface.ROTATION_270:
                            this.prevOrientation = Orientation.REVERSELANDSCAPE;
                            break;
                    }
                    gameEngine = new GameEngine(gson.fromJson(walls, coordinateArrayType),
                            gson.fromJson(snake, coordinateArrayType),
                            gson.fromJson(apples, coordinateArrayType),
                            gson.fromJson(direction, Direction.class),
                            gson.fromJson(state, GameState.class), orientation, prevOrientation);
                } else {
                    gameEngine = new GameEngine(orientation);
                }
                snakeView = (SnakeView) findViewById(R.id.snakeView);
                CreateTouchListener();

                startUpdateHandler();


            } else if(gson.fromJson(view, CurrentView.class) == CurrentView.START){
                setContentView(R.layout.activity_starting);
                currentView = CurrentView.START;

                Button start = findViewById(R.id.startbutton);
                Button quit = findViewById(R.id.quitbutton);

                start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setContentView(R.layout.activity_main);
                        currentView = CurrentView.MAIN;
                        gameEngine = new GameEngine(orientation);

                        snakeView = (SnakeView) findViewById(R.id.snakeView);
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
            } else{
                OnGameLost();
            }

        }

    }

    @SuppressLint("ClickableViewAccessibility")
    private void CreateTouchListener() {
        snakeView.setOnTouchListener(this);
    }

    int count = 0;
    private void startUpdateHandler(){
        m_Runnable = new Runnable() {
            @Override
            public void run() {
                Log.i("Update", Integer.toString(count++));
                gameEngine.Update();

                if (gameEngine.getCurrentState() == GameState.Running){
                    handler.postDelayed(this, updateDelay);
                }
                if (gameEngine.getCurrentState() == GameState.Lost) {
                    OnGameLost();
                }
                    snakeView.setSnakeViewMap(gameEngine.getMap());
                    snakeView.setOrientation(orientation);
                    snakeView.invalidate();
            }
        };
        handler.postDelayed(m_Runnable,updateDelay);
    }

    private void OnGameLost() {
        Toast.makeText(this, "Game Over", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_gameover);
        currentView = CurrentView.GAME_OVER;

        Button retry = findViewById(R.id.retrybutton);
        Button quit = findViewById(R.id.quitbutton_2);

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_main);
                currentView = CurrentView.MAIN;
                gameEngine = new GameEngine(orientation);

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
