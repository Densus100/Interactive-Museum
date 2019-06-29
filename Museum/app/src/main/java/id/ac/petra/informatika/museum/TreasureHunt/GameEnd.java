package id.ac.petra.informatika.museum.TreasureHunt;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;

import id.ac.petra.informatika.museum.R;

public class GameEnd extends AppCompatActivity {
    private TextView hasil;
    private String username;
    private String score, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_end);
        hasil=(TextView)findViewById(R.id.hasilscore);
        Intent intent = getIntent();
        score = Integer.toString(intent.getIntExtra("totalscore",0));

        username = intent.getStringExtra("username");
        hasil.setText(score);
        date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new java.util.Date());

        param_set_score param = new id.ac.petra.informatika.museum.TreasureHunt.param_set_score("score", username, score);
        final SetScore set = new id.ac.petra.informatika.museum.TreasureHunt.SetScore(this);
        set.execute(param);

        param_set_score_per_game param2= new id.ac.petra.informatika.museum.TreasureHunt.param_set_score_per_game("score",username,score,date);
        final SetScorePerGame set2= new id.ac.petra.informatika.museum.TreasureHunt.SetScorePerGame(this);
        set2.execute(param2);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        final Intent pindah = new Intent(this, PermainanTreasureHunt.class);
        final Toast toast =Toast.makeText(this, "Dibatalkan!", Toast.LENGTH_LONG);
        AlertDialog.Builder confirm = new AlertDialog.Builder(this);
        confirm.setTitle("Konfirmasi");
        confirm.setMessage("Apakah kamu yakin untuk keluar dari game? Score kamu akan hilang.");
        confirm.setPositiveButton("YAKIN", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                startActivity(pindah);
                finish();
            }
        });
        confirm.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked BATAL button
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
            }
        });
        confirm.show();
    }

    public void ok(View view) {
        Toast toast = Toast.makeText(this, "Tukarkan poinmu di meja Booth kami dengan hadiah yang menarik!", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
        Intent pindah = new Intent(this, PermainanTreasureHunt.class);
        pindah.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(pindah,0);
        finishAffinity();
    }
}
