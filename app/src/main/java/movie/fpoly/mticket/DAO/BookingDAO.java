package movie.fpoly.mticket.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import movie.fpoly.mticket.databases.DatabaseHelper;
import movie.fpoly.mticket.models.Booking;

public class BookingDAO {
    private SQLiteDatabase db;

    public BookingDAO(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        this.db = dbHelper.getReadableDatabase();
        this.db = dbHelper.getWritableDatabase();
    }

    public ArrayList<Booking> getBooking(String sql) {
        ArrayList<Booking> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Booking booking = new Booking();
                booking.setBooking_id(cursor.getInt(0));
                booking.setUser_id(cursor.getInt(1));
                booking.setSchedule_id(cursor.getInt(2));
                booking.setSeat_id(cursor.getInt(3));
                booking.setPrice(cursor.getDouble(4));
                list.add(booking);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public long insertBooking(Booking booking) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_id", booking.getUser_id());
        contentValues.put("schedule_id", booking.getSchedule_id());
        contentValues.put("seat_id", booking.getSeat_id());
        contentValues.put("price", booking.getPrice());
        return db.insert("BOOKING", null, contentValues);
    }

    public long updateBooking(Booking booking) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_id", booking.getUser_id());
        contentValues.put("schedule_id", booking.getSchedule_id());
        contentValues.put("seat_id", booking.getSeat_id());
        contentValues.put("price", booking.getPrice());
        return db.update("BOOKING", contentValues, "booking_id = ?", new String[]{String.valueOf(booking.getBooking_id())});
    }

    public long deleteBooking(Booking booking) {
        return db.delete("BOOKING", "booking_id = ?", new String[]{String.valueOf(booking.getBooking_id())});
    }
}
