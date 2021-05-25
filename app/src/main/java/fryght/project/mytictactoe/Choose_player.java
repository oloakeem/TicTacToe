package fryght.project.mytictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Choose_player extends AppCompatActivity {
    EditText player1, player2;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_player);
        player1 = (EditText) findViewById(R.id.editplayer1);
        player2 = (EditText) findViewById(R.id.editplayer2);
        button = (Button) findViewById(R.id.playButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkNames();
            }
        });

    }
    private void checkNames(){
        String [] bothNames = new String[2];
        String user1 = player1.getText().toString();
        String user2 = player2.getText().toString();

        if (!user1.isEmpty() && !user2.isEmpty()){
            bothNames[0] = user1;
            bothNames[1] = user2;
            Intent intent = new Intent(Choose_player.this,TicTacToe_Board.class);
            intent.putExtra("PlayerTurn", bothNames); //Optional parameters
            startActivity(intent);


        }
    }
}