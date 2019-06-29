package id.ac.petra.informatika.museum;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveGameInformation {
    private SharedPreferences save;

    public SaveGameInformation(Context context) {
        save = PreferenceManager.getDefaultSharedPreferences(context);
    }
    public void set(String _game_clue, String _info_qrcode, String _info_judul){
        SharedPreferences.Editor ScoreEdit = save.edit();
        ScoreEdit.putString("game", _game_clue);
        ScoreEdit.putString("infoqrcode", _info_qrcode);
        ScoreEdit.commit();
    }

    public String getGameclue() {
        return save.getString("game","NULL");
    }

    public String getInfoqrcode() {
        return save.getString("infoqrcode","NULL");
    }

    public void deleteGame() {
        SharedPreferences.Editor ScoreEdit = save.edit();
        ScoreEdit.clear();
        ScoreEdit.commit();
    }
}
