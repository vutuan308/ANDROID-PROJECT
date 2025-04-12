package com.example.qlcafe.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.example.qlcafe.DAO.NhanVienDAO;
import com.example.qlcafe.DTO.ThanhToanDTO;
import com.example.qlcafe.Fragments.DisplayHomeFragment;
import com.example.qlcafe.Fragments.DisplayInformationFragment;
import com.example.qlcafe.Fragments.DisplayStaffFragment;
import com.example.qlcafe.R;

import java.util.List;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    MenuItem selectedFeature, selectedManager;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FragmentManager fragmentManager;
    TextView TXT_menu_tennv, TXT_menu_hotennv;
    int maquyen = 0, manv;
    SharedPreferences sharedPreferences;
    BottomNavigationView bot_nav;
    List<ThanhToanDTO> thanhToanDTOList;
    NhanVienDAO nhanVienDAO;


    private BottomNavigationView.OnNavigationItemSelectedListener
            mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                // Hiển thị trang chủ
                FragmentTransaction tranDisplayHome = fragmentManager.beginTransaction();
                DisplayHomeFragment displayHomeFragment = new DisplayHomeFragment();
                tranDisplayHome.replace(R.id.contentView, displayHomeFragment);
                tranDisplayHome.commit();
                navigationView.setCheckedItem(itemId);
                return true;

            } else if (itemId == R.id.nav_staff) {
                if (maquyen == 1) {
                    // Hiển thị nhân viên nếu có quyền
                    FragmentTransaction tranDisplayStaff = fragmentManager.beginTransaction();
                    DisplayStaffFragment displayStaffFragment = new DisplayStaffFragment();
                    tranDisplayStaff.replace(R.id.contentView, displayStaffFragment);
                    tranDisplayStaff.commit();
                    navigationView.setCheckedItem(itemId);
                } else {
                    Toast.makeText(getApplicationContext(), "Bạn không có quyền truy cập", Toast.LENGTH_SHORT).show();
                }
                return true;

            } else if (itemId == R.id.nav_information) {
                // Hiển thị thông tin
                FragmentTransaction tranDisplayStatistic = fragmentManager.beginTransaction();
                DisplayInformationFragment displayInformationFragment = new DisplayInformationFragment();
                tranDisplayStatistic.replace(R.id.contentView, displayInformationFragment);
                tranDisplayStatistic.commit();
                navigationView.setCheckedItem(itemId);
                return true;
            }

            return false;
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_nav);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //region thuộc tính bên view
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView = (NavigationView)findViewById(R.id.navigation_view_trangchu);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        View view = navigationView.getHeaderView(0);
        TXT_menu_tennv = (TextView) view.findViewById(R.id.txt_menu_tennv);
        //endregion


        //xử lý toolbar và navigation
        setSupportActionBar(toolbar); //tạo toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //tạo nút mở navigation
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar
        ,R.string.opentoggle,R.string.closetoggle){
            @Override
            public void onDrawerOpened(View drawerView) {    super.onDrawerOpened(drawerView); }

            @Override
            public void onDrawerClosed(View drawerView) {    super.onDrawerClosed(drawerView); }
        };
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        //lấy file share prefer
        sharedPreferences = getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen",0);

        //hiện thị fragment home mặc định
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction tranDisplayHome = fragmentManager.beginTransaction();
        DisplayHomeFragment displayHomeFragment = new DisplayHomeFragment();
        tranDisplayHome.replace(R.id.contentView, displayHomeFragment);
        tranDisplayHome.commit();
        navigationView.setCheckedItem(R.id.nav_home);



    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        return false;
    }

}