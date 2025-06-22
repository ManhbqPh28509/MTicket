package movie.fpoly.mticket.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import movie.fpoly.mticket.databases.DatabaseHelper;
import movie.fpoly.mticket.models.Schedule;

public class ScheduleDAO {
    private SQLiteDatabase db;

    public ScheduleDAO(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        this.db = dbHelper.getReadableDatabase();
        this.db = dbHelper.getWritableDatabase();
    }

    public ArrayList<Schedule> getSchedule(String sql) {
        ArrayList<Schedule> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Schedule schedule = new Schedule();
                schedule.setSchedule_id(cursor.getInt(0));
                schedule.setMovie_id(cursor.getInt(1));
                schedule.setRoom_id(cursor.getInt(2));
                schedule.setSchedule_date(cursor.getString(3));
                list.add(schedule);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public long insertSchedule(Schedule schedule) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("movie_id", schedule.getMovie_id());
        contentValues.put("room_id", schedule.getRoom_id());
        contentValues.put("schedule_date", schedule.getSchedule_date());
        return db.insert("schedule", null, contentValues);
    }

    public long updateSchedule(Schedule schedule) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("movie_id", schedule.getMovie_id());
        contentValues.put("room_id", schedule.getRoom_id());
        contentValues.put("schedule_date", schedule.getSchedule_date());
        return db.update("SCHEDULE", contentValues, "schedule_id = ?", new String[]{String.valueOf(schedule.getSchedule_id())});
    }

    public long deleteSchedule(int id) {
        return db.delete("SCHEDULE", "schedule_id = ?", new String[]{String.valueOf(id)});
    }
}
