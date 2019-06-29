package id.ac.petra.informatika.museum;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import id.ac.petra.informatika.museum.Information.InformasiArtefak;
import id.ac.petra.informatika.museum.TreasureHunt.PermainanTreasureHunt;

public class MenuUtama extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_utama);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            final AlertDialog.Builder confirm = new AlertDialog.Builder(this);
            confirm.setTitle("Konfirmasi");
            confirm.setMessage("Apakah kamu yakin untuk keluar dari aplikasi ini?");
            confirm.setCancelable(false);
            confirm.setPositiveButton("KELUAR", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                    MenuUtama.super.onBackPressed();
                }
            });
            confirm.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked BATAL button
                    dialog.cancel();
                }
            });
            confirm.show();

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
        } else if (id == R.id.nav_permainan) {
            Intent pindah = new Intent(this, PermainanTreasureHunt.class);
            startActivity(pindah);
            finish();
        } else if (id == R.id.nav_informasimuseum) {
            Intent pindah = new Intent(this, InformasiMuseum.class);
            startActivity(pindah);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void buttonGame(View view){
        Intent pindah = new Intent(this,PermainanTreasureHunt.class);
        startActivity(pindah);
        finish();
    }
    public void buttonArtefak(View view){
        Intent pindah = new Intent(this,InformasiArtefak.class);
        startActivity(pindah);
    }
    public void buttonMuseum(View view){
        Intent pindah = new Intent(this,InformasiMuseum.class);
        startActivity(pindah);
    }
}
