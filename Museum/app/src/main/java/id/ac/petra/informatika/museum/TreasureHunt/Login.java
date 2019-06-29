package id.ac.petra.informatika.museum.TreasureHunt;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import id.ac.petra.informatika.museum.Information.InformasiArtefak;
import id.ac.petra.informatika.museum.Information.information;
import id.ac.petra.informatika.museum.R;

public class Login extends AppCompatActivity {

    private EditText UsernameEt, PasswordEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        UsernameEt = (EditText)findViewById(R.id.etUserName);
        PasswordEt = (EditText)findViewById(R.id.etPassword);
    }

    public void OnLogin(View view) {
        String username = UsernameEt.getText().toString();
        String password = PasswordEt.getText().toString();
        String type = "login";
        if (UsernameEt.getText().length() == 0) {
            UsernameEt.setError("Harap mengisi bagian ini");
        }
        if (PasswordEt.getText().length() == 0) {
            PasswordEt.setError("Harap mengisi bagian ini");
        }
        if(!UsernameEt.getText().toString().matches("[a-zA-Z0-9._]*")) {
            UsernameEt.setError("Hanya boleh mengandung huruf, angka, _ , dan .");
        }
        if(!PasswordEt.getText().toString().matches("[a-zA-Z0-9._]*")) {
            PasswordEt.setError("Hanya boleh mengandung huruf, angka, _ , dan .");
        }
        if (PasswordEt.getText().length() != 0 &&
                UsernameEt.getText().length() != 0 &&
                UsernameEt.getText().toString().matches("[a-zA-Z0-9._]*") &&
                PasswordEt.getText().toString().matches("[a-zA-Z0-9._]*")){
            DatabaseLogin databaseLogin = new DatabaseLogin(this);
            databaseLogin.execute(type, username, password);
        }
    }

    public void buttonregister(View view) {
        Intent keLayout2 = new Intent(this,Register.class);
        startActivity(keLayout2);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent pindah = new Intent(this,PermainanTreasureHunt.class);
        startActivity(pindah);
        finish();
    }
}