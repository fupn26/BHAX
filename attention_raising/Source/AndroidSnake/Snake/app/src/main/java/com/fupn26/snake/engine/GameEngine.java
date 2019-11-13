package com.fupn26.snake.engine;

import com.fupn26.snake.classes.Coordinate;
import com.fupn26.snake.enums.Direction;
import com.fupn26.snake.enums.GameState;
import com.fupn26.snake.enums.TileType;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class GameEngine {
    public static final int GameWidth = 28;
    public static final int  GameHeight = 42;

    private List<Coordinate> walls = new ArrayList<>();
    private List<Coordinate> snake = new ArrayList<>();
    private List<Coordinate> apples = new ArrayList<>();

    private Direction currentDirection = Direction.East;

    private GameState currentGameState = GameState.Running;

    private Random random = new Random();

    private boolean increaseTail = false;

    private Coordinate getSnakeHead(){
        return snake.get(0);
    }

    public GameEngine(){

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
        switch (currentDirection){

            case North:
                UpdateSnake(0, -1);
                break;
            case East:
                UpdateSnake(1,0);
                break;
            case South:
                UpdateSnake(0,1);
                break;
            case West:
                UpdateSnake(-1, 0);
                break;
        }

        for(Coordinate w : walls){
            if(snake.get(0).equals(w)){
                currentGameState = GameState.Lost;
            }
        }

        for (int i = 1; i < snake.size(); ++i) {
            if(getSnakeHead().equals(snake.get(i))){
                currentGameState = GameState.Lost;
                return;
            }
        }

        Coordinate appleToRemove = null;
        for ( Coordinate apple : apples ){
            if(getSnakeHead().equals(apple)){
                appleToRemove = apple;
                increaseTail = true;
            }
        }

        if(appleToRemove != null) {
            apples.remove(appleToRemove);
            AddApples();
        }
    }

    public TileType[][] getMap  (){
        TileType[][] map = new TileType[GameWidth][GameHeight];

        for (int i = 0; i < GameWidth; i++) {
            for (int j = 0; j < GameHeight; j++) {
                map[i][j] = TileType.Nothing;
            }

        }

        for(Coordinate s : snake){
            map[s.getX()][s.getY()] = TileType.SnakeTail;
        }

        map[snake.get(0).getX()][snake.get(0).getY()] = TileType.SnakeHead;


        for (Coordinate a : apples) {
            map[a.getX()][a.getY()] = TileType.Apple;
        }

        for (Coordinate wall: walls) {
            map[wall.getX()][wall.getY()] = TileType.Wall;
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
