package id.ac.petra.informatika.museum;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveQuizSoal {
    private SharedPreferences save;

    public SaveQuizSoal(Context context) {
        save = PreferenceManager.getDefaultSharedPreferences(context);
    }
    public void setQuiz(String _clue){
        SharedPreferences.Editor ScoreEdit = save.edit();
        ScoreEdit.putString("clue", _clue);
        ScoreEdit.commit();
    }

    public String getSoalQuiz() {
        return save.getString("clue","Sinyal Internet Kurang Stabil");
    }

    public void deleteSoalQuiz() {
        SharedPreferences.Editor ScoreEdit = save.edit();
        ScoreEdit.clear();
        ScoreEdit.commit();
    }
}
