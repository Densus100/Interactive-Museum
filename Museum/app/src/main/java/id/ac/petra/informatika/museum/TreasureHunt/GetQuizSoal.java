package id.ac.petra.informatika.museum.TreasureHunt;

import android.app.AlertDialog;
import android.content.Context;
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
import java.util.ArrayList;

import id.ac.petra.informatika.museum.SaveQuizSoal;
import id.ac.petra.informatika.museum.Session;

/**
 * Created by User on 02/11/2018.
 */

public class GetQuizSoal extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog.Builder alertDialog;
    private ArrayList<String> wordList;
    public static String soal = "";
    private SaveQuizSoal save;

    public GetQuizSoal(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String game_url = "http://opensource.petra.ac.id/~m26416093tw/quiz_clue.php";
        if (type.equals("word")) {
            try {
                String ruangan = params[1];
                URL url = new URL(game_url);
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
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    soal = line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return soal;
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
        save = new SaveQuizSoal(this.context);
    }

    @Override
    protected void onPostExecute(String result) {
        save.setQuiz(result);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
