package id.ac.petra.informatika.museum.TreasureHunt;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

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

import id.ac.petra.informatika.museum.SaveQuizJawaban;

/**
 * Created by User on 02/11/2018.
 */

public class GetQuizJawaban extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog.Builder alertDialog;
    public static String result_quiz = "";
    private SaveQuizJawaban jawabanquiz;

    public GetQuizJawaban(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String siluet_url = "http://opensource.petra.ac.id/~m26416093tw/quiz_jawaban.php";
        if (type.equals("quiz")) {
            try {
                String soal = params[1];
                URL url = new URL(siluet_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("clue_game", "UTF-8") + "=" + URLEncoder.encode(soal, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result_quiz = line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result_quiz;
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
        jawabanquiz = new id.ac.petra.informatika.museum.SaveQuizJawaban(this.context);
    }

    @Override
    protected void onPostExecute(String result) {
        jawabanquiz.setJawaban(result);

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}

