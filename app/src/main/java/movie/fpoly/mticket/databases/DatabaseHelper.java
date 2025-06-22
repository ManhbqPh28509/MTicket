package movie.fpoly.mticket.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;

import movie.fpoly.mticket.R;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME ="MTICKET.db";
    private Context mContext;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 1. Tạo các bảng
        String CREATE_TABLE_USERS = "CREATE TABLE USERS (" +
                "user_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT , " +
                "password TEXT NOT NULL, " +
                "email TEXT , " +
                "phone TEXT )";
        db.execSQL(CREATE_TABLE_USERS);

        String CREATE_TABLE_CATEGORIES = "CREATE TABLE CATEGORIES (" +
                "category_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "category_name TEXT NOT NULL)";
        db.execSQL(CREATE_TABLE_CATEGORIES);

        String CREATE_TABLE_CINEMAS = "CREATE TABLE CINEMAS (" +
                "cinema_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "cinema_name TEXT NOT NULL, " +
                "cinema_address TEXT NOT NULL)";
        db.execSQL(CREATE_TABLE_CINEMAS);

        String CREATE_TABLE_ROOM = "CREATE TABLE ROOM (" +
                "room_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "cinema_id INTEGER REFERENCES CINEMAS(cinema_id), " +
                "room_name TEXT NOT NULL)";
        db.execSQL(CREATE_TABLE_ROOM);

        String CREATE_TABLE_MOVIES = "CREATE TABLE MOVIES (" +
                "movie_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "category_id INTEGER NOT NULL REFERENCES CATEGORIES(category_id), " +
                "movie_name TEXT NOT NULL, " +
                "movie_description TEXT NOT NULL, " +
                "movie_trailer TEXT NOT NULL, " +
                "movie_release DATE NOT NULL, " +
                "movie_poster BLOB NOT NULL, " +
                "movie_length TEXT NOT NULL)";
        db.execSQL(CREATE_TABLE_MOVIES);

        String CREATE_TABLE_SCHEDULE = "CREATE TABLE SCHEDULE (" +
                "schedule_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "movie_id INTEGER REFERENCES MOVIES(movie_id), " +
                "room_id INTEGER REFERENCES ROOM(room_id), " +
                "schedule_date DATE NOT NULL)";
        db.execSQL(CREATE_TABLE_SCHEDULE);

        String CREATE_TABLE_SEATS = "CREATE TABLE SEATS (" +
                "seat_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "room_id INTEGER REFERENCES ROOM(room_id), " +
                "row_seat TEXT NOT NULL, " +
                "number INTEGER NOT NULL, " +
                "seat_status INTEGER NOT NULL)";
        db.execSQL(CREATE_TABLE_SEATS);

        String CREATE_TABLE_BOOKING = "CREATE TABLE BOOKING (" +
                "booking_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id INTEGER REFERENCES USERS(user_id), " +
                "schedule_id INTEGER REFERENCES SCHEDULE(schedule_id), " +
                "seat_id INTEGER REFERENCES SEATS(seat_id), " +
                "price DOUBLE NOT NULL)";
        db.execSQL(CREATE_TABLE_BOOKING);
        db.execSQL(
                "INSERT INTO USERS(username, password, email, phone) VALUES " +
                        "('alice', 'alice123', 'alice@example.com', '0123456789')," +
                        "('bob',   'bobpass',  'bob@example.com',   '0987654321')"
        );

        // CATEGORIES
        db.execSQL(
                "INSERT INTO CATEGORIES(category_name) VALUES " +
                        "('Hành động')," +
                        "('Hài')," +
                        "('Kinh dị')"
        );

        // CINEMAS
        db.execSQL(
                "INSERT INTO CINEMAS(cinema_name, cinema_address) VALUES " +
                        "('CGV Vincom',   '72 Lê Thánh Tôn, Q.1, TP.HCM')," +
                        "('Galaxy Nguyễn Du', '116–118 Nguyễn Du, Q.1, TP.HCM')"
        );

        // ROOM
        db.execSQL(
                "INSERT INTO ROOM(cinema_id, room_name) VALUES " +
                        "(1, 'Phòng A')," +
                        "(1, 'Phòng B')," +
                        "(2, 'Phòng 1')"
        );

        // MOVIES (dùng X'' để tạo BLOB rỗng cho demo)
        db.execSQL(
                "INSERT INTO MOVIES(category_id, movie_name, movie_description, movie_trailer, movie_release, movie_poster, movie_length) VALUES " +
                        "(1, 'Fast & Furious', 'Đua xe tốc độ cao', 'https://www.youtube.com/embed/hbQ7Tm25iQ4?si=ZFQ3Rex_dcUfJquY', '2025-06-01', X'', '130 phút')," +
                        "(2, 'Joker',           'Tâm lý tội phạm',       'https://www.youtube.com/embed/zAGVQLHvwOY?si=GZSo8VtTeSBr7BV7', X'', '122 phút')"
        );

        // SCHEDULE
        db.execSQL(
                "INSERT INTO SCHEDULE(movie_id, room_id, schedule_date) VALUES " +
                        "(1, 1, '2025-06-15')," +
                        "(1, 2, '2025-06-16')," +
                        "(2, 3, '2025-05-20')"
        );

        // SEATS
        db.execSQL(
                "INSERT INTO SEATS(room_id, row_seat, number, seat_status) VALUES " +
                        "(1, 'A', 1, 0)," +
                        "(1, 'A', 2, 1)," +
                        "(1, 'B', 1, 0)," +
                        "(2, 'A', 1, 0)"
        );

        // BOOKING
        db.execSQL(
                "INSERT INTO BOOKING(user_id, schedule_id, seat_id, price) VALUES " +
                        "(1, 1, 2, 120.0)," +
                        "(2, 3, 1, 150.0)"
        );

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xoá bảng cũ rồi tạo lại
        db.execSQL("DROP TABLE IF EXISTS USERS");
        db.execSQL("DROP TABLE IF EXISTS CATEGORIES");
        db.execSQL("DROP TABLE IF EXISTS CINEMAS");
        db.execSQL("DROP TABLE IF EXISTS ROOM");
        db.execSQL("DROP TABLE IF EXISTS MOVIES");
        db.execSQL("DROP TABLE IF EXISTS SCHEDULE");
        db.execSQL("DROP TABLE IF EXISTS SEATS");
        db.execSQL("DROP TABLE IF EXISTS BOOKING");
        onCreate(db);
    }
}
