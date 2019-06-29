package id.ac.petra.informatika.museum.TreasureHunt;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import id.ac.petra.informatika.museum.InformasiMuseum;
import id.ac.petra.informatika.museum.Information.InformasiArtefak;
import id.ac.petra.informatika.museum.MenuUtama;
import id.ac.petra.informatika.museum.R;
import id.ac.petra.informatika.museum.Session;
import id.ac.petra.informatika.museum.TotalScore;

public class PermainanTreasureHunt extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Session sesi;
    private TotalScore total;
    private TextView viewusername, viewganti, viewpoin;
    public static String nama, poin;
    public static Boolean cek;
    public Boolean status=false, run = false;
    public ImageView uang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permainan_treasure_hunt);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        uang = (ImageView)findViewById(R.id.imageView6);
        sesi = new id.ac.petra.informatika.museum.Session(this);
        if(!sesi.getCek()){
            status = false;
        }else if(sesi.getCek()){
            nama = sesi.getUsername();
            cek = sesi.getCek();
            total = new id.ac.petra.informatika.museum.TotalScore(this);
            poin = total.getTotalScore();
            status = true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        viewusername = (TextView) findViewById(R.id.viewUsername);
        viewganti = (TextView) findViewById(R.id.viewGanti);
        viewpoin = (TextView)findViewById(R.id.viewScore);
        if (!status) {
            viewusername.setVisibility(View.INVISIBLE);
            viewganti.setVisibility(View.INVISIBLE);
            viewpoin.setVisibility(View.INVISIBLE);
            uang.setVisibility(View.INVISIBLE);
        } else if (status) {
            viewusername.setText("Hi, "+nama);
            viewusername.setVisibility(View.VISIBLE);
            viewganti.setText(Html.fromHtml("<u><b><i> Ganti Akun? </i></b></u>"));
            viewganti.setVisibility(View.VISIBLE);
            viewpoin.setText(poin);
            viewpoin.setVisibility(View.VISIBLE);
            uang.setVisibility(View.VISIBLE);

        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer
                = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            Intent pindah = new Intent(this, MenuUtama.class);
            startActivity(pindah);
            finish();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_informasiartefak) {
            Intent pindah = new Intent(this, InformasiArtefak.class);
            startActivity(pindah);
            finish();
        } else if (id == R.id.nav_permainan) {

        } else if (id == R.id.nav_informasimuseum) {
            Intent pindah = new Intent(this, InformasiMuseum.class);
            startActivity(pindah);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void buttonok(View view) {
        if (!status) {
            Intent pindah = new Intent(this, Login.class);
            startActivity(pindah);
            finish();
        } else if (status) {
            Intent pindah = new Intent(this, Ruangan.class);
            run = false;
            startActivity(pindah);
            //onPause();
        }
    }
    public void ganti(View view){
        final Intent ganti = new Intent(this, Login.class);
        final Toast toast =Toast.makeText(this, "Dibatalkan!", Toast.LENGTH_LONG);
        AlertDialog.Builder confirm = new AlertDialog.Builder(this);
        confirm.setTitle("Konfirmasi");
        confirm.setMessage("Apakah kamu yakin untuk mengganti akun? Hal ini akan membuat kamu perlu login kembali.");
        confirm.setPositiveButton("YAKIN", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                sesi.deleteSession();
                startActivity(ganti);
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

    @Override
    protected void onResume() {
        super.onResume();
        if (!status) {
            viewusername.setVisibility(View.INVISIBLE);
            viewganti.setVisibility(View.INVISIBLE);
            viewpoin.setVisibility(View.INVISIBLE);
            uang.setVisibility(View.INVISIBLE);
        } else if (status) {
            viewusername.setText("Hi, "+nama);
            viewusername.setVisibility(View.VISIBLE);
            viewpoin.setText("Loading...");
            viewpoin.setVisibility(View.VISIBLE);
            uang.setVisibility(View.VISIBLE);
            viewganti.setText(Html.fromHtml("<u><b><i> Ganti Akun? </i></b></u>"));
            viewganti.setVisibility(View.VISIBLE);
            if(!run) {
                final Context ctx = this;
                GetScore score = new GetScore(this);
                score.execute("totalscore", nama);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        total = new id.ac.petra.informatika.museum.TotalScore(ctx);
                        poin = total.getTotalScore();
                        viewpoin.setText(poin);
                    }
                }, 3000);
                run = true;
            }
        }
    }
}