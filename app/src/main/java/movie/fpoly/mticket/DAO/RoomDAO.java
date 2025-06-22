package movie.fpoly.mticket.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import movie.fpoly.mticket.databases.DatabaseHelper;
import movie.fpoly.mticket.models.Room;

public class RoomDAO {
    private SQLiteDatabase db;

    public RoomDAO(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        this.db = dbHelper.getReadableDatabase();
        this.db = dbHelper.getWritableDatabase();
    }

    public ArrayList<Room> getRoom(String sql) {
        ArrayList<Room> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do{
                Room room = new Room();
                room.setRoom_id(cursor.getInt(0));
                room.setCinema_id(cursor.getInt(1));
                room.setRoom_name(cursor.getString(2));
                list.add(room);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public long insertRoom(Room room) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("cinema_id", room.getCinema_id());
        contentValues.put("room_name", room.getRoom_name());
        return db.insert("ROOM", null, contentValues);
    }

    public long updateRoom(Room room) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("cinema_id", room.getCinema_id());
        contentValues.put("room_name", room.getRoom_name());
        return db.update("ROOM", contentValues, "room_id = ?", new String[]{String.valueOf(room.getRoom_id())});
    }

    public long deleteRoom(int id) {
        return db.delete("ROOM", "room_id = ?", new String[]{String.valueOf(id)});
    }
}
