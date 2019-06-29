package id.ac.petra.informatika.museum;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class TotalScore {
    private SharedPreferences totalscore;

    public TotalScore(Context context) {
        totalscore = PreferenceManager.getDefaultSharedPreferences(context);
    }
    public void setTotalScore(String _score){
        SharedPreferences.Editor ScoreEdit = totalscore.edit();
        ScoreEdit.putString("totalscore", _score);
        ScoreEdit.commit();
    }
    public String getTotalScore() {
        return totalscore.getString("totalscore","0");
    }
}
