package com.example.c3a_game;

import androidx.appcompat.app.AppCompatActivity;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button addBtn;
    List<Player> listPlayer = new ArrayList<Player>();
    int currentRound = 1;
    ListView userListView;
    CustomListAdapter adapter;
    ArrayList<Integer> listRemainPlayer = new ArrayList<Integer>();
    Button endPhaseBtn;
    TextView roundTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addBtn = (Button)findViewById(R.id.addBtn);
        endPhaseBtn = (Button)findViewById(R.id.endPhaseBtn);
        roundTV = (TextView) findViewById(R.id.roundTextView);
        adapter = new CustomListAdapter(this, listPlayer);
        roundTV.setText("Round 1");
        userListView = (ListView)findViewById(R.id.userListview);
        userListView.setAdapter(adapter);

        endPhaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEndRound()){
                    int hostScore = scoreForHost();
//                    for (int i=0; i<listPlayer.size();i++){
//                        Player player = listPlayer.get(i);
//                        if (player.getStatus() == 1) {
//                            player.updateScore(-hostScore);
//                            break;
//                        }
//                    }
                    updateScore(hostScore);
                    resetRound();
                }
                else{
                    Toast.makeText(MainActivity.this,
                            "Please complete before end round!", Toast.LENGTH_LONG).show();
                }
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Player player = new Player("");
                listPlayer.add(player);
//                if (listPlayer.size() > 1) {
//                    resetRound();
//                }
                adapter.notifyDataSetChanged();
            }
        });

    }

    public int scoreForHost(){
        int total = 0;
        for (int i=0; i<this.listPlayer.size(); i++){
            Player  player = this.listPlayer.get(i);

            switch (player.getStatus()){
                case 1:
                    break;
                case 2:
                    total += 1;
                    break;
                case 3:
                    total -= 1;
                    break;
                case 4:
                    break;
            }
        }
        return total;
    }

    public void updateScore(int hostScore){
        for (int i=0; i<this.listPlayer.size();i++){
            Player player = this.listPlayer.get(i);
            switch (player.getStatus()){
                case 1:
                    player.updateScore(-hostScore);
                    break;
                case 2:
                    player.updateScore(1);
                    break;
                case 3:
                    player.updateScore(-1);
                    break;
                case 4:
                    player.updateScore(0);
                    break;
            }
            player.holder.scoreView.setText(String.valueOf(player.getScore()));
        }
    }

    public boolean checkEndRound(){
        boolean flag = true;
        for(int i=0; i<listPlayer.size(); i++){
            Player player = listPlayer.get(i);
            Log.i("Check end round: " + String.valueOf(i), String.valueOf(player.getStatus()));
            if (player.getStatus() == 0){
                flag = false;
                break;
            }
        }

        return flag;
    }

    public void resetRound(){
        for(int i=0; i<listPlayer.size(); i++){
            Player player = listPlayer.get(i);
            if (player.holder == null){
                continue;
            }
//            View v = adapter.getView(i, null, null);

//            RadioGroup scoreRadioGroup = (RadioGroup) v.findViewById(R.id.scoreRadio);
//            scoreRadioGroup.setVisibility(View.VISIBLE);
            RadioGroup scoreRadioGroup = player.holder.scoreRadioGroup;
            EditText t = player.holder.playerNameView;
//            Log.i("FLy---------" + String.valueOf(i), String.valueOf(scoreRadioGroup.getCheckedRadioButtonId()));
//            Log.i("FLy---------" + String.valueOf(i) + t.getText(), player.getName());
            scoreRadioGroup.clearCheck();
            if (player.getStatus() != 1){
                player.setStatus(0);
            }

        }

        currentRound += 1;
        roundTV.setText("Round " + String.valueOf(currentRound));

    }
}