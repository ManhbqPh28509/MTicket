package movie.fpoly.mticket.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import movie.fpoly.mticket.databases.DatabaseHelper;
import movie.fpoly.mticket.models.Categories;

public class CategoryDAO {
    private SQLiteDatabase db;

    public CategoryDAO(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        this.db = dbHelper.getReadableDatabase();
        this.db = dbHelper.getWritableDatabase();
    }

    public ArrayList<Categories> getCategories(String sql) {
        ArrayList<Categories> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Categories category = new Categories();
                category.setCategory_id(cursor.getInt(0));
                category.setCategory_name(cursor.getString(1));
                list.add(category);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public long insertCategory(Categories category) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("category_name", category.getCategory_name());
        return db.insert("CATEGORIES", null, contentValues);
    }

    public long updateCategory(Categories category) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("category_name", category.getCategory_name());
        return db.update("CATEGORIES", contentValues, "category_id = ?", new String[]{String.valueOf(category.getCategory_id())});
    }

    public long deleteCategory(Categories category) {
        return db.delete("CATEGORIES", "category_id = ?", new String[]{String.valueOf(category.getCategory_id())});
    }
}
