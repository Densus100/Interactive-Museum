package id.ac.petra.informatika.museum.TreasureHunt;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

class DatabaseRegisterParams {
    String name, password, username , password2 , email, phone, alamat, type;
    DatabaseRegisterParams(String type , String name , String username , String password , String password2 , String email , String phone , String alamat){
        this.type = type;
        this.name = name;
        this.username = username;
        this.password = password;
        this.password2 = password2;
        this.email = email;
        this.phone = phone;
        this.alamat = alamat;
    }
}
public class DatabaseRegister extends AsyncTask<DatabaseRegisterParams, Void, String> {
    Context context;
    AlertDialog.Builder alertDialog;

    DatabaseRegister(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(DatabaseRegisterParams... databaseRegisterParams) {
        String type = databaseRegisterParams[0].type;
        String register_url = "http://opensource.petra.ac.id/~m26416093tw/register.php";
        if (type.equals("register")) {
            try {
                String username = databaseRegisterParams[0].username;
                String password = databaseRegisterParams[0].password;
                String password2 = databaseRegisterParams[0].password2;
                String name = databaseRegisterParams[0].name;
                String email = databaseRegisterParams[0].email;
                String phone = databaseRegisterParams[0].phone;
                String alamat = databaseRegisterParams[0].alamat;
                URL url = new URL(register_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data =
                        URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") + "&"
                        + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
                        + URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8") + "&"
                        + URLEncoder.encode("password2", "UTF-8") + "=" + URLEncoder.encode(password2, "UTF-8") + "&"
                        + URLEncoder.encode("usia", "UTF-8") + "=" + URLEncoder.encode(alamat, "UTF-8") + "&"
                        + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                Log.i("test", username);
                Log.i("test", name);
                Log.i("test", email);
                Log.i("test", phone);
                Log.i("test", alamat);
                Log.i("test", password);
                Log.i("test", password2);
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Registrasi Status");
    }

    @Override
    protected void onPostExecute(String result) {
        final Intent pindah = new Intent(this.context,Login.class);

        if(result.matches("Registrasi sukses")){
            alertDialog.setMessage("Registrasi berhasil! Silahkan login untuk mulai bermain.");
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    context.startActivity(pindah);
                }
            });
        }else {
            alertDialog.setMessage(result);
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                }
            });
        }
        alertDialog.show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}



