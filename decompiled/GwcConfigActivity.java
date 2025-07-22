package pl.alres.controller_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;

/* loaded from: /Users/patrykgalazka/Downloads/msr23nikol/classes.dex */
public class GwcConfigActivity extends AppCompatActivity {
    int gwc_komfort;
    int gwc_tryb;
    Intent intent;
    RadioButton rbt_gwcauto;
    RadioButton rbt_gwcautor;
    RadioButton rbt_gwcoff;
    RadioButton rbt_gwcon;

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gwc_config);
        setTitle("MSR-23 " + getString(R.string.str_GWC));
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.x = 0;
        params.height = 1100;
        params.width = 800;
        params.y = -200;
        getWindow().setAttributes(params);
        this.rbt_gwcoff = (RadioButton) findViewById(R.id.rbtnGwcOff);
        this.rbt_gwcon = (RadioButton) findViewById(R.id.rbtnGwcOn);
        this.rbt_gwcauto = (RadioButton) findViewById(R.id.rbtnGwcAuto);
        this.rbt_gwcautor = (RadioButton) findViewById(R.id.rbtnGwcAutoReg);
        this.intent = new Intent(this, (Class<?>) MainActivity.class);
        this.intent = getIntent();
        this.gwc_tryb = this.intent.getIntExtra("sc_cfg_gwc_tryb", 0);
        int i = this.gwc_tryb;
        if (i == 0) {
            this.rbt_gwcoff.setChecked(true);
            return;
        }
        if (i == 1) {
            this.rbt_gwcon.setChecked(true);
        } else if (i == 2) {
            this.rbt_gwcauto.setChecked(true);
        } else if (i == 3) {
            this.rbt_gwcautor.setChecked(true);
        }
    }

    public void Update(View view) {
        this.intent = new Intent(this, (Class<?>) MainActivity.class);
        this.intent.putExtra("gc_set_gwc_tryb", this.gwc_tryb);
        this.intent.putExtra("update_settings", 1);
        startActivity(this.intent);
    }

    public void onGwcOff(View view) {
        this.rbt_gwcon.setChecked(false);
        this.rbt_gwcauto.setChecked(false);
        this.rbt_gwcautor.setChecked(false);
        this.gwc_tryb = 0;
    }

    public void onGwcOn(View view) {
        this.rbt_gwcoff.setChecked(false);
        this.rbt_gwcauto.setChecked(false);
        this.rbt_gwcautor.setChecked(false);
        this.gwc_tryb = 1;
    }

    public void onGwcAuto(View view) {
        this.rbt_gwcoff.setChecked(false);
        this.rbt_gwcon.setChecked(false);
        this.rbt_gwcautor.setChecked(false);
        this.gwc_tryb = 2;
    }

    public void onGwcAutoR(View view) {
        this.rbt_gwcoff.setChecked(false);
        this.rbt_gwcon.setChecked(false);
        this.rbt_gwcauto.setChecked(false);
        this.gwc_tryb = 3;
    }
}
