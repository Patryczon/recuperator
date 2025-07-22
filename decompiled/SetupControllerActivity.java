package pl.alres.controller_1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/* loaded from: /Users/patrykgalazka/Downloads/msr23nikol/classes.dex */
public class SetupControllerActivity extends AppCompatActivity {
    int bps_redtw;
    int bps_tryb;
    int bps_tw;
    int bps_tz;
    int din_tryb;
    int gwc_comfort;
    int gwc_cool_tz;
    int gwc_heat_tz;
    int gwc_tryb;
    int heat_Kp;
    int heat_Ti;
    int heat_Tn;
    int heat_Tw;
    int heat_rTn;
    int heat_tryb;
    Intent intent;
    String[] tmp_str;
    int[] tmp_i = new int[6];
    String tmp_str1 = BuildConfig.FLAVOR;
    String tmp_str2 = BuildConfig.FLAVOR;
    int[] obr = new int[5];

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        int i;
        int i2;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_controller);
        this.intent = getIntent();
        Bitmap bmp = Bitmap.createBitmap(290, 62, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint p = new Paint();
        char c = 0;
        p.setAntiAlias(false);
        p.setColor(-3355444);
        p.setStrokeWidth(1.0f);
        p.setAlpha(255);
        p.setStyle(Paint.Style.FILL);
        TextView txt_day = (TextView) findViewById(R.id.txtViewHarmValue);
        txt_day.setTextColor(-1);
        txt_day.setText(this.intent.getStringExtra("ma_cfg_day"));
        this.tmp_i = this.intent.getIntArrayExtra("ma_cfg_harm");
        int[] tmp_j = new int[24];
        int j = 0;
        while (true) {
            i = 2;
            if (j >= 6) {
                break;
            }
            int[] iArr = this.tmp_i;
            tmp_j[j * 4] = (iArr[j] >> 12) & 15;
            tmp_j[(j * 4) + 1] = (iArr[j] >> 8) & 15;
            tmp_j[(j * 4) + 2] = (iArr[j] >> 4) & 15;
            tmp_j[(j * 4) + 3] = iArr[j] & 15;
            j++;
        }
        this.tmp_i[0] = this.intent.getIntExtra("ma_cfg_hour", -1);
        int i3 = 0;
        while (i3 < 24) {
            if (i3 == this.tmp_i[c]) {
                p.setColor(-16711936);
            } else {
                p.setColor(-3355444);
            }
            int j2 = 0;
            while (j2 < tmp_j[i3]) {
                canvas.drawRect((i3 * 12) + i, 62 - (j2 * 13), (i3 * 12) + 12, (62 - 10) - (j2 * 13), p);
                j2++;
                tmp_j = tmp_j;
                i = 2;
            }
            i3++;
            i = 2;
            c = 0;
        }
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.constLay);
        constraintLayout.setBackground(new BitmapDrawable(bmp));
        this.gwc_tryb = this.intent.getIntExtra("ma_cfg_gwc_tryb", -1);
        int i4 = this.gwc_tryb;
        if (i4 == 0) {
            i2 = 2;
            this.tmp_str1 = getString(R.string.str_OFF);
        } else if (i4 != 1) {
            i2 = 2;
            if (i4 == 2) {
                this.tmp_str1 = getString(R.string.str_AUTO);
            } else if (i4 == 3) {
                this.tmp_str1 = getString(R.string.str_AUTO_R);
            } else {
                this.tmp_str1 = "-----";
            }
        } else {
            i2 = 2;
            this.tmp_str1 = getString(R.string.str_ON);
        }
        int intExtra = this.intent.getIntExtra("ma_cfg_gwc_comfort", -1);
        this.gwc_comfort = intExtra;
        if (intExtra == 0) {
            this.tmp_str2 = getString(R.string.str_COOL);
        } else if (intExtra == 1) {
            this.tmp_str2 = getString(R.string.str_HEAT);
        } else if (intExtra == i2) {
            this.tmp_str2 = getString(R.string.str_TempTz);
        }
        TextView textViewGWC = (TextView) findViewById(R.id.txtViewGwcConfig);
        textViewGWC.setTextColor(-1);
        textViewGWC.setText((CharSequence) null);
        if (this.gwc_tryb > 1) {
            textViewGWC.append(this.tmp_str1 + "   " + this.tmp_str2);
        } else {
            textViewGWC.setText(this.tmp_str1);
        }
        this.gwc_heat_tz = this.intent.getIntExtra("ma_cfg_gwc_heat_tz", -1);
        this.gwc_cool_tz = this.intent.getIntExtra("ma_cfg_gwc_cool_tz", -1);
        this.bps_tryb = this.intent.getIntExtra("ma_cfg_bps_tryb", -1);
        int i5 = this.bps_tryb;
        if (i5 == 0) {
            this.tmp_str1 = getString(R.string.str_OFF);
        } else if (i5 == 1) {
            this.tmp_str1 = getString(R.string.str_ON);
        } else if (i5 == i2) {
            this.tmp_str1 = getString(R.string.str_AUTO);
        } else {
            this.tmp_str1 = "-----";
        }
        this.bps_tz = this.intent.getIntExtra("ma_cfg_bps_tz", 0);
        this.bps_tw = this.intent.getIntExtra("ma_cfg_bps_tw", 0);
        TextView textViewBPS = (TextView) findViewById(R.id.txtViewBpsConfig);
        textViewBPS.setTextColor(-1);
        textViewBPS.setText((CharSequence) null);
        if (this.bps_tryb > 1) {
            textViewBPS.append(this.tmp_str1 + "   ");
            textViewBPS.append(getString(R.string.str_Tzbps) + String.valueOf(this.bps_tz) + "°C   ");
            textViewBPS.append(getString(R.string.str_Twbps) + String.valueOf(this.bps_tw) + "°C");
        } else {
            textViewBPS.setText(this.tmp_str1);
        }
        this.bps_redtw = this.intent.getIntExtra("ma_cfg_bps_redtw", 0);
        this.obr = this.intent.getIntArrayExtra("ma_cfg_obroty");
        TextView textViewObroty = (TextView) findViewById(R.id.txtViewObrValue);
        textViewObroty.setTextColor(-1);
        textViewObroty.setText((CharSequence) null);
        textViewObroty.append("I=" + String.valueOf(this.obr[0]));
        textViewObroty.append(" II=" + String.valueOf(this.obr[1]));
        textViewObroty.append(" III=" + String.valueOf(this.obr[i2]));
        textViewObroty.append(" IV=" + String.valueOf(this.obr[3]));
        textViewObroty.append(" V=" + String.valueOf(this.obr[4]));
        this.tmp_i = this.intent.getIntArrayExtra("ma_cfg_dpz");
        TextView textViewdpz = (TextView) findViewById(R.id.txtView_dpzValue);
        textViewdpz.setTextColor(-1);
        textViewdpz.setText((CharSequence) null);
        textViewdpz.append(String.valueOf(this.tmp_i[0]) + " / " + String.valueOf(this.tmp_i[1]) + " / " + String.valueOf(this.tmp_i[2]) + " / " + String.valueOf(this.tmp_i[3]) + " / " + String.valueOf(this.tmp_i[4]));
        this.tmp_i = this.intent.getIntArrayExtra("ma_cfg_dpg");
        TextView textViewdpg = (TextView) findViewById(R.id.txtView_dpgValue);
        textViewdpg.setTextColor(-1);
        textViewdpg.setText((CharSequence) null);
        textViewdpg.append(String.valueOf(this.tmp_i[0]) + " / " + String.valueOf(this.tmp_i[1]) + " / " + String.valueOf(this.tmp_i[2]) + " / " + String.valueOf(this.tmp_i[3]) + " / " + String.valueOf(this.tmp_i[4]));
        this.tmp_i = this.intent.getIntArrayExtra("ma_cfg_heat");
        TextView textViewheat = (TextView) findViewById(R.id.txtViewHeaterValue);
        textViewheat.setTextColor(-1);
        int i6 = this.tmp_i[0];
        if (i6 == 0) {
            this.tmp_str1 = getString(R.string.str_OFF);
        } else if (i6 == 1) {
            this.tmp_str1 = getString(R.string.str_HE);
        } else if (i6 == 2) {
            this.tmp_str1 = getString(R.string.str_HW);
        } else if (i6 == 3) {
            this.tmp_str1 = getString(R.string.str_HC);
        }
        textViewheat.setText((CharSequence) null);
        int[] iArr2 = this.tmp_i;
        int max_x = iArr2[0];
        if (max_x == 1 || iArr2[0] == 2) {
            textViewheat.append(this.tmp_str1 + "  " + getString(R.string.str_Tn) + String.valueOf(this.tmp_i[1]) + " (" + String.valueOf(this.tmp_i[3]) + getString(R.string.str_Tw2) + String.valueOf(this.tmp_i[2]) + "°C");
        } else if (iArr2[0] == 3) {
            textViewheat.append(this.tmp_str1 + getString(R.string.str_Tn) + String.valueOf(this.tmp_i[1]));
        } else {
            textViewheat.append(this.tmp_str1);
        }
        this.heat_tryb = this.tmp_i[0];
        this.din_tryb = this.intent.getIntExtra("ma_cfg_we5", -1);
        switch (this.din_tryb) {
            case 0:
                this.tmp_str1 = getString(R.string.str_OFF);
                break;
            case 1:
                this.tmp_str1 = getString(R.string.str_CLEAN);
                break;
            case 2:
                this.tmp_str1 = getString(R.string.str_FIRE);
                break;
            case 3:
                this.tmp_str1 = getString(R.string.str_ALARM);
                break;
            case 4:
                this.tmp_str1 = getString(R.string.str_HIGR);
                break;
            case 5:
                this.tmp_str1 = getString(R.string.str_TERM);
                break;
            case 6:
                this.tmp_str1 = getString(R.string.str_USER);
                break;
        }
        TextView textViewWE5 = (TextView) findViewById(R.id.txtViewWe5Value);
        textViewWE5.setTextColor(-1);
        textViewWE5.setText(this.tmp_str1);
        int intExtra2 = this.intent.getIntExtra("ma_cfg_defr", -1);
        if (intExtra2 == 0) {
            this.tmp_str1 = getString(R.string.str_OFF);
        } else if (intExtra2 == 1) {
            this.tmp_str1 = getString(R.string.str_PREH);
        } else if (intExtra2 == 2) {
            this.tmp_str1 = getString(R.string.str_TIMER);
        } else if (intExtra2 == 3) {
            this.tmp_str1 = getString(R.string.str_FIRM);
        }
        TextView textViewDefrost = (TextView) findViewById(R.id.txtViewDefrValue);
        textViewDefrost.setTextColor(-1);
        textViewDefrost.setText(this.tmp_str1);
    }

    public void onClickGwcConfig(View view) {
        this.intent = new Intent(this, (Class<?>) GwcConfigActivity.class);
        this.intent.putExtra("sc_cfg_gwc_tryb", this.gwc_tryb);
        startActivity(this.intent);
    }

    public void onClickBpsConfig(View view) {
        this.intent = new Intent(this, (Class<?>) BpsConfigActivity.class);
        this.intent.putExtra("sc_cfg_bps_tryb", this.bps_tryb);
        startActivity(this.intent);
    }

    public void onClickHeaterConfig(View view) {
        this.intent = new Intent(this, (Class<?>) HeaterConfigActivity.class);
        this.intent.putExtra("sc_cfg_heat_tryb", this.heat_tryb);
        startActivity(this.intent);
    }

    public void onClickWe5Config(View view) {
        this.intent = new Intent(this, (Class<?>) DinConfigActivity.class);
        this.intent.putExtra("sc_cfg_din_tryb", this.din_tryb);
        startActivity(this.intent);
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        this.intent = new Intent(this, (Class<?>) MainActivity.class);
        startActivity(this.intent);
    }
}
