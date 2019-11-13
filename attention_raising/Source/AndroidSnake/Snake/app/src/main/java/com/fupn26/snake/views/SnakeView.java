package com.fupn26.snake.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.fupn26.snake.enums.TileType;

public class SnakeView extends View {
    private Paint m_Paint = new Paint();
    private TileType snakeViewMap[][];

    public SnakeView(Context context) {
        super(context);
    }

    public SnakeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SnakeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SnakeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setSnakeViewMap(TileType[][] map){
        this.snakeViewMap = map;
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        if (snakeViewMap != null){
            float tileSizeX = canvas.getWidth() / snakeViewMap.length;
            float tileSizeY = canvas.getHeight() / snakeViewMap[0].length;

            float circleSize = Math.min(tileSizeX, tileSizeY) / 2;

            for (int x = 0; x < snakeViewMap.length; ++x){
                for (int y = 0; y < snakeViewMap[x].length; ++y){
//                    if(snakeViewMap[x][y] != null) {
                        switch (snakeViewMap[x][y]) {

                            case Nothing:
                                m_Paint.setColor(Color.WHITE);
                                break;
                            case Wall:
                                m_Paint.setColor(Color.BLACK);
                                break;
                            case SnakeHead:
                                m_Paint.setColor(Color.RED);
                                break;
                            case SnakeTail:
                                m_Paint.setColor(Color.YELLOW);
                                break;
                            case Apple:
                                m_Paint.setColor(Color.GREEN);
                                break;
                        }
//                      }
                    canvas.drawCircle(x * tileSizeX + tileSizeX / 2f + circleSize / 2, y * tileSizeY + tileSizeY / 2f + circleSize / 2, circleSize, m_Paint);
                }
            }
        }
    }
}
