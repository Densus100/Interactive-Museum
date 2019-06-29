package id.ac.petra.informatika.museum.Information;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import id.ac.petra.informatika.museum.R;
import id.ac.petra.informatika.museum.SaveInformation;
import id.ac.petra.informatika.museum.TreasureHunt.PermainanTreasureHunt;

public class information extends AppCompatActivity {
    private TextView m_nama, m_deskripsi;
    private ImageButton videobut, imagebut;
    private String idgambar;
    private SaveInformation simpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        m_nama = (TextView)findViewById(R.id.viewJudul);
        m_deskripsi = (TextView)findViewById(R.id.viewDeskripsi);
        videobut = (ImageButton)findViewById(R.id.button2);
        imagebut = (ImageButton)findViewById(R.id.button4);


        simpan = new id.ac.petra.informatika.museum.SaveInformation(this);

        idgambar = "http://opensource.petra.ac.id/~m26416122/manpro/foto2/" + simpan.getPhotopath()+".jpg";
        m_nama.setText(simpan.getJudul());
        m_deskripsi.setText(simpan.getPenjelasan());

        if(simpan.getVideourl().matches("null") || simpan.getVideourl().matches("NULL")){
            videobut.setVisibility(View.INVISIBLE);
        }
        if(simpan.getPhotopath().matches("NULL") || simpan.getPhotopath().matches("null")) {
            imagebut.setVisibility(View.INVISIBLE);
        }

        //Uri uri = Uri.parse(idgambar);
        //m_gambar.setImageURI(uri);
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
        super.onBackPressed();
        finish();
    }
}
