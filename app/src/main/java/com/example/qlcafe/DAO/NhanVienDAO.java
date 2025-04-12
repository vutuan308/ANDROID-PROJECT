package com.example.qlcafe.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.qlcafe.DTO.NhanVienDTO;
import com.example.qlcafe.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {

    SQLiteDatabase database;
    public NhanVienDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public long ThemNhanVien(NhanVienDTO nhanVienDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_NHANVIEN_HOTENNV,nhanVienDTO.getHOTENNV());
        contentValues.put(CreateDatabase.TBL_NHANVIEN_TENDN,nhanVienDTO.getTENDN());
        contentValues.put(CreateDatabase.TBL_NHANVIEN_MATKHAU,nhanVienDTO.getMATKHAU());
        contentValues.put(CreateDatabase.TBL_NHANVIEN_EMAIL,nhanVienDTO.getEMAIL());
        contentValues.put(CreateDatabase.TBL_NHANVIEN_SDT,nhanVienDTO.getSDT());
        contentValues.put(CreateDatabase.TBL_NHANVIEN_GIOITINH,nhanVienDTO.getGIOITINH());
        contentValues.put(CreateDatabase.TBL_NHANVIEN_NGAYSINH,nhanVienDTO.getNGAYSINH());
        contentValues.put(CreateDatabase.TBL_NHANVIEN_MAQUYEN,nhanVienDTO.getMAQUYEN());

        long ktra = database.insert(CreateDatabase.TBL_NHANVIEN,null,contentValues);
        return ktra;
    }

    public long SuaNhanVien(NhanVienDTO nhanVienDTO,int manv){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_NHANVIEN_HOTENNV,nhanVienDTO.getHOTENNV());
        contentValues.put(CreateDatabase.TBL_NHANVIEN_TENDN,nhanVienDTO.getTENDN());
        contentValues.put(CreateDatabase.TBL_NHANVIEN_MATKHAU,nhanVienDTO.getMATKHAU());
        contentValues.put(CreateDatabase.TBL_NHANVIEN_EMAIL,nhanVienDTO.getEMAIL());
        contentValues.put(CreateDatabase.TBL_NHANVIEN_SDT,nhanVienDTO.getSDT());
        contentValues.put(CreateDatabase.TBL_NHANVIEN_GIOITINH,nhanVienDTO.getGIOITINH());
        contentValues.put(CreateDatabase.TBL_NHANVIEN_NGAYSINH,nhanVienDTO.getNGAYSINH());
        contentValues.put(CreateDatabase.TBL_NHANVIEN_MAQUYEN,nhanVienDTO.getMAQUYEN());

        long ktra = database.update(CreateDatabase.TBL_NHANVIEN,contentValues,
                CreateDatabase.TBL_NHANVIEN_MANV+" = "+manv,null);
        return ktra;
    }

    public long CapNhapMatKhau(NhanVienDTO nhanVienDTO, int manv){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_NHANVIEN_MATKHAU,nhanVienDTO.getMATKHAU());

        long ktra = database.update(CreateDatabase.TBL_NHANVIEN,contentValues,
                CreateDatabase.TBL_NHANVIEN_MANV+" = "+manv,null);
        return ktra;
    }


    public int KiemTraDN(String tenDN, String matKhau) {
        int manv = -1; // Trả về -1 nếu tài khoản không tồn tại
        String query = "SELECT " + CreateDatabase.TBL_NHANVIEN_MANV + " FROM "
                + CreateDatabase.TBL_NHANVIEN + " WHERE "
                + CreateDatabase.TBL_NHANVIEN_TENDN + " = ? AND "
                + CreateDatabase.TBL_NHANVIEN_MATKHAU + " = ?";

        Cursor cursor = database.rawQuery(query, new String[]{tenDN, matKhau});

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) { // Kiểm tra nếu có dữ liệu
                    int indexMaNV = cursor.getColumnIndex(CreateDatabase.TBL_NHANVIEN_MANV);
                    if (indexMaNV >= 0) {
                        manv = cursor.getInt(indexMaNV);
                    }
                }
            } finally {
                cursor.close(); // Đóng Cursor để tránh rò rỉ bộ nhớ
            }
        }

        return manv;
    }
    public int KiemTraUser(String tenDN, String matKhau) {
        int manv = -1; // Trả về -1 nếu không tìm thấy
        String query = "SELECT " + CreateDatabase.TBL_NHANVIEN_MANV +
                " FROM " + CreateDatabase.TBL_NHANVIEN +
                " WHERE " + CreateDatabase.TBL_NHANVIEN_TENDN + " = ? AND " +
                CreateDatabase.TBL_NHANVIEN_MATKHAU + " = ?";
        Cursor cursor = database.rawQuery(query, new String[]{tenDN, matKhau});

        if (cursor.moveToFirst()) {
            manv = cursor.getInt(0); // Lấy giá trị MANV từ cột đầu tiên
        }
        cursor.close();
        return manv; // Trả về MANV nếu tìm thấy, -1 nếu không tìm thấy
    }


    public boolean KtraTonTaiNV(){
        String query = "SELECT * FROM "+CreateDatabase.TBL_NHANVIEN;
        Cursor cursor =database.rawQuery(query,null);
        if(cursor.getCount() != 0){
            return true;
        }else {
            return false;
        }
    }
    public List<NhanVienDTO> LayDSNV() {
        List<NhanVienDTO> nhanVienDTOS = new ArrayList<>();
        String query = "SELECT * FROM " + CreateDatabase.TBL_NHANVIEN;

        Cursor cursor = database.rawQuery(query, null);

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {
                        NhanVienDTO nhanVienDTO = new NhanVienDTO();
                        nhanVienDTO.setHOTENNV(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_NHANVIEN_HOTENNV)));
                        nhanVienDTO.setEMAIL(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_NHANVIEN_EMAIL)));
                        nhanVienDTO.setGIOITINH(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_NHANVIEN_GIOITINH)));
                        nhanVienDTO.setNGAYSINH(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_NHANVIEN_NGAYSINH)));
                        nhanVienDTO.setSDT(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_NHANVIEN_SDT)));
                        nhanVienDTO.setTENDN(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_NHANVIEN_TENDN)));
                        nhanVienDTO.setMATKHAU(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_NHANVIEN_MATKHAU)));
                        nhanVienDTO.setMANV(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_NHANVIEN_MANV)));
                        nhanVienDTO.setMAQUYEN(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_NHANVIEN_MAQUYEN)));

                        nhanVienDTOS.add(nhanVienDTO);
                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close(); // Đảm bảo đóng cursor để tránh rò rỉ bộ nhớ
            }
        }

        return nhanVienDTOS;
    }
    public boolean XoaNV(int manv){
        long ktra = database.delete(CreateDatabase.TBL_NHANVIEN,CreateDatabase.TBL_NHANVIEN_MANV+ " = " +manv
                ,null);
        if(ktra !=0 ){
            return true;
        }else {
            return false;
        }
    }

    public NhanVienDTO LayNVTheoMa(int manv) {
        NhanVienDTO nhanVienDTO = null;
        String query = "SELECT * FROM " + CreateDatabase.TBL_NHANVIEN + " WHERE " + CreateDatabase.TBL_NHANVIEN_MANV + " = ?";

        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(manv)});

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    nhanVienDTO = new NhanVienDTO();
                    nhanVienDTO.setHOTENNV(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_NHANVIEN_HOTENNV)));
                    nhanVienDTO.setEMAIL(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_NHANVIEN_EMAIL)));
                    nhanVienDTO.setGIOITINH(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_NHANVIEN_GIOITINH)));
                    nhanVienDTO.setNGAYSINH(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_NHANVIEN_NGAYSINH)));
                    nhanVienDTO.setSDT(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_NHANVIEN_SDT)));
                    nhanVienDTO.setTENDN(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_NHANVIEN_TENDN)));
                    nhanVienDTO.setMATKHAU(cursor.getString(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_NHANVIEN_MATKHAU)));
                    nhanVienDTO.setMANV(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_NHANVIEN_MANV)));
                    nhanVienDTO.setMAQUYEN(cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_NHANVIEN_MAQUYEN)));
                }
            } finally {
                cursor.close(); // Đảm bảo đóng Cursor để tránh rò rỉ bộ nhớ
            }
        }

        return nhanVienDTO;
    }
    public int LayQuyenNV(int manv) {
        int maquyen = -1; // Đặt giá trị mặc định khác 0 để phân biệt trường hợp không tìm thấy nhân viên
        String query = "SELECT " + CreateDatabase.TBL_NHANVIEN_MAQUYEN +
                " FROM " + CreateDatabase.TBL_NHANVIEN +
                " WHERE " + CreateDatabase.TBL_NHANVIEN_MANV + " = ?";

        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(manv)});

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    maquyen = cursor.getInt(cursor.getColumnIndexOrThrow(CreateDatabase.TBL_NHANVIEN_MAQUYEN));
                }
            } finally {
                cursor.close(); // Đảm bảo đóng Cursor để tránh rò rỉ bộ nhớ
            }
        }

        return maquyen;
    }



}
