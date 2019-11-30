package com.example.theham.Activities;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.theham.FragmentUtils;
import com.example.theham.Fragments.ExchangeFragment;
import com.example.theham.Fragments.MakingFragment;
import com.example.theham.Fragments.RecyclerFragment;
import com.example.theham.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    long backKeyPressedTime;    //앱종료 위한 백버튼 누른시간

    Toolbar toolbar;

    BottomNavigationView bottomNavigationView;
    ArrayList<Fragment> fragments;
    FragmentUtils fragmentUtils;

    public static byte[] byteArray;

    ExchangeFragment exchangeFragment;
    RecyclerFragment recyclerFragment;
    // ScanFragment scanFragment;
    MakingFragment makingFragment;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUp();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
// Get the ActionBar here to configure the way it behaves.
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김


    }

    //뒤로가기 2번하면 앱종료
    @Override
    public void onBackPressed() {
        //1번째 백버튼 클릭
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
        //2번째 백버튼 클릭 (종료)
        else {
            AppFinish();


        }
    }

    //앱종료
    public void AppFinish() {
        finish();
        System.exit(0);
        android.os.Process.killProcess(android.os.Process.myPid());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_menu, menu);

        return true;

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void setUp() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#000051"));
        fragments = new ArrayList<>();

        exchangeFragment = new ExchangeFragment();
        recyclerFragment = new RecyclerFragment();
        makingFragment = new MakingFragment();

        fragments.add(exchangeFragment);
        fragments.add(recyclerFragment);
        fragments.add(makingFragment);

        fragmentUtils = new FragmentUtils(R.id.framelayout, fragments);
       // fragmentUtils.setCurrentFragmentByPosition(getSupportFragmentManager(), 0, new Bundle());
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.bottom_exchange:
                        fragmentUtils.setCurrentFragmentByPosition(getSupportFragmentManager(), 0, new Bundle());
                        return true;
                    case R.id.bottom_recycler:
                        fragmentUtils.setCurrentFragmentByPosition(getSupportFragmentManager(), 1, new Bundle());
                        return true;
                    case R.id.bottom_making:
                        fragmentUtils.setCurrentFragmentByPosition(getSupportFragmentManager(), 2, new Bundle());
                        return true;
                }
                return false;
            }
        });
    }

}
