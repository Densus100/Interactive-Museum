package id.ac.petra.informatika.museum;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {
    private SharedPreferences session;

    public Session(Context context) {
        session = PreferenceManager.getDefaultSharedPreferences(context);
    }
    public void setUsername(String _username){
        SharedPreferences.Editor ScoreEdit = session.edit();
        ScoreEdit.putString("username", _username);
        ScoreEdit.putBoolean("cek",true);
        ScoreEdit.commit();
    }

    public String getUsername() {
        return session.getString("username","TIDAK ADA");
    }

    public boolean getCek() {
        return session.getBoolean("cek",false);
    }

    public void deleteSession() {
        SharedPreferences.Editor ScoreEdit = session.edit();
        ScoreEdit.clear();
        ScoreEdit.commit();
    }
}
