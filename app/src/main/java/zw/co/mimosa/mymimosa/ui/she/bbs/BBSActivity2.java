package zw.co.mimosa.mymimosa.ui.she.bbs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class BBSActivity2 extends AppCompatActivity {
    CheckBox cb1,cb2,cb3,cb4,cb5,cb6,cb7,cb8,cb9,cb10,cb11,cb12,cb13,cb14,cb15,cb16,cb17,cb18;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbs2);
        cb1 = findViewById(R.id.chkExcellence);
        cb2 = findViewById(R.id.chkAccountability);
        cb3 = findViewById(R.id.chkCare);
        cb4 = findViewById(R.id.chkCommunication);
        cb5 = findViewById(R.id.chkCustomer);
        cb6 = findViewById(R.id.chkEfficiency);
        cb7 = findViewById(R.id.chkEmpowerment);
        cb8 = findViewById(R.id.chkGoal);
        cb9 = findViewById(R.id.chkInnovation);
        cb10 = findViewById(R.id.chkMotivation);
        cb11 = findViewById(R.id.chkPrayer);
        cb12 = findViewById(R.id.chkTeamwork);
        cb13 = findViewById(R.id.chkWellness);
        cb14 = findViewById(R.id.chkTransparency);
        cb15 = findViewById(R.id.chkSustainability);
        cb16 = findViewById(R.id.chkRecognition);
        cb17 = findViewById(R.id.chkOwnership);
        cb18 = findViewById(R.id.chkIntegrity);

        Button button = findViewById(R.id.Button);
       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                openMainActivity3();
            }
        });
    }

    private void openMainActivity3() {
        Intent intent = new Intent(this, BBSActivity3.class);
        startActivity(intent);
    }
}
