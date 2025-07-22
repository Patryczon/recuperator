package pl.alres.controller_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;

/* loaded from: /Users/patrykgalazka/Downloads/msr23nikol/classes.dex */
public class BpsConfigActivity extends AppCompatActivity {
    int bps_tryb;
    Intent intent;
    RadioButton rbt_bpsauto;
    RadioButton rbt_bpsoff;
    RadioButton rbt_bpson;

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bps_config);
        setTitle("MSR-23 " + getString(R.string.str_BYPASS));
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.x = 0;
        params.height = 1100;
        params.width = 800;
        params.y = -200;
        getWindow().setAttributes(params);
        this.rbt_bpsoff = (RadioButton) findViewById(R.id.rbtnBpsOff);
        this.rbt_bpson = (RadioButton) findViewById(R.id.rbtnBpsOn);
        this.rbt_bpsauto = (RadioButton) findViewById(R.id.rbtnBpsAuto);
        this.intent = new Intent(this, (Class<?>) MainActivity.class);
        this.intent = getIntent();
        this.bps_tryb = this.intent.getIntExtra("sc_cfg_bps_tryb", 0);
        int i = this.bps_tryb;
        if (i == 0) {
            this.rbt_bpsoff.setChecked(true);
        } else if (i == 1) {
            this.rbt_bpson.setChecked(true);
        } else if (i == 2) {
            this.rbt_bpsauto.setChecked(true);
        }
    }

    public void Update(View view) {
        this.intent = new Intent(this, (Class<?>) MainActivity.class);
        this.intent.putExtra("bc_set_bps_tryb", this.bps_tryb);
        this.intent.putExtra("update_settings", 2);
        startActivity(this.intent);
    }

    public void onBpsOff(View view) {
        this.rbt_bpson.setChecked(false);
        this.rbt_bpsauto.setChecked(false);
        this.bps_tryb = 0;
    }

    public void onBpsOn(View view) {
        this.rbt_bpsoff.setChecked(false);
        this.rbt_bpsauto.setChecked(false);
        this.bps_tryb = 1;
    }

    public void onBpsAuto(View view) {
        this.rbt_bpsoff.setChecked(false);
        this.rbt_bpson.setChecked(false);
        this.bps_tryb = 2;
    }
}
