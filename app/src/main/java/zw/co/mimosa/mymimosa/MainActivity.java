package zw.co.mimosa.mymimosa;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import zw.co.mimosa.mymimosa.ui.main.FormsFragment;
import zw.co.mimosa.mymimosa.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {
    String globalEmployeeId;
    String globalLastName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        Intent intent = getIntent();
        globalEmployeeId = intent.getStringExtra("EMPLOYEEID");
        globalLastName = intent.getStringExtra("LASTNAME");

        Bundle bundle = new Bundle();
        bundle.putString("LASTNAME", globalEmployeeId);
        FormsFragment fragobj = new FormsFragment();
        fragobj.setArguments(bundle);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Logged in as " + globalLastName, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}