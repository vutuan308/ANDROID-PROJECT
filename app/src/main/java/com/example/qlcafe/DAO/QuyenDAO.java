package com.example.qlcafe.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.qlcafe.Database.CreateDatabase;

public class QuyenDAO {

    SQLiteDatabase database;
    public QuyenDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public void ThemQuyen(String tenquyen){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_QUYEN_TENQUYEN,tenquyen);
        database.insert(CreateDatabase.TBL_QUYEN,null,contentValues);
    }


    public String LayTenQuyenTheoMa(int maquyen) {
        String tenquyen = "";
        String query = "SELECT " + CreateDatabase.TBL_QUYEN_TENQUYEN +
                " FROM " + CreateDatabase.TBL_QUYEN +
                " WHERE " + CreateDatabase.TBL_QUYEN_MAQUYEN + " = ?";

        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(maquyen)});

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    tenquyen = cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_QUYEN_TENQUYEN));
                }
            } finally {
                cursor.close(); // Đảm bảo đóng Cursor để tránh rò rỉ bộ nhớ
            }
        }

        return tenquyen;
    }

}
