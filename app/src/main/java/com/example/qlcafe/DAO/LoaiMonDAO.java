package com.example.qlcafe.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.qlcafe.DTO.LoaiMonDTO;
import com.example.qlcafe.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class LoaiMonDAO {

    SQLiteDatabase database;
    public LoaiMonDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean ThemLoaiMon(LoaiMonDTO loaiMonDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_LOAIMON_TENLOAI,loaiMonDTO.getTenLoai());
        contentValues.put(CreateDatabase.TBL_LOAIMON_HINHANH,loaiMonDTO.getHinhAnh());
        long ktra = database.insert(CreateDatabase.TBL_LOAIMON,null,contentValues);

        if(ktra != 0){
            return true;
        }else {
            return false;
        }
    }

    public boolean XoaLoaiMon(int maloai){
        long ktra = database.delete(CreateDatabase.TBL_LOAIMON,CreateDatabase.TBL_LOAIMON_MALOAI+ " = " +maloai
                ,null);
        if(ktra !=0 ){
            return true;
        }else {
            return false;
        }
    }

    public boolean SuaLoaiMon(LoaiMonDTO loaiMonDTO,int maloai){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_LOAIMON_TENLOAI,loaiMonDTO.getTenLoai());
        contentValues.put(CreateDatabase.TBL_LOAIMON_HINHANH,loaiMonDTO.getHinhAnh());
        long ktra = database.update(CreateDatabase.TBL_LOAIMON,contentValues
                ,CreateDatabase.TBL_LOAIMON_MALOAI+" = "+maloai,null);
        if(ktra != 0){
            return true;
        }else {
            return false;
        }
    }

    public List<LoaiMonDTO> LayDSLoaiMon() {
        List<LoaiMonDTO> loaiMonDTOList = new ArrayList<>();
        String query = "SELECT * FROM " + CreateDatabase.TBL_LOAIMON;
        Cursor cursor = database.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) { // Kiểm tra cursor có dữ liệu không
            do {
                LoaiMonDTO loaiMonDTO = new LoaiMonDTO();
                int indexMaLoai = cursor.getColumnIndex(CreateDatabase.TBL_LOAIMON_MALOAI);
                int indexTenLoai = cursor.getColumnIndex(CreateDatabase.TBL_LOAIMON_TENLOAI);
                int indexHinhAnh = cursor.getColumnIndex(CreateDatabase.TBL_LOAIMON_HINHANH);

                if (indexMaLoai >= 0) loaiMonDTO.setMaLoai(cursor.getInt(indexMaLoai));
                if (indexTenLoai >= 0) loaiMonDTO.setTenLoai(cursor.getString(indexTenLoai));
                if (indexHinhAnh >= 0) loaiMonDTO.setHinhAnh(cursor.getBlob(indexHinhAnh));

                loaiMonDTOList.add(loaiMonDTO);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return loaiMonDTOList;
    }


    public LoaiMonDTO LayLoaiMonTheoMa(int maloai) {
        LoaiMonDTO loaiMonDTO = null;
        String query = "SELECT * FROM " + CreateDatabase.TBL_LOAIMON + " WHERE " + CreateDatabase.TBL_LOAIMON_MALOAI + " = " + maloai;
        Cursor cursor = database.rawQuery(query, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {  // Kiểm tra xem có dữ liệu không
                loaiMonDTO = new LoaiMonDTO();

                int indexMaLoai = cursor.getColumnIndex(CreateDatabase.TBL_LOAIMON_MALOAI);
                int indexTenLoai = cursor.getColumnIndex(CreateDatabase.TBL_LOAIMON_TENLOAI);
                int indexHinhAnh = cursor.getColumnIndex(CreateDatabase.TBL_LOAIMON_HINHANH);

                if (indexMaLoai >= 0) loaiMonDTO.setMaLoai(cursor.getInt(indexMaLoai));
                if (indexTenLoai >= 0) loaiMonDTO.setTenLoai(cursor.getString(indexTenLoai));
                if (indexHinhAnh >= 0) loaiMonDTO.setHinhAnh(cursor.getBlob(indexHinhAnh));
            }
            cursor.close();  // Đóng cursor sau khi sử dụng
        }

        return loaiMonDTO;  // Trả về null nếu không tìm thấy dữ liệu
    }


}
