package id.ac.petra.informatika.museum.TreasureHunt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import id.ac.petra.informatika.museum.R;

public class Register extends Activity {
    EditText EtUser, EtName, EtEmail, EtPass, EtPass2 , EtAlamat , EtPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        EtUser = (EditText)findViewById(R.id.EtUsername);
        EtPass = (EditText)findViewById(R.id.EtPassword);
        EtName = (EditText)findViewById(R.id.EtName);
        EtEmail = (EditText)findViewById(R.id.EtEmail);
        EtAlamat = (EditText)findViewById(R.id.EtAlamat);
        EtPhone = (EditText)findViewById(R.id.EtPhone);
        EtPass2 = (EditText)findViewById(R.id.EtPassword2);
    }

    public void OnRegister(View view) {
        String username = EtUser.getText().toString();
        String password = EtPass.getText().toString();
        String name = EtName.getText().toString();
        String email = EtEmail.getText().toString();
        String password2 = EtPass2.getText().toString();
        String alamat = EtAlamat.getText().toString();
        String phone = EtPhone.getText().toString();
        String type = "register";
        if (EtName.getText().length() == 0) {
            EtName.setError("Harap mengisi bagian ini");
        }
        if (EtUser.getText().length() == 0) {
            EtUser.setError("Harap mengisi bagian ini");
        }
        if (EtPass.getText().length() == 0) {
            EtPass.setError("Harap mengisi bagian ini");
        }
        if (EtPass2.getText().length() == 0) {
            EtPass2.setError("Harap mengisi bagian ini");
        }
        if(!EtUser.getText().toString().matches("[a-zA-Z0-9._]*")) {
            EtUser.setError("Hanya boleh mengandung huruf, angka, _ , dan .");
        }
        if(!EtPass.getText().toString().matches("[a-zA-Z0-9._]*")) {
            EtPass.setError("Hanya boleh mengandung huruf, angka, _ , dan .");
        }
        if(!EtPass2.getText().toString().matches("[a-zA-Z0-9._]*")) {
            EtPass2.setError("Hanya boleh mengandung huruf, angka, _ , dan .");
        }
        if (EtName.getText().length() != 0 &&
                EtUser.getText().length() != 0 &&
                EtPass.getText().length() != 0 &&
                EtPass2.getText().length() != 0 &&
                EtUser.getText().toString().matches("[a-zA-Z0-9._]*") &&
                EtPass.getText().toString().matches("[a-zA-Z0-9._]*") &&
                EtPass2.getText().toString().matches("[a-zA-Z0-9._]*")) {
            DatabaseRegisterParams params = new DatabaseRegisterParams(type, name, username, password, password2, email, phone, alamat);
            DatabaseRegister databaseRegister = new DatabaseRegister(this);
            databaseRegister.execute(params);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent pindah = new Intent(this,Login.class);
        startActivity(pindah);
        finish();
    }
}
