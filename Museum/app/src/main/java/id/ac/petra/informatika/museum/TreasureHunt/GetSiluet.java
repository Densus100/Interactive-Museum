package id.ac.petra.informatika.museum.TreasureHunt;

import android.app.AlertDialog;
import android.content.Context;
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

import id.ac.petra.informatika.museum.SaveSiluet;

/**
 * Created by User on 02/11/2018.
 */

public class GetSiluet extends AsyncTask<String,Void,String> {
    Context context;
    String json_string;
    SaveSiluet simpansiluet;
    String result="";

    GetSiluet(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String siluet_url = "http://opensource.petra.ac.id/~m26416093tw/siluet_clue.php";
        if (type.equals("siluet")) {
            String ruangan = params[1];
            try {
                URL url = new URL(siluet_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("ruangan","UTF-8")+"="+ URLEncoder.encode(ruangan,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                StringBuilder sBuilder = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    sBuilder.append(line + "\n");
                }
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
    protected void onPostExecute(String result_siluet) {
        json_string = result_siluet;
        ParseJson();
    }

    public void ParseJson() {
        simpansiluet = new id.ac.petra.informatika.museum.SaveSiluet(this.context);
        simpansiluet.deleteSiluet();
        try {
            JSONObject jsonObject = new JSONObject(json_string);
            JSONArray jsonArray = jsonObject.getJSONArray("clue");
            //for(int i=0; i < jsonArray.length(); i++) {
            JSONObject jObject = jsonArray.getJSONObject(0);
            simpansiluet.setSiluet(jObject.getString("game_clue"),
                    jObject.getString("info_judul"),
                    jObject.getString("game_siluet"));
        } catch (JSONException e) {
            Log.e("JSONException", "Error: " + e.toString());
            simpansiluet.setSiluet("gagal","gagal","gagal");
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
