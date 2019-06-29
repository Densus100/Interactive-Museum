package id.ac.petra.informatika.museum.TreasureHunt;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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

import java.text.SimpleDateFormat;

import id.ac.petra.informatika.museum.InformasiMuseum;
import id.ac.petra.informatika.museum.Information.DatabaseInformation;
import id.ac.petra.informatika.museum.Information.Image;
import id.ac.petra.informatika.museum.Information.InformasiArtefak;
import id.ac.petra.informatika.museum.Information.Video;
import id.ac.petra.informatika.museum.Information.information;
import id.ac.petra.informatika.museum.R;
import id.ac.petra.informatika.museum.SaveInformation;
import id.ac.petra.informatika.museum.Session;


public class InformationGame extends AppCompatActivity {
    private TextView m_nama, m_deskripsi;
    private ImageButton videobut, imagebut;
    private String idgambar;
    private SaveInformation simpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_game);
        m_nama = (TextView) findViewById(R.id.viewJudul);
        m_deskripsi = (TextView) findViewById(R.id.viewDeskripsi);
        videobut = (ImageButton) findViewById(R.id.button2);
        imagebut = (ImageButton) findViewById(R.id.button4);

        m_nama.setText("");
        m_deskripsi.setText("Loading...");

        Intent intent = getIntent();
        String jawaban = intent.getStringExtra("jawaban");
        //String score = intent.getStringExtra("poin");
        //String gameid = intent.getStringExtra("gameid");
        id.ac.petra.informatika.museum.TreasureHunt.DatabaseInformation datainfo = new id.ac.petra.informatika.museum.TreasureHunt.DatabaseInformation(this);
        datainfo.execute("info", jawaban);
        simpan = new id.ac.petra.informatika.museum.SaveInformation(this);
        //param_set_score param = new id.ac.petra.informatika.museum.TreasureHunt.param_set_score("score", gameid, username, score, date);
        //SetScore set = new id.ac.petra.informatika.museum.TreasureHunt.SetScore(this);
        //set.execute(param);
        //Log.e("",game.getUsername());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                idgambar = "http://opensource.petra.ac.id/~m26416122/manpro/foto2/" + simpan.getPhotopath() + ".jpg";
                m_nama.setText(simpan.getJudul());
                m_deskripsi.setText(simpan.getPenjelasan());
                if (simpan.getVideourl().matches("null")||simpan.getVideourl().matches("NULL")) {
                    videobut.setVisibility(View.INVISIBLE);
                }
                if (simpan.getPhotopath().matches("NULL")||simpan.getPhotopath().matches("null")) {
                    imagebut.setVisibility(View.INVISIBLE);
                    //Uri uri = Uri.parse(idgambar);
                    //m_gambar.setImageURI(uri);
                }
            }
        }, 3000);
    }
    public void ok(View view) {
        Intent pindah = new Intent(this,GameManager.class);
        pindah.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(pindah,0);
        finish();
    }
    public void videoplay(View view) {
        Intent pindah = new Intent(this,Video.class);
        pindah.putExtra("videourl", simpan.getVideourl());
        startActivity(pindah);
    }
    public void gambarplay(View view) {
        Intent pindah = new Intent(this, Image.class);
        pindah.putExtra("picturl",idgambar);
        startActivity(pindah);
    }


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

