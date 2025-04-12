package com.example.qlcafe.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.qlcafe.DTO.MonDTO;
import com.example.qlcafe.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class MonDAO {
    SQLiteDatabase database;
    public MonDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean ThemMon(MonDTO monDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_MON_MALOAI,monDTO.getMaLoai());
        contentValues.put(CreateDatabase.TBL_MON_TENMON,monDTO.getTenMon());
        contentValues.put(CreateDatabase.TBL_MON_GIATIEN,monDTO.getGiaTien());
        contentValues.put(CreateDatabase.TBL_MON_HINHANH,monDTO.getHinhAnh());
        contentValues.put(CreateDatabase.TBL_MON_TINHTRANG,"true");

        long ktra = database.insert(CreateDatabase.TBL_MON,null,contentValues);

        if(ktra !=0){
            return true;
        }else {
            return false;
        }
    }

    public boolean XoaMon(int mamon){
        long ktra = database.delete(CreateDatabase.TBL_MON,CreateDatabase.TBL_MON_MAMON+ " = " +mamon
                ,null);
        if(ktra !=0 ){
            return true;
        }else {
            return false;
        }
    }

    public boolean SuaMon(MonDTO monDTO,int mamon){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_MON_MALOAI,monDTO.getMaLoai());
        contentValues.put(CreateDatabase.TBL_MON_TENMON,monDTO.getTenMon());
        contentValues.put(CreateDatabase.TBL_MON_GIATIEN,monDTO.getGiaTien());
        contentValues.put(CreateDatabase.TBL_MON_HINHANH,monDTO.getHinhAnh());
        contentValues.put(CreateDatabase.TBL_MON_TINHTRANG,monDTO.getTinhTrang());

        long ktra = database.update(CreateDatabase.TBL_MON,contentValues,
                CreateDatabase.TBL_MON_MAMON+" = "+mamon,null);
        if(ktra !=0){
            return true;
        }else {
            return false;
        }
    }

    public List<MonDTO> LayDSMonTheoLoai(int maloai) {
        List<MonDTO> monDTOList = new ArrayList<>();
        String query = "SELECT * FROM " + CreateDatabase.TBL_MON + " WHERE " + CreateDatabase.TBL_MON_MALOAI + " = ?";

        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(maloai)});

        if (cursor != null) {
            try {
                int indexHinhAnh = cursor.getColumnIndex(CreateDatabase.TBL_MON_HINHANH);
                int indexTenMon = cursor.getColumnIndex(CreateDatabase.TBL_MON_TENMON);
                int indexMaLoai = cursor.getColumnIndex(CreateDatabase.TBL_MON_MALOAI);
                int indexMaMon = cursor.getColumnIndex(CreateDatabase.TBL_MON_MAMON);
                int indexGiaTien = cursor.getColumnIndex(CreateDatabase.TBL_MON_GIATIEN);
                int indexTinhTrang = cursor.getColumnIndex(CreateDatabase.TBL_MON_TINHTRANG);

                while (cursor.moveToNext()) {
                    MonDTO monDTO = new MonDTO();

                    if (indexHinhAnh >= 0) monDTO.setHinhAnh(cursor.getBlob(indexHinhAnh));
                    if (indexTenMon >= 0) monDTO.setTenMon(cursor.getString(indexTenMon));
                    if (indexMaLoai >= 0) monDTO.setMaLoai(cursor.getInt(indexMaLoai));
                    if (indexMaMon >= 0) monDTO.setMaMon(cursor.getInt(indexMaMon));
                    if (indexGiaTien >= 0) monDTO.setGiaTien(cursor.getString(indexGiaTien));
                    if (indexTinhTrang >= 0) monDTO.setTinhTrang(cursor.getString(indexTinhTrang));

                    monDTOList.add(monDTO);
                }
            } finally {
                cursor.close(); // Đóng Cursor để tránh rò rỉ bộ nhớ
            }
        }

        return monDTOList;
    }


    public MonDTO LayMonTheoMa(int mamon) {
        MonDTO monDTO = null;
        String query = "SELECT * FROM " + CreateDatabase.TBL_MON + " WHERE " + CreateDatabase.TBL_MON_MAMON + " = ?";

        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(mamon)});

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) { // Kiểm tra có dữ liệu không
                    monDTO = new MonDTO();

                    int indexHinhAnh = cursor.getColumnIndex(CreateDatabase.TBL_MON_HINHANH);
                    int indexTenMon = cursor.getColumnIndex(CreateDatabase.TBL_MON_TENMON);
                    int indexMaLoai = cursor.getColumnIndex(CreateDatabase.TBL_MON_MALOAI);
                    int indexMaMon = cursor.getColumnIndex(CreateDatabase.TBL_MON_MAMON);
                    int indexGiaTien = cursor.getColumnIndex(CreateDatabase.TBL_MON_GIATIEN);
                    int indexTinhTrang = cursor.getColumnIndex(CreateDatabase.TBL_MON_TINHTRANG);

                    if (indexHinhAnh >= 0) monDTO.setHinhAnh(cursor.getBlob(indexHinhAnh));
                    if (indexTenMon >= 0) monDTO.setTenMon(cursor.getString(indexTenMon));
                    if (indexMaLoai >= 0) monDTO.setMaLoai(cursor.getInt(indexMaLoai));
                    if (indexMaMon >= 0) monDTO.setMaMon(cursor.getInt(indexMaMon));
                    if (indexGiaTien >= 0) monDTO.setGiaTien(cursor.getString(indexGiaTien));
                    if (indexTinhTrang >= 0) monDTO.setTinhTrang(cursor.getString(indexTinhTrang));
                }
            } finally {
                cursor.close(); // Đóng Cursor để tránh rò rỉ bộ nhớ
            }
        }

        return monDTO; // Trả về null nếu không tìm thấy
    }


}
