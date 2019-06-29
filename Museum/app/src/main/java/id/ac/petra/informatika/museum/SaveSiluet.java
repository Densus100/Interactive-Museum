package id.ac.petra.informatika.museum;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSiluet {

   private SharedPreferences save;

    public SaveSiluet(Context context) {
        save = PreferenceManager.getDefaultSharedPreferences(context);
    }
    public void setSiluet(String _clue, String _result, String _photosiluet){
        android.content.SharedPreferences.Editor ScoreEdit = save.edit();
        ScoreEdit.putString("clue", _clue);
        ScoreEdit.putString("result", _result);
        ScoreEdit.putString("photosiluet", _photosiluet);
        ScoreEdit.commit();
    }

    public String getClueSiluet() {
        return save.getString("clue","Sinyal Internet Kurang Stabil");
    }

    public String getResultSiluet() {
        return save.getString("result","ADA KESALAHAN");
    }

    public String getPhotoSiluet() {
        return save.getString("photosiluet","NULL");
    }

    public void deleteSiluet() {
        SharedPreferences.Editor ScoreEdit = save.edit();
        ScoreEdit.clear();
        ScoreEdit.commit();
    }
}
