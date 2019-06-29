package id.ac.petra.informatika.museum.TreasureHunt;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.Random;

import id.ac.petra.informatika.museum.Information.CaptureActivityPortrait;
import id.ac.petra.informatika.museum.Information.InformasiArtefak;
import id.ac.petra.informatika.museum.Information.information;
import id.ac.petra.informatika.museum.R;
import id.ac.petra.informatika.museum.SaveQuizJawaban;
import id.ac.petra.informatika.museum.SaveQuizSoal;
import id.ac.petra.informatika.museum.Session;

public class GameQuiz extends AppCompatActivity {
    private IntentIntegrator qrScan;
    private TextView question, viewround, penjelasanpoin;
    private String soal, jawaban, username, ruangan, round, endround;
    private int pengurangan=0, score, countsalah=5, scorekurang;
    private SaveQuizSoal soalquiz;
    private SaveQuizJawaban jawabquiz;

    //private ImageView life1,life2,life3,life4,life5;
    private GetQuizSoal databasegame;
    private GetQuizJawaban jawab;
    private ImageButton ubah, butjawab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_quiz);
        ubah = (ImageButton)findViewById(R.id.buttonubahquiz);
        butjawab = (ImageButton)findViewById(R.id.buttonjawabquiz);

        //life1 = (ImageView)findViewById(R.id.lifeq1);
        //life2 = (ImageView)findViewById(R.id.lifeq2);
        //life3 = (ImageView)findViewById(R.id.lifeq3);
        //life4 = (ImageView)findViewById(R.id.lifeq4);
        //life5 = (ImageView)findViewById(R.id.lifeq5);

        question = (TextView) findViewById(R.id.viewQuestion);
        viewround=(TextView)findViewById(R.id.viewroundquiz);
        penjelasanpoin=(TextView)findViewById(R.id.pointquiz);

        Intent intent = getIntent();
        ruangan = intent.getStringExtra("ruangan");
        round = intent.getStringExtra("round");
        endround = intent.getStringExtra("endround");

        String temp = "Round : "+round+"/"+endround;
        viewround.setText(temp);

        if(ruangan.matches("Koleksi Tanah Liat")){
            score = 25000;
            scorekurang=0;
        } else if(ruangan.matches("Koleksi Logam")){
            score = 2500;
            scorekurang=0;
        }else if(ruangan.matches("Koleksi Arca")) {
            score = 50000;
            scorekurang=0;
        }
        penjelasanpoin.setText("Poin maksimal di round ini = "+score);

        databasegame = new GetQuizSoal(this);
        jawab = new GetQuizJawaban(this);

        soalquiz = new SaveQuizSoal(this);
        jawabquiz = new SaveQuizJawaban(this);
        databasegame.execute("word", ruangan);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //NANTI DIGANTI DATABASE
                soal = soalquiz.getSoalQuiz();
                jawab.execute("quiz", soal);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        jawaban = jawabquiz.getJawabanQuiz();
                        question.setText(soal);
                    }
                }, 2500);
            }
        }, 2500);

    }

    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast toast = Toast.makeText(this, "QRCode tidak dikenali", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } else {
                //if qr contains data AND VALID WITH ANSWERE
                String hasil = result.getContents();
                if (hasil.matches(jawaban)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    final Intent pindah = new Intent(GameQuiz.this, InformationGame.class);
                    pindah.putExtra("jawaban", jawaban);
                    //pindah.putExtra("gameid", "1");

                    score = score - (pengurangan * scorekurang);
                    if (score <= 450) {
                        score = 500;
                    }
                    SharedPreferences m_score;
                    m_score = getSharedPreferences("Score", MODE_PRIVATE);

                    SharedPreferences.Editor ScoreEdit = m_score.edit();
                    ScoreEdit.putInt("nilai", score);
                    ScoreEdit.commit();


                    // Setting Dialog Title
                    builder.setTitle("Selamat");
                    // Setting Dialog Message
                    builder.setMessage("Jawaban Anda Benar!");
                    // Add the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked OK button
                            startActivity(pindah);
                            finish();
                        }
                    });
                    // Showing Alert Message
                    builder.show();
                } else {
                    if (countsalah == -10000) {
                        final Intent pindah = new Intent(this, GameManager.class);
                        pindah.putExtra("ruangan", ruangan);
                        pindah.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        butjawab.setVisibility(View.INVISIBLE);

                        AlertDialog.Builder confirm = new AlertDialog.Builder(this);

                        confirm.setTitle("Maaf");
                        confirm.setMessage("Sayang sekali sepertinya nyawa kamu sudah habis di round ini.");
                        confirm.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK button
                                startActivityIfNeeded(pindah,0);
                                finish();
                            }
                        });
                        confirm.show();
                    } else {
                        Toast salah = Toast.makeText(this, "Jawaban Anda Salah!!", Toast.LENGTH_LONG);
                        salah.setGravity(Gravity.CENTER, 0, 0);
                        salah.show();
                        pengurangan += 1;
                        //ganti(countsalah);
                        countsalah--;
                    }
                }
            }
        }
        else{
            Toast toast =Toast.makeText(this, "Dibatalkan", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }

    public void answere(View view){
        qrScan = new IntentIntegrator(this);
        qrScan.setOrientationLocked(true);
        qrScan.setBeepEnabled(false);
        qrScan.setCaptureActivity(CaptureActivityPortrait.class);
        qrScan.initiateScan();
    }
    public void ulang (View view) {
        if (countsalah == -10000) {
            final Intent pindah = new Intent(this, GameManager.class);
            AlertDialog.Builder confirm = new AlertDialog.Builder(this);
            pindah.putExtra("ruangan", ruangan);
            pindah.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            butjawab.setVisibility(View.INVISIBLE);

            confirm.setTitle("Maaf");
            confirm.setMessage("Sayang sekali sepertinya nyawa kamu sudah habis di round ini.");
            confirm.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                    startActivityIfNeeded(pindah,0);
                    finish();
                }
            });
            confirm.show();
        } else {
            ubah.setEnabled(false);
            //ganti(countsalah);
            countsalah--;

            //LOAD PERTANYAAN
            databasegame = new GetQuizSoal(this);
            jawab = new GetQuizJawaban(this);
            databasegame.execute("word", ruangan);
            question.setText("Loading...");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //NANTI DIGANTI DATABASE
                    soal = soalquiz.getSoalQuiz();
                    jawab.execute("quiz", soal);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            jawaban = jawabquiz.getJawabanQuiz();
                            question.setText(soal);
                            if(countsalah>=-10000) {
                                ubah.setEnabled(true);
                            }
                        }
                    }, 2500);
                }
            }, 2500);
        }
    }
//    private void ganti(int salah) {
//        if (salah == 1) {
//            life1.setBackgroundResource(R.drawable.i_dead);
//        } else if (salah == 2) {
//            life2.setBackgroundResource(R.drawable.i_dead);
//        } else if (salah == 3) {
//            life3.setBackgroundResource(R.drawable.i_dead);
//        } else if (salah == 4) {
//            life4.setBackgroundResource(R.drawable.i_dead);
//        } else if (salah == 5) {
//            life5.setBackgroundResource(R.drawable.i_dead);
//        }
//    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        final Intent pindah = new Intent(this, PermainanTreasureHunt.class);
        final Toast toast =Toast.makeText(this, "Dibatalkan!", Toast.LENGTH_LONG);
        AlertDialog.Builder confirm = new AlertDialog.Builder(this);
        confirm.setTitle("Konfirmasi");
        confirm.setMessage("Apakah kamu yakin untuk keluar dari game?");
        confirm.setPositiveButton("YAKIN", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                startActivity(pindah);
                finishAffinity();
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

}
