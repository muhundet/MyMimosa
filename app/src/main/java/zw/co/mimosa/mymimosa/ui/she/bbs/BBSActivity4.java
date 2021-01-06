package zw.co.mimosa.mymimosa.ui.she.bbs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import zw.co.mimosa.mymimosa.R;

public class BBSActivity4 extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbs4);

        button = (Button) findViewById(R.id.Button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainActivity5();
            }
        });
    }

    private void openMainActivity5() {
        Intent intent = new Intent(this, BBSActivity5.class);
        startActivity(intent);
    }
}