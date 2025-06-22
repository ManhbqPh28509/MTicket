package movie.fpoly.mticket.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import movie.fpoly.mticket.databases.DatabaseHelper;
import movie.fpoly.mticket.models.Users;

public class UserDao {
    private SQLiteDatabase db;

    public UserDao(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        this.db = dbHelper.getReadableDatabase();
        this.db = dbHelper.getWritableDatabase();
    }

    public List<Users> getUsers(String sql) {
        List<Users> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Users user = new Users();
                user.setUser_id(cursor.getInt(0));
                user.setUsername(cursor.getString(1));
                user.setPassword(cursor.getString(2));
                user.setEmail(cursor.getString(3));
                user.setPhone(cursor.getString(4));
                list.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public long insertUser(Users user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user.getUsername());
        contentValues.put("password", user.getPassword());
        contentValues.put("email", user.getEmail());
        contentValues.put("phone", user.getPhone());
        return db.insert("USERS", null, contentValues);
    }

    public long updateUser(Users user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user.getUsername());
        contentValues.put("password", user.getPassword());
        contentValues.put("email", user.getEmail());
        contentValues.put("phone", user.getPhone());
        return db.update("USERS", contentValues, "user_id = ?", new String[]{String.valueOf(user.getUser_id())});
    }

    public long deleteUser(Users user) {
        return db.delete("USERS", "user_id = ?", new String[]{String.valueOf(user.getUser_id())});
    }
}
