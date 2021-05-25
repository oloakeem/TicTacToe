package fryght.project.mytictactoe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

class TTTBoard extends View {
    private final int boardColor;
    private final int XColor;
    private final int OColor;
    private final int winningLineColor;

    private boolean winningLine = false;

    private final Paint paint = new Paint();
    private int cellSize = getWidth()/3;
    private final GameLogic game;
    public TTTBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.TTTBoard,0,0);
        game = new GameLogic();
        try {
            boardColor = a.getInteger(R.styleable.TTTBoard_boardColor,0);
            XColor = a.getInteger(R.styleable.TTTBoard_XColor,0);
            OColor = a.getInteger(R.styleable.TTTBoard_OColor,0);
            winningLineColor = a.getInteger(R.styleable.TTTBoard_winningLineColor,0);

        }finally {
            a.recycle();

        }

    }
    @Override
    protected void onMeasure(int width, int height){
        super.onMeasure(width,height);

        int dimension = Math.min(getMeasuredWidth(),getMeasuredHeight());
        cellSize = dimension/3;

        setMeasuredDimension(dimension,dimension);

    }
    @Override
    protected void  onDraw(Canvas canvas){
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        drawGameBoard(canvas);
        drawMarkers(canvas);

        if(winningLine){
            paint.setColor(winningLineColor);
            drawWinningLine(canvas);
        }
    }

    private void drawMarkers(Canvas canvas) {
        for(int r= 0; r<3; r++){
            for(int c = 0; c <3; c++){
                if(game.getGameBoard()[r][c] != 0){
                    if(game.getGameBoard()[r][c] ==1){
                        drawX(canvas, r, c);

                    }else {
                        drawO(canvas, r, c);
                    }
                }
            }
        }
    }

    @SuppressLint("ClickableView")
    @Override
    public boolean onTouchEvent(MotionEvent event){
         float x = event.getX();
         float y = event.getY();

         int action = event.getAction();

         if (action == MotionEvent.ACTION_DOWN){
             int row = (int) Math.ceil(y/cellSize);
             int col = (int) Math.ceil(x/cellSize);

             if(!winningLine) {
                 if (game.updateGameBoard(row, col)) {
                     invalidate();
                     if(game.winnerCheck()){
                         winningLine = true;
                         invalidate();
                     }
                     //Updating players turn
                     if (game.getPlayer() % 2 == 0) {
                         game.setPlayer(game.getPlayer() - 1);
                     } else {
                         game.setPlayer(game.getPlayer() + 1);
                     }
                 }
             }

             invalidate();
             return true;
         }
   return false;
    }

    private void drawGameBoard(Canvas canvas) {
        paint.setColor(boardColor);
        paint.setStrokeWidth(16);
        for (int x = 1; x < 3; x++){
            canvas.drawLine(cellSize*x,0,cellSize*x, canvas.getWidth(),paint);
        }
        for (int y = 1; y < 3; y++){
            canvas.drawLine(0,cellSize*y,canvas.getWidth(),cellSize*y,paint);

        }
    }
    private void drawX(Canvas canvas, int row, int col){
        paint.setColor(XColor);

        canvas.drawLine((float) ((col+1)*cellSize - cellSize* .2),
                        (float)(row*cellSize + cellSize* .2),
                        (float)(col*cellSize + cellSize *.2),
                        (float)((row+1)*cellSize - cellSize*.2),
                        paint);

        canvas.drawLine((float) (col*cellSize + cellSize *.2),
                        (float) (row*cellSize + cellSize *.2),
                        (float) ((col+1)*cellSize - cellSize *.2),
                        (float) ((row+1)*cellSize - cellSize *.2),
                        paint);
    }
    private void drawO(Canvas canvas, int row, int col){
        paint.setColor(OColor);

        canvas.drawOval((float) (col*cellSize + cellSize *.2),
                        (float) (row*cellSize + cellSize *.2),
                        (float) ((col*cellSize + cellSize) - cellSize *.2),
                        (float) ((row*cellSize + cellSize) - cellSize *.2),
                        paint);
    }

    private void drawHorizontalLine(Canvas canvas, int row, int col){
        canvas.drawLine(col, row*cellSize + (float)cellSize/2,
                        cellSize*3, row*cellSize + (float)cellSize/2,
                        paint);
    }
    private void drawVerticalLine(Canvas canvas, int row, int col){
        canvas.drawLine(col * cellSize + (float)cellSize/2,
                        row, col*cellSize+ (float)cellSize/2,
                cellSize*3,paint
                );
    }
    private void drawDiagonalLineNeg(Canvas canvas){
        canvas.drawLine(0,0, cellSize *3, cellSize*3,paint);
    }
    private void drawDiagonalLinePos(Canvas canvas){
        canvas.drawLine(0, cellSize *3, cellSize*3,0,paint);

    }
    private void drawWinningLine(Canvas canvas){
        int row = game.getWinType()[0];
        int col = game.getWinType()[1];

        switch (game.getWinType()[2]){
            case 1:
                drawHorizontalLine( canvas,row,col);
                break;
            case 2:
                drawVerticalLine( canvas,row,col);
                break;
            case 3:
                drawDiagonalLineNeg(canvas);
                break;
            case 4:
                drawDiagonalLinePos(canvas);
                break;
        }
    }
    public void setUpGame(Button playAgain, Button home, TextView playerDisplay, String[] playerName){
        game.setPlayAgainBTN(playAgain);
        game.setHomeBTN(home);
        game.setPlayerTurn(playerDisplay);
        game.setPlayerNames(playerName);
    }

    public void resetGame(){
        game.resetGame();
        winningLine = false;
    }
}
