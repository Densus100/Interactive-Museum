package id.ac.petra.informatika.museum.TreasureHunt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.Collections;
import id.ac.petra.informatika.museum.R;
import id.ac.petra.informatika.museum.Session;

public class GameManager extends AppCompatActivity {

    private ArrayList<Integer> m_urutangame = new ArrayList<Integer>();
    private int manager, status=0;
    public int count=4, score=0, round=1, endround;
    private String ruangan, username;
    private Session sesi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        ruangan = intent.getStringExtra("ruangan");
        sesi = new Session(this);
        if(status==0){
            username = sesi.getUsername();
            status=1;
        }
        for(int i=0,j=1;i<count+1;i++,j++) {
            if(j==3){
                j=1;
            }
            m_urutangame.add(j);
        }
        endround=count+1;
        Collections.shuffle(m_urutangame);
        Collections.shuffle(m_urutangame);


        SharedPreferences m_score;
        m_score = getSharedPreferences("Score",MODE_PRIVATE);

        SharedPreferences.Editor ScoreEdit = m_score.edit();
        ScoreEdit.clear();
        ScoreEdit.commit();


    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences m_score;
        m_score = getSharedPreferences("Score",MODE_PRIVATE);
        score = score + m_score.getInt("nilai",0);

        SharedPreferences.Editor ScoreEdit = m_score.edit();
        ScoreEdit.clear();
        ScoreEdit.commit();

        if (count==-1) {
            Intent pindah = new Intent(this, GameEnd.class);
            pindah.putExtra("totalscore", score);
            pindah.putExtra("username", username);
            startActivity(pindah);
            finish();
        } else {
            manager = m_urutangame.get(count);
            count--;
            if (manager == 1) {        //QUIZ
                Intent pindah = new Intent(this, GameQuiz.class);
                pindah.putExtra("ruangan", ruangan);
                pindah.putExtra("round", Integer.toString(round));
                pindah.putExtra("endround",Integer.toString(endround));
                startActivity(pindah);
            } else if (manager == 2) {  //SILUET
                Intent pindah = new Intent(this, GameSiluet.class);
                pindah.putExtra("ruangan", ruangan);
                pindah.putExtra("round", Integer.toString(round));
                pindah.putExtra("endround",Integer.toString(endround));
                startActivity(pindah);
            }
            round++;
        }
    }

}
