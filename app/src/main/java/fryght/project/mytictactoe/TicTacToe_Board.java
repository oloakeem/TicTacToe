package fryght.project.mytictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TicTacToe_Board extends AppCompatActivity {

    private TTTBoard tttBoard;
    Button playAgainBTN, homeBTN;
    TextView playerTurn, player1, player2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tic_tac_toe_board);
        tttBoard = findViewById(R.id.TTTBoard2);
        playAgainBTN = findViewById(R.id.playAgainBTN);
        homeBTN = findViewById(R.id.homeBTN);
        playerTurn = findViewById(R.id.playerTURN);
        player1 = findViewById(R.id.displayName1);
        player2 = findViewById(R.id.displayName2);

        String[] playerNames = getIntent().getStringArrayExtra("PlayerTurn");
        player1.setText((playerNames[0]));
        player2.setText((playerNames[1]));

        if (playerNames != null) {
            playerTurn.setText((playerNames[0] + "'s Turn"));
        }

        tttBoard.setUpGame(playAgainBTN, homeBTN, playerTurn, playerNames);
    }
        public void playAgainButton (View view){
            tttBoard.resetGame();
            tttBoard.invalidate();
        }

        public void homeButton (View view){
            Intent intent = new Intent(this, Press_to_Start.class);
            startActivity(intent);
        }
    }
