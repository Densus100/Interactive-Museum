package id.ac.petra.informatika.museum.Information;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import id.ac.petra.informatika.museum.InformasiMuseum;
import id.ac.petra.informatika.museum.MenuUtama;
import id.ac.petra.informatika.museum.R;
import id.ac.petra.informatika.museum.TreasureHunt.PermainanTreasureHunt;

public class InformasiArtefak extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informasi_artefak);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        qrScan = new IntentIntegrator(this);
        qrScan.setOrientationLocked(true);
        qrScan.setCaptureActivity(CaptureActivityPortrait.class);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_informasiartefak) {

        } else if (id == R.id.nav_permainan) {
            Intent pindah = new Intent(this, PermainanTreasureHunt.class);
            startActivity(pindah);
            finishAffinity();
        } else if (id == R.id.nav_informasimuseum) {
            Intent pindah = new Intent(this, InformasiMuseum.class);
            startActivity(pindah);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
                //if qr contains data
                String hasil = result.getContents();

                String type = "info";
                DatabaseInformation info = new DatabaseInformation(this);
                info.execute(type, hasil);
                Toast toast = Toast.makeText(this, "Loading...", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent pindah = new Intent(InformasiArtefak.this, information.class);
                        startActivity(pindah);
                    }
                }, 3000);
            }
        } else {
            Toast toast = Toast.makeText(this, "Dibatalkan", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    public void scan(View view) {
        qrScan.setBeepEnabled(false);
        qrScan.initiateScan();
    }
}
