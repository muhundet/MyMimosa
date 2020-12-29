package zw.co.mimosa.mymimosa.ui.hr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import zw.co.mimosa.mymimosa.R;

public class HrDashboardActivity extends AppCompatActivity {

    RecyclerView mRecyclerViewHrMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hr_dashboard);
        mRecyclerViewHrMenu = findViewById(R.id.rvHrMenu);
        HrMenuModel hmm = new HrMenuModel("LEAVE AND TEMPORARY EARNINGS FORM");
        HrMenuModel hmm1 = new HrMenuModel("EDUCATIONAL ASSISTANCE FORM");
        HrMenuModel hmm2 = new HrMenuModel("ACTING ALLOWANCE FORM");
        ArrayList<HrMenuModel> hrMenuModels = new ArrayList<>();
        hrMenuModels.add(hmm);
        hrMenuModels.add(hmm1);
        hrMenuModels.add(hmm2);
        HrFormRecyclerAdapter adapter = new HrFormRecyclerAdapter(this, hrMenuModels);
        mRecyclerViewHrMenu.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewHrMenu.setAdapter(adapter);
    }
}