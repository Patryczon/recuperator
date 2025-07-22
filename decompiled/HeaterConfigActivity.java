package pl.alres.controller_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;

/* loaded from: /Users/patrykgalazka/Downloads/msr23nikol/classes.dex */
public class HeaterConfigActivity extends AppCompatActivity {
    int heat_tryb;
    Intent intent;
    RadioButton rbt_heat_HC;
    RadioButton rbt_heat_HE;
    RadioButton rbt_heat_HW;
    RadioButton rbt_heat_Off;

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heater_config);
        setTitle("MSR-23 " + getString(R.string.str_HEATER));
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.x = 0;
        params.height = 1100;
        params.width = 800;
        params.y = -200;
        getWindow().setAttributes(params);
        this.rbt_heat_Off = (RadioButton) findViewById(R.id.rbtnHeatOff);
        this.rbt_heat_HE = (RadioButton) findViewById(R.id.rbtnHeatHE);
        this.rbt_heat_HW = (RadioButton) findViewById(R.id.rbtnHeatHW);
        this.rbt_heat_HC = (RadioButton) findViewById(R.id.rbtnHeatHC);
        this.intent = new Intent(this, (Class<?>) MainActivity.class);
        this.intent = getIntent();
        this.heat_tryb = this.intent.getIntExtra("sc_cfg_heat_tryb", 0);
        int i = this.heat_tryb;
        if (i == 0) {
            this.rbt_heat_Off.setChecked(true);
            return;
        }
        if (i == 1) {
            this.rbt_heat_HE.setChecked(true);
        } else if (i == 2) {
            this.rbt_heat_HW.setChecked(true);
        } else if (i == 3) {
            this.rbt_heat_HC.setChecked(true);
        }
    }

    public void Update(View view) {
        this.intent = new Intent(this, (Class<?>) MainActivity.class);
        this.intent.putExtra("hc_set_heat_tryb", this.heat_tryb);
        this.intent.putExtra("update_settings", 4);
        startActivity(this.intent);
    }

    public void HeatOff(View view) {
        this.rbt_heat_HE.setChecked(false);
        this.rbt_heat_HW.setChecked(false);
        this.rbt_heat_HC.setChecked(false);
        this.heat_tryb = 0;
    }

    public void HeatHE(View view) {
        this.rbt_heat_Off.setChecked(false);
        this.rbt_heat_HW.setChecked(false);
        this.rbt_heat_HC.setChecked(false);
        this.heat_tryb = 1;
    }

    public void HeatHW(View view) {
        this.rbt_heat_Off.setChecked(false);
        this.rbt_heat_HE.setChecked(false);
        this.rbt_heat_HC.setChecked(false);
        this.heat_tryb = 2;
    }

    public void HeatHC(View view) {
        this.rbt_heat_Off.setChecked(false);
        this.rbt_heat_HE.setChecked(false);
        this.rbt_heat_HW.setChecked(false);
        this.heat_tryb = 3;
    }
}
