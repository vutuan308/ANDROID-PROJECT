package com.example.qlcafe.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.qlcafe.DTO.ThanhToanDTO;
import com.example.qlcafe.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class ThanhToanDAO {

    SQLiteDatabase database;
    public ThanhToanDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public List<ThanhToanDTO> LayDSMonTheoMaDon(int madondat) {
        List<ThanhToanDTO> thanhToanDTOS = new ArrayList<>();

        String query = "SELECT ctdd." + CreateDatabase.TBL_CHITIETDONDAT_SOLUONG + ", " +
                "mon." + CreateDatabase.TBL_MON_GIATIEN + ", " +
                "mon." + CreateDatabase.TBL_MON_TENMON + ", " +
                "mon." + CreateDatabase.TBL_MON_HINHANH +
                " FROM " + CreateDatabase.TBL_CHITIETDONDAT + " ctdd " +
                "JOIN " + CreateDatabase.TBL_MON + " mon " +
                "ON ctdd." + CreateDatabase.TBL_CHITIETDONDAT_MAMON + " = mon." + CreateDatabase.TBL_MON_MAMON +
                " WHERE ctdd." + CreateDatabase.TBL_CHITIETDONDAT_MADONDAT + " = ?";

        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(madondat)});

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {
                        ThanhToanDTO thanhToanDTO = new ThanhToanDTO();
                        thanhToanDTO.setSoLuong(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_CHITIETDONDAT_SOLUONG)));
                        thanhToanDTO.setGiaTien(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_MON_GIATIEN)));
                        thanhToanDTO.setTenMon(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_MON_TENMON)));
                        thanhToanDTO.setHinhAnh(cursor.getBlob(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_MON_HINHANH)));

                        thanhToanDTOS.add(thanhToanDTO);
                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close(); // Đóng Cursor để tránh rò rỉ bộ nhớ
            }
        }

        return thanhToanDTOS;
    }

}
