package com.example.qlcafe.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.qlcafe.DTO.DonDatDTO;
import com.example.qlcafe.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class DonDatDAO {

    SQLiteDatabase database;
    public DonDatDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public long ThemDonDat(DonDatDTO donDatDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_DONDAT_MABAN,donDatDTO.getMaBan());
        contentValues.put(CreateDatabase.TBL_DONDAT_MANV,donDatDTO.getMaNV());
        contentValues.put(CreateDatabase.TBL_DONDAT_NGAYDAT,donDatDTO.getNgayDat());
        contentValues.put(CreateDatabase.TBL_DONDAT_TINHTRANG,donDatDTO.getTinhTrang());
        contentValues.put(CreateDatabase.TBL_DONDAT_TONGTIEN,donDatDTO.getTongTien());

        long madondat = database.insert(CreateDatabase.TBL_DONDAT,null,contentValues);

        return madondat;
    }

    public List<DonDatDTO> LayDSDonDat() {
        List<DonDatDTO> donDatDTOS = new ArrayList<>();
        String query = "SELECT * FROM " + CreateDatabase.TBL_DONDAT;
        Cursor cursor = database.rawQuery(query, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {  // Kiểm tra nếu có dữ liệu
                do {
                    DonDatDTO donDatDTO = new DonDatDTO();

                    int indexMaDonDat = cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MADONDAT);
                    int indexMaBan = cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MABAN);
                    int indexTongTien = cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_TONGTIEN);
                    int indexTinhTrang = cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_TINHTRANG);
                    int indexNgayDat = cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_NGAYDAT);
                    int indexMaNV = cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MANV);

                    if (indexMaDonDat >= 0) donDatDTO.setMaDonDat(cursor.getInt(indexMaDonDat));
                    if (indexMaBan >= 0) donDatDTO.setMaBan(cursor.getInt(indexMaBan));
                    if (indexTongTien >= 0) donDatDTO.setTongTien(cursor.getString(indexTongTien));
                    if (indexTinhTrang >= 0) donDatDTO.setTinhTrang(cursor.getString(indexTinhTrang));
                    if (indexNgayDat >= 0) donDatDTO.setNgayDat(cursor.getString(indexNgayDat));
                    if (indexMaNV >= 0) donDatDTO.setMaNV(cursor.getInt(indexMaNV));

                    donDatDTOS.add(donDatDTO);
                } while (cursor.moveToNext());  // Dùng `do-while` thay vì `while`
            }
            cursor.close();  // Đóng Cursor sau khi sử dụng
        }

        return donDatDTOS;
    }


    public List<DonDatDTO> LayDSDonDatNgay(String ngaythang) {
        List<DonDatDTO> donDatDTOS = new ArrayList<>();
        String query = "SELECT * FROM " + CreateDatabase.TBL_DONDAT +
                " WHERE " + CreateDatabase.TBL_DONDAT_NGAYDAT + " LIKE ?";

        Cursor cursor = database.rawQuery(query, new String[]{"%" + ngaythang + "%"});

        if (cursor != null) {
            if (cursor.moveToFirst()) {  // Kiểm tra nếu có dữ liệu
                do {
                    DonDatDTO donDatDTO = new DonDatDTO();

                    int indexMaDonDat = cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MADONDAT);
                    int indexMaBan = cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MABAN);
                    int indexTongTien = cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_TONGTIEN);
                    int indexTinhTrang = cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_TINHTRANG);
                    int indexNgayDat = cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_NGAYDAT);
                    int indexMaNV = cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MANV);

                    if (indexMaDonDat >= 0) donDatDTO.setMaDonDat(cursor.getInt(indexMaDonDat));
                    if (indexMaBan >= 0) donDatDTO.setMaBan(cursor.getInt(indexMaBan));
                    if (indexTongTien >= 0) donDatDTO.setTongTien(cursor.getString(indexTongTien));
                    if (indexTinhTrang >= 0) donDatDTO.setTinhTrang(cursor.getString(indexTinhTrang));
                    if (indexNgayDat >= 0) donDatDTO.setNgayDat(cursor.getString(indexNgayDat));
                    if (indexMaNV >= 0) donDatDTO.setMaNV(cursor.getInt(indexMaNV));

                    donDatDTOS.add(donDatDTO);
                } while (cursor.moveToNext());  // Dùng `do-while` thay vì `while`
            }
            cursor.close();  // Đóng Cursor sau khi sử dụng
        }

        return donDatDTOS;
    }


    public long LayMaDonTheoMaBan(int maban, String tinhtrang) {
        long magoimon = 0;
        String query = "SELECT " + CreateDatabase.TBL_DONDAT_MADONDAT + " FROM " + CreateDatabase.TBL_DONDAT +
                " WHERE " + CreateDatabase.TBL_DONDAT_MABAN + " = ? AND " + CreateDatabase.TBL_DONDAT_TINHTRANG + " = ?";

        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(maban), tinhtrang});

        if (cursor != null) {
            if (cursor.moveToFirst()) { // Kiểm tra có dữ liệu không
                int indexMaDonDat = cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MADONDAT);
                if (indexMaDonDat >= 0) {
                    magoimon = cursor.getInt(indexMaDonDat);
                }
            }
            cursor.close(); // Đóng Cursor sau khi sử dụng
        }

        return magoimon;
    }


    public boolean UpdateTongTienDonDat(int madondat,String tongtien){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_DONDAT_TONGTIEN,tongtien);
        long ktra  = database.update(CreateDatabase.TBL_DONDAT,contentValues,
                CreateDatabase.TBL_DONDAT_MADONDAT+" = "+madondat,null);
        if(ktra != 0){
            return true;
        }else{
            return false;
        }
    }

    public boolean UpdateTThaiDonTheoMaBan(int maban,String tinhtrang){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_DONDAT_TINHTRANG,tinhtrang);
        long ktra = database.update(CreateDatabase.TBL_DONDAT,contentValues,CreateDatabase.TBL_DONDAT_MABAN+
                " = '"+maban+"'",null);
        if(ktra !=0){
            return true;
        }else {
            return false;
        }
    }

}
