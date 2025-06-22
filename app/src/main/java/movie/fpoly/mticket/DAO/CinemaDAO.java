package movie.fpoly.mticket.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import movie.fpoly.mticket.databases.DatabaseHelper;
import movie.fpoly.mticket.models.Cinemas;

public class CinemaDAO {
    private SQLiteDatabase db;

    public CinemaDAO(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        this.db = dbHelper.getReadableDatabase();
        this.db = dbHelper.getWritableDatabase();
    }

    public ArrayList<Cinemas> getCinemas(String sql) {
        ArrayList<Cinemas> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Cinemas cinemas = new Cinemas();
                cinemas.setCinema_id(cursor.getInt(0));
                cinemas.setCinema_name(cursor.getString(1));
                cinemas.setCinema_address(cursor.getString(2));
                list.add(cinemas);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public long insertCinema(Cinemas cinemas) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("cinema_name", cinemas.getCinema_name());
        contentValues.put("cinema_address", cinemas.getCinema_address());
        return db.insert("CINEMAS", null, contentValues);
    }

    public long updateCinemas(Cinemas cinemas) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("cinema_name", cinemas.getCinema_name());
        contentValues.put("cinema_address", cinemas.getCinema_address());
        return db.update("CINEMAS", contentValues, "cinema_id = ?", new String[]{String.valueOf(cinemas.getCinema_id())});
    }

    public long deleteCinemas(Cinemas cinemas) {
        return db.delete("CINEMAS", "cinema_id = ?", new String[]{String.valueOf(cinemas.getCinema_id())});
    }
}
