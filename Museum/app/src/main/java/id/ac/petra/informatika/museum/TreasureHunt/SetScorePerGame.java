package id.ac.petra.informatika.museum.TreasureHunt;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by User on 01/11/2018.
 */
class param_set_score_per_game {
    String stype, susername, sscore, sdate;
    param_set_score_per_game(String _type, String _username, String _score, String _date) {
        this.stype = _type;
        this.susername = _username;
        this.sscore = _score;
        this.sdate = _date;
    }
}

public class SetScorePerGame extends AsyncTask<param_set_score_per_game,Void,String> {
    Context context;
    SetScorePerGame(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(param_set_score_per_game... param) {
        String type = param[0].stype;
        String info_url = "http://opensource.petra.ac.id/~m26416093tw/set_score.php";
        if(type.equals("score")) {
            try {
                String username = param[0].susername;
                String score = param[0].sscore;
                String date = param[0].sdate;
                URL url = new URL(info_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
                                + URLEncoder.encode("game_score", "UTF-8") + "=" + URLEncoder.encode(score, "UTF-8") + "&"
                                + URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                inputStream.close();
                httpURLConnection.disconnect();
                return null;
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
        cancel(true);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
