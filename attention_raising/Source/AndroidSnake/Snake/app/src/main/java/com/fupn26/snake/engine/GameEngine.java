package com.fupn26.snake.engine;

import android.os.Bundle;
import android.util.Log;

import com.fupn26.snake.classes.Coordinate;
import com.fupn26.snake.enums.Direction;
import com.fupn26.snake.enums.GameState;
import com.fupn26.snake.enums.Orientation;
//import com.fupn26.snake.enums.TileType;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class GameEngine {
    //for Landscape mode
    public int GameWidth = 42 + 3 ;     // for portrait 28;
    public int GameHeight = 28 - 3 - 2;    // fo portrait 42;

    private final String TileType[] = {"Nothing", "Wall", "SnakeHead", "SnakeTail", "Apple"};

    private List<Coordinate> walls = new ArrayList<>();
    private List<Coordinate> snake = new ArrayList<>();
    private List<Coordinate> apples = new ArrayList<>();

    private Direction currentDirection = Direction.East;

    private GameState currentGameState = GameState.Running;

    private Random random = new Random();

    private boolean increaseTail = false;

    public void SwitchCoordinates() {
//        for (Coordinate wall:walls) {
//            int x = wall.getY();
//            int y = wall.getX();
//            wall.setX(x);
//            wall.setY(y);
//        }

        for (Coordinate snake_element : snake) {
            int x = snake_element.getY();
            int y = snake_element.getX();
            snake_element.setX(x);
            snake_element.setY(y);
        }

        for (Coordinate apple : apples) {
            int x = apple.getY();
            int y = apple.getX();
            apple.setX(x);
            apple.setY(y);
        }
    }

    private Coordinate getSnakeHead() {
        return snake.get(0);
    }

    public GameEngine(Orientation orienntation) {
        if (orienntation == Orientation.PORTRAIT) {
            GameHeight += GameWidth;
            GameWidth = GameHeight - GameWidth;
            GameHeight -= GameWidth;
        }
        initGame();
    }

    public GameEngine(List<Coordinate> walls, List<Coordinate> snake,
                      List<Coordinate> apples, Direction direction, GameState gameState,
                      Orientation orientation, Orientation prevOrientation) {

        this.snake = snake;
        this.apples = apples;
        this.currentDirection = direction;
        this.currentGameState = gameState;

        SwitchCoordinates();
//        SetDirection(orientation, prevOrientation, direction);
        if (orientation == Orientation.PORTRAIT || orientation == Orientation.REVERSEPORTRAIT) {
            GameHeight += GameWidth;
            GameWidth = GameHeight - GameWidth;
            GameHeight -= GameWidth;
        }
        AddWalls();
    }


    private void SetDirection(Orientation orientation, Orientation prevOrientation, Direction direction){

        Coordinate irany = new Coordinate(snake.get(0).getX() - snake.get(1).getX(), snake.get(0).getY() - snake.get(1).getY());
        switch(orientation){
            case PORTRAIT:
                switch(prevOrientation){
//                    case PORTRAIT:
//                        if(irany.getX()==-1)
//                        {
//                            if (irany.getY() == 0) this.currentDirection = Direction.North;
//                            else if (irany.getY() == -1) this.currentDirection = Direction.West;
//                            else if (irany.getY() == 1) this.currentDirection = Direction.East;
//                        }
//                        else if(irany.getX()==0)
//
//                        {
//                            if (irany.getY() == 1) this.currentDirection = Direction.East;
//                            else if (irany.getY() == -1) this.currentDirection = Direction.West;
//                        }
//                        else if(irany.getX()==1)
//
//                        {
//                            if (irany.getY() == 0) this.currentDirection = Direction.South;
//                            else if (irany.getY() == -1) this.currentDirection = Direction.West;
//                            else if (irany.getY() == 1) this.currentDirection = Direction.East;
//                        }
//
//                        break;
                    case LANDSCAPE:
//                        if(irany.getX()==-1)
//                        {
//                            if (irany.getY() == 0) this.currentDirection = Direction.West;
//                            else if (irany.getY() == -1) this.currentDirection = Direction.South;
//                            else if (irany.getY() == 1) this.currentDirection = Direction.North;
//                        }
//                        else if(irany.getX()==0)
//
//                        {
//                            if (irany.getY() == 1) this.currentDirection = Direction.North;
//                            else if (irany.getY() == -1) this.currentDirection = Direction.South;
//                        }
//                        else if(irany.getX()==1)
//
//                        {
//                            if (irany.getY() == 0) this.currentDirection = Direction.East;
//                            else if (irany.getY() == -1) this.currentDirection = Direction.South;
//                            else if (irany.getY() == 1) this.currentDirection = Direction.North;
//                        }
                        switch(direction){

                            case North:
                                this.currentDirection = Direction.South;
                                break;
                            case East:
                                this.currentDirection = Direction.North;
                                break;
                            case South:
                                this.currentDirection = Direction.North;
                                break;
                            case West:
                                this.currentDirection = Direction.East;
                                break;
                        }
                        break;
                    case REVERSEPORTRAIT:
                        break;
                    case REVERSELANDSCAPE:
                        if(irany.getX()==-1)
                        {
                            if (irany.getY() == 0) this.currentDirection = Direction.East;
                            else if (irany.getY() == -1) this.currentDirection = Direction.North;
                            else if (irany.getY() == 1) this.currentDirection = Direction.South;
                        }
                        else if(irany.getX()==0)

                        {
                            if (irany.getY() == 1) this.currentDirection = Direction.South;
                            else if (irany.getY() == -1) this.currentDirection = Direction.North;
                        }
                        else if(irany.getX()==1)

                        {
                            if (irany.getY() == 0) this.currentDirection = Direction.West;
                            else if (irany.getY() == -1) this.currentDirection = Direction.North;
                            else if (irany.getY() == 1) this.currentDirection = Direction.South;
                        }
                        break;
                }
                break;
            case LANDSCAPE:
                switch(prevOrientation){

                    case PORTRAIT:
                        if(irany.getX()==-1)
                        {
                            if (irany.getY() == 0) this.currentDirection = Direction.West;
                            else if (irany.getY() == -1) this.currentDirection = Direction.South;
                            else if (irany.getY() == 1) this.currentDirection = Direction.North;
                        }
                        else if(irany.getX()==0)

                        {
                            if (irany.getY() == 1) this.currentDirection = Direction.North;
                            else if (irany.getY() == -1) this.currentDirection = Direction.South;
                        }
                        else if(irany.getX()==1)

                        {
                            if (irany.getY() == 0) this.currentDirection = Direction.East;
                            else if (irany.getY() == -1) this.currentDirection = Direction.South;
                            else if (irany.getY() == 1) this.currentDirection = Direction.North;
                        }

                        break;
//                    case LANDSCAPE:
//                        if(irany.getX()==-1)
//                        {
//                            if (irany.getY() == 0) this.currentDirection = Direction.North;
//                            else if (irany.getY() == -1) this.currentDirection = Direction.West;
//                            else if (irany.getY() == 1) this.currentDirection = Direction.East;
//                        }
//                        else if(irany.getX()==0)
//
//                        {
//                            if (irany.getY() == 1) this.currentDirection = Direction.East;
//                            else if (irany.getY() == -1) this.currentDirection = Direction.West;
//                        }
//                        else if(irany.getX()==1)
//
//                        {
//                            if (irany.getY() == 0) this.currentDirection = Direction.South;
//                            else if (irany.getY() == -1) this.currentDirection = Direction.West;
//                            else if (irany.getY() == 1) this.currentDirection = Direction.East;
//                        }
//                        break;
                    case REVERSEPORTRAIT:
                        break;
                    case REVERSELANDSCAPE:
                        if(irany.getX()==-1)
                        {
                            if (irany.getY() == 0) this.currentDirection = Direction.South;
                            else if (irany.getY() == -1) this.currentDirection = Direction.East;
                            else if (irany.getY() == 1) this.currentDirection = Direction.West;
                        }
                        else if(irany.getX()==0)

                        {
                            if (irany.getY() == 1) this.currentDirection = Direction.West;
                            else if (irany.getY() == -1) this.currentDirection = Direction.East;
                        }
                        else if(irany.getX()==1)

                        {
                            if (irany.getY() == 0) this.currentDirection = Direction.North;
                            else if (irany.getY() == -1) this.currentDirection = Direction.East;
                            else if (irany.getY() == 1) this.currentDirection = Direction.West;
                        }

                        break;
                }
                break;
            case REVERSEPORTRAIT:
                break;
            case REVERSELANDSCAPE:
                switch(prevOrientation){

                    case PORTRAIT:
                        if(irany.getX()==-1)
                        {
                            if (irany.getY() == 0) this.currentDirection = Direction.East;
                            else if (irany.getY() == -1) this.currentDirection = Direction.North;
                            else if (irany.getY() == 1) this.currentDirection = Direction.South;
                        }
                        else if(irany.getX()==0)

                        {
                            if (irany.getY() == 1) this.currentDirection = Direction.South;
                            else if (irany.getY() == -1) this.currentDirection = Direction.North;
                        }
                        else if(irany.getX()==1)

                        {
                            if (irany.getY() == 0) this.currentDirection = Direction.West;
                            else if (irany.getY() == -1) this.currentDirection = Direction.South;
                            else if (irany.getY() == 1) this.currentDirection = Direction.North;
                        }

                        break;
                    case LANDSCAPE:
                        if(irany.getX()==-1)
                        {
                            if (irany.getY() == 0) this.currentDirection = Direction.South;
                            else if (irany.getY() == -1) this.currentDirection = Direction.East;
                            else if (irany.getY() == 1) this.currentDirection = Direction.West;
                        }
                        else if(irany.getX()==0)

                        {
                            if (irany.getY() == 1) this.currentDirection = Direction.West;
                            else if (irany.getY() == -1) this.currentDirection = Direction.East;
                        }
                        else if(irany.getX()==1)

                        {
                            if (irany.getY() == 0) this.currentDirection = Direction.North;
                            else if (irany.getY() == -1) this.currentDirection = Direction.East;
                            else if (irany.getY() == 1) this.currentDirection = Direction.West;
                        }

                        break;
                    case REVERSEPORTRAIT:
                        break;
//                    case REVERSELANDSCAPE:
//                        if(irany.getX()==-1)
//                        {
//                            if (irany.getY() == 0) this.currentDirection = Direction.North;
//                            else if (irany.getY() == -1) this.currentDirection = Direction.West;
//                            else if (irany.getY() == 1) this.currentDirection = Direction.East;
//                        }
//                        else if(irany.getX()==0)
//
//                        {
//                            if (irany.getY() == 1) this.currentDirection = Direction.East;
//                            else if (irany.getY() == -1) this.currentDirection = Direction.West;
//                        }
//                        else if(irany.getX()==1)
//
//                        {
//                            if (irany.getY() == 0) this.currentDirection = Direction.South;
//                            else if (irany.getY() == -1) this.currentDirection = Direction.West;
//                            else if (irany.getY() == 1) this.currentDirection = Direction.East;
//                        }
//
//                        break;
                }
                break;
        }

    }


    public List<Coordinate> getWalls(){
        return walls;
    }
    public List<Coordinate> getSnake(){
        return snake;
    }
    public List<Coordinate> getApples(){
        return apples;
    }

    public Direction getDirection(){
        return currentDirection;
    }
    public void initGame(){
        AddSnake();
        AddWalls();
        AddApples();
    }

    private void AddApples() {
        Coordinate coordinate = null;

        boolean added = false;

        while ( !added ){
            int x = 1 + random.nextInt(GameWidth - 2);
            int y = 1 + random.nextInt(GameHeight -2);

            coordinate = new Coordinate(x,y);
            boolean collision = false;

            for (Coordinate s : snake) {
                if(s.equals(coordinate)){
                    collision = true;
                    break;
                }
            }
            if(collision) continue;

            for (Coordinate s : apples) {
                if(apples.equals(coordinate)){
                    collision = true;
                    break;
                }
            }

            added = !collision;

        }

        apples.add(coordinate);
    }

    public void UpdateDirection(Direction newDirection){

        if(Math.abs(newDirection.ordinal() - currentDirection.ordinal()) % 2 == 1){
            currentDirection = newDirection;
        }
    }

    private void UpdateSnake(int x, int y){
    //    Log.i("UpdateSnake", Integer.toString(count++));
        int newX = snake.get(snake.size() - 1).getX();
        int newY = snake.get(snake.size() - 1).getY();

        for (int i = snake.size() - 1; i > 0 ; i--) {
            snake.get(i).setX(snake.get(i-1).getX());
            snake.get(i).setY(snake.get(i-1).getY());
        }

        if(increaseTail){
            snake.add(new Coordinate(newX, newY));
            increaseTail = false;
        }

        snake.get(0).setX(snake.get(0).getX() + x);
        snake.get(0).setY(snake.get(0).getY() + y);
    }

    private void AddSnake() {
        snake.clear();

        snake.add(new Coordinate(7,7));
        snake.add(new Coordinate(6,7));
        snake.add(new Coordinate(5,7));
        snake.add(new Coordinate(4,7));
        snake.add(new Coordinate(3,7));
        snake.add(new Coordinate(2,7));

    }

    public void Update (){
            switch (currentDirection) {

                case North:
                    UpdateSnake(0, -1);
                    break;
                case East:
                    UpdateSnake(1, 0);
                    break;
                case South:
                    UpdateSnake(0, 1);
                    break;
                case West:
                    UpdateSnake(-1, 0);
                    break;
            }

            for (Coordinate w : walls) {
                if (snake.get(0).equals(w)) {
                    currentGameState = GameState.Lost;
                }
            }

            for (int i = 1; i < snake.size(); ++i) {
                if (getSnakeHead().equals(snake.get(i))) {
                    currentGameState = GameState.Lost;
                    return;
                }
            }

            Coordinate appleToRemove = null;
            for (Coordinate apple : apples) {
                if (getSnakeHead().equals(apple)) {
                    appleToRemove = apple;
                    increaseTail = true;
                }
            }

            if (appleToRemove != null) {
                apples.remove(appleToRemove);
                AddApples();
            }
    }

    public String[][] getMap  (){
        String[][] map = new String[GameWidth][GameHeight];

        for (int i = 0; i < GameWidth; i++) {
            for (int j = 0; j < GameHeight; j++) {
                map[i][j] = TileType[0];
            }

        }

        for(Coordinate s : snake){
            map[s.getX()][s.getY()] = TileType[3];
        }

        map[snake.get(0).getX()][snake.get(0).getY()] = TileType[2];


        for (Coordinate a : apples) {
            map[a.getX()][a.getY()] = TileType[4];
        }

        for (Coordinate wall: walls) {
            map[wall.getX()][wall.getY()] = TileType[1];
        }
        return map;
     }

    private void AddWalls() {
        for (int i = 0; i < GameWidth; i++) {
            walls.add(new Coordinate(i,0));
            walls.add(new Coordinate(i, GameHeight - 1));
        }

        for (int i = 1; i < GameHeight; i++) {
            walls.add(new Coordinate(0, i));
            walls.add(new Coordinate(GameWidth-1, i));
        }
    }



    public GameState getCurrentState() {
        return currentGameState;
    }
}
