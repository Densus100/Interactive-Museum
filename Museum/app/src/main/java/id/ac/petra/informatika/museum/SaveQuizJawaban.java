package id.ac.petra.informatika.museum;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveQuizJawaban {

    private SharedPreferences save;

    public SaveQuizJawaban(Context context) {
        save = PreferenceManager.getDefaultSharedPreferences(context);
    }
    public void setJawaban(String _jawaban){
        SharedPreferences.Editor ScoreEdit = save.edit();
        ScoreEdit.putString("jawaban", _jawaban);
        ScoreEdit.commit();
    }

    public String getJawabanQuiz() {
        return save.getString("jawaban","Sinyal Internet Kurang Stabil");
    }

    public void deleteJawabanQuiz() {
        SharedPreferences.Editor ScoreEdit = save.edit();
        ScoreEdit.clear();
        ScoreEdit.commit();
    }
}
