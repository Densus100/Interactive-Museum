package id.ac.petra.informatika.museum;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveInformation {
    private SharedPreferences save;

    public SaveInformation(Context context) {
        save = PreferenceManager.getDefaultSharedPreferences(context);
    }
    public void set(String _judul, String _penjelasan, String _photopath, String _videourl){
        SharedPreferences.Editor ScoreEdit = save.edit();
        ScoreEdit.putString("judul", _judul);
        ScoreEdit.putString("penjelasan", _penjelasan);
        ScoreEdit.putString("photopath", _photopath);
        ScoreEdit.putString("videourl", _videourl);
        ScoreEdit.commit();
    }

    public String getJudul() {
        return save.getString("judul","NULL");
    }

    public String getPenjelasan() {
        return save.getString("penjelasan","Sinyal Internet Kurang Stabil");
    }

    public String getPhotopath() {
        return save.getString("photopath","NULL");
    }

    public String getVideourl() {
        return save.getString("videourl","NULL");
    }

    public void deleteInformation() {
        SharedPreferences.Editor ScoreEdit = save.edit();
        ScoreEdit.clear();
        ScoreEdit.commit();
    }
}
