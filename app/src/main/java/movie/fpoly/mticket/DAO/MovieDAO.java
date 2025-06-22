package movie.fpoly.mticket.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import movie.fpoly.mticket.databases.DatabaseHelper;
import movie.fpoly.mticket.models.Movies;

public class MovieDAO {
    private SQLiteDatabase db;

    public MovieDAO(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        this.db = dbHelper.getReadableDatabase();
        this.db = dbHelper.getWritableDatabase();
    }

    public ArrayList<Movies> getMovie(String sql) {
        ArrayList<Movies> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Movies movies = new Movies();
                movies.setMovie_id(cursor.getInt(0));
                movies.setCategory_id(cursor.getInt(1));
                movies.setMovie_name(cursor.getString(2));
                movies.setMovie_description(cursor.getString(3));
                movies.setMovie_trailer(cursor.getString(4));
                movies.setMovie_release(cursor.getString(5));
                movies.setMovie_poster(cursor.getBlob(6));
                movies.setMovie_length(cursor.getString(7));
                list.add(movies);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public long insertMovies(Movies movies) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("category_id", movies.getCategory_id());
        contentValues.put("movie_name", movies.getMovie_name());
        contentValues.put("movie_description", movies.getMovie_description());
        contentValues.put("movie_trailer", movies.getMovie_trailer());
        contentValues.put("movie_release", movies.getMovie_release());
        contentValues.put("movie_poster", movies.getMovie_poster());
        contentValues.put("movie_length", movies.getMovie_length());
        return db.insert("MOVIES", null, contentValues);
    }

    public long updateMovies(Movies movies) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("category_id", movies.getCategory_id());
        contentValues.put("movie_name", movies.getMovie_name());
        contentValues.put("movie_description", movies.getMovie_description());
        contentValues.put("movie_trailer", movies.getMovie_trailer());
        contentValues.put("movie_release", movies.getMovie_release());
        contentValues.put("movie_poster", movies.getMovie_poster());
        contentValues.put("movie_length", movies.getMovie_length());
        return db.update("MOVIES", contentValues, "movie_id = ?", new String[]{String.valueOf(movies.getMovie_id())});
    }

    public long deleteMovies(Movies movies) {
        return db.delete("MOVIES", "movie_id = ?", new String[]{String.valueOf(movies.getMovie_id())});
    }


}
