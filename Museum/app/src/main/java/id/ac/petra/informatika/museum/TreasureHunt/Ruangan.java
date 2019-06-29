package id.ac.petra.informatika.museum.TreasureHunt;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import id.ac.petra.informatika.museum.R;

public class Ruangan extends AppCompatActivity {
    private RadioGroup group;
    private RadioButton kiri, kanan, depan;
    private String ruangan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruangan);
        group = (RadioGroup) findViewById(R.id.radioGroup);
        kiri = (RadioButton)findViewById(R.id.ruangankiri);
        kanan = (RadioButton)findViewById(R.id.ruangankanan);
        depan = (RadioButton)findViewById(R.id.ruangandepan);
    }

    public void okruang(View view){
        if(kiri.isChecked() || kanan.isChecked() || depan.isChecked()){
            if(kiri.isChecked()){
                ruangan = "Koleksi Tanah Liat";
            }else if(kanan.isChecked()) {
                ruangan = "Koleksi Logam";
            }else if(depan.isChecked()){
                ruangan ="Koleksi Arca";
            }
            Intent pindah = new Intent(this, GameManager.class);
            pindah.putExtra("ruangan", ruangan);
            startActivity(pindah);

        }else {
            Toast salah = Toast.makeText(this, "Pilihlah ruangan sebelum menekan OK", Toast.LENGTH_LONG);
            salah.setGravity(Gravity.CENTER,0,0);
            salah.show();
        }
    }
}
