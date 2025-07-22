package pl.alres.controller_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;

/* loaded from: /Users/patrykgalazka/Downloads/msr23nikol/classes.dex */
public class DinConfigActivity extends AppCompatActivity {
    int din_tryb;
    Intent intent;
    RadioButton rbt_dinalarm;
    RadioButton rbt_dinclean;
    RadioButton rbt_dinfire;
    RadioButton rbt_dinhigr;
    RadioButton rbt_dinoff;
    RadioButton rbt_dinterm;
    RadioButton rbt_dinuser;

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_din_config);
        setTitle("MSR-23 " + getString(R.string.str_DIN));
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.x = 0;
        params.height = 1100;
        params.width = 800;
        params.y = -200;
        getWindow().setAttributes(params);
        this.rbt_dinoff = (RadioButton) findViewById(R.id.rbtnDinOff);
        this.rbt_dinclean = (RadioButton) findViewById(R.id.rbtnDinClean);
        this.rbt_dinfire = (RadioButton) findViewById(R.id.rbtnDinFire);
        this.rbt_dinalarm = (RadioButton) findViewById(R.id.rbtnDinAlarm);
        this.rbt_dinhigr = (RadioButton) findViewById(R.id.rbtnDinHigr);
        this.rbt_dinterm = (RadioButton) findViewById(R.id.rbtnDinTerm);
        this.rbt_dinuser = (RadioButton) findViewById(R.id.rbtnDinUser);
        this.intent = new Intent(this, (Class<?>) MainActivity.class);
        this.intent = getIntent();
        this.din_tryb = this.intent.getIntExtra("sc_cfg_din_tryb", 0);
        switch (this.din_tryb) {
            case 0:
                this.rbt_dinoff.setChecked(true);
                break;
            case 1:
                this.rbt_dinclean.setChecked(true);
                break;
            case 2:
                this.rbt_dinfire.setChecked(true);
                break;
            case 3:
                this.rbt_dinalarm.setChecked(true);
                break;
            case 4:
                this.rbt_dinhigr.setChecked(true);
                break;
            case 5:
                this.rbt_dinterm.setChecked(true);
                break;
            case 6:
                this.rbt_dinuser.setChecked(true);
                break;
        }
    }

    public void Update(View view) {
        this.intent = new Intent(this, (Class<?>) MainActivity.class);
        this.intent.putExtra("dc_set_din_tryb", this.din_tryb);
        this.intent.putExtra("update_settings", 3);
        startActivity(this.intent);
    }

    public void onDinOff(View view) {
        this.rbt_dinclean.setChecked(false);
        this.rbt_dinfire.setChecked(false);
        this.rbt_dinalarm.setChecked(false);
        this.rbt_dinhigr.setChecked(false);
        this.rbt_dinterm.setChecked(false);
        this.rbt_dinuser.setChecked(false);
        this.din_tryb = 0;
    }

    public void onDinClean(View view) {
        this.rbt_dinoff.setChecked(false);
        this.rbt_dinfire.setChecked(false);
        this.rbt_dinalarm.setChecked(false);
        this.rbt_dinhigr.setChecked(false);
        this.rbt_dinterm.setChecked(false);
        this.rbt_dinuser.setChecked(false);
        this.din_tryb = 1;
    }

    public void onDinFire(View view) {
        this.rbt_dinoff.setChecked(false);
        this.rbt_dinclean.setChecked(false);
        this.rbt_dinalarm.setChecked(false);
        this.rbt_dinhigr.setChecked(false);
        this.rbt_dinterm.setChecked(false);
        this.rbt_dinuser.setChecked(false);
        this.din_tryb = 2;
    }

    public void onDinAlarm(View view) {
        this.rbt_dinoff.setChecked(false);
        this.rbt_dinclean.setChecked(false);
        this.rbt_dinfire.setChecked(false);
        this.rbt_dinhigr.setChecked(false);
        this.rbt_dinterm.setChecked(false);
        this.rbt_dinuser.setChecked(false);
        this.din_tryb = 3;
    }

    public void onDinHigr(View view) {
        this.rbt_dinoff.setChecked(false);
        this.rbt_dinclean.setChecked(false);
        this.rbt_dinfire.setChecked(false);
        this.rbt_dinalarm.setChecked(false);
        this.rbt_dinterm.setChecked(false);
        this.rbt_dinuser.setChecked(false);
        this.din_tryb = 4;
    }

    public void onDinTerm(View view) {
        this.rbt_dinoff.setChecked(false);
        this.rbt_dinclean.setChecked(false);
        this.rbt_dinfire.setChecked(false);
        this.rbt_dinalarm.setChecked(false);
        this.rbt_dinhigr.setChecked(false);
        this.rbt_dinuser.setChecked(false);
        this.din_tryb = 5;
    }

    public void onDinUser(View view) {
        this.rbt_dinoff.setChecked(false);
        this.rbt_dinclean.setChecked(false);
        this.rbt_dinfire.setChecked(false);
        this.rbt_dinalarm.setChecked(false);
        this.rbt_dinhigr.setChecked(false);
        this.rbt_dinterm.setChecked(false);
        this.din_tryb = 6;
    }
}
