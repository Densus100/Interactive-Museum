package id.ac.petra.informatika.museum.Information;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

import id.ac.petra.informatika.museum.SaveInformation;
import id.ac.petra.informatika.museum.Session;
import id.ac.petra.informatika.museum.TreasureHunt.PermainanTreasureHunt;

/**
 * Created by User on 31/10/2018.
 */

public class DatabaseInformation extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog.Builder alertDialog;
    SaveInformation simpan;
    String json_string;

    DatabaseInformation(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String info_url = "http://opensource.petra.ac.id/~m26416093tw/info_museum.php";
        if(type.equals("info")) {
            try {
                String qr_code = params[1];
                URL url = new URL(info_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("qr_code","UTF-8")+"="+URLEncoder.encode(qr_code,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                StringBuilder sBuilder = new StringBuilder();
                String line = "";
                String result="";
                while ((line = bufferedReader.readLine()) != null) {
                    sBuilder.append(line + "\n");
                }
               // inputStream.close();
                result = sBuilder.toString();
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
    }

    @Override
    protected void onPostExecute(String result) {
        json_string = result;
        ParseJson();
    }

    public void ParseJson() {
        simpan = new id.ac.petra.informatika.museum.SaveInformation(this.context);
        simpan.deleteInformation();
        try {
            JSONObject jsonObject = new JSONObject(json_string);
            JSONArray jsonArray = jsonObject.getJSONArray("jawaban");
            //for(int i=0; i < jsonArray.length(); i++) {
                JSONObject jObject = jsonArray.getJSONObject(0);
                simpan.set(jObject.getString("info_judul"),
                        jObject.getString("info_penjelasan"),
                        jObject.getString("info_photopath"),
                        jObject.getString("info_videourl"));
            //} // End Loop

        } catch (JSONException e) {
            Log.e("JSONException", "Error: " + e.toString());
            simpan.set("gagal","gagal","gagal","gagal");
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
