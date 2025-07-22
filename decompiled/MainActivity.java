package pl.alres.controller_1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.InputDeviceCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/* loaded from: /Users/patrykgalazka/Downloads/msr23nikol/classes.dex */
public class MainActivity extends AppCompatActivity {
    private static Socket socket;
    int SERVERPORT;
    ToggleButton autoButton;
    TextView autoTxtView;
    int bps_redtw;
    int bps_tryb;
    int bps_tw;
    int bps_tz;
    private ClientThread clientThread;
    TextView clockView;
    TextView connectstatusTxtView;
    volatile int contr_id;
    volatile int counter_wifi_error;
    int defr;
    int dt_value;
    int gwc_comfort;
    int gwc_cool_tz;
    int gwc_heat_tz;
    int gwc_tryb;
    int h_value;
    Handler handler;
    Intent intent;
    TextView ipTxtView;
    ToggleButton komButton;
    ToggleButton manButton;
    SeekBar manSeekBar;
    TextView manTxtView;
    volatile int modbus_fun;
    volatile int reg_adr;
    volatile int reg_numb;
    private Thread thread;
    Toast toast;
    TextView txt_left_info;
    TextView txt_right_info;
    TextView valueTg;
    TextView valueTn;
    TextView valueTw;
    TextView valueTz;
    TextView valveState;
    ToggleButton ventButton;
    SeekBar ventSeekBar;
    TextView ventTxtView;
    Handler vhandler;
    int we5;
    volatile int delay_time = 500;
    String SERVER_IP = BuildConfig.FLAVOR;
    int net_config = 0;
    volatile int[] reg_val_h = new int[5];
    volatile int[] reg_val_l = new int[5];
    String dt_str = BuildConfig.FLAVOR;
    int[] obr = new int[5];
    int[] dpz = new int[5];
    int[] dpg = new int[5];
    int[] heat = new int[4];
    int[] harm = new int[6];
    int old_ver = 0;
    private byte[] buf_in = new byte[213];
    volatile int cfg_update = 0;
    volatile int flaga_connected = 0;

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("MSR-23");
        this.txt_right_info = (TextView) findViewById(R.id.textView_counter);
        this.txt_left_info = (TextView) findViewById(R.id.textView_address);
        this.ipTxtView = (TextView) findViewById(R.id.txtViewIP);
        this.ipTxtView.setTextColor(-1);
        this.connectstatusTxtView = (TextView) findViewById(R.id.txtOnline);
        this.autoTxtView = (TextView) findViewById(R.id.txtBieg);
        this.autoTxtView.setVisibility(4);
        this.autoTxtView.setTextColor(-1);
        this.manTxtView = (TextView) findViewById(R.id.txtObrProcent);
        this.manTxtView.setVisibility(4);
        this.manTxtView.setTextColor(-1);
        this.ventTxtView = (TextView) findViewById(R.id.txtVentTime);
        this.ventTxtView.setVisibility(4);
        this.ventTxtView.setTextColor(-1);
        this.autoButton = (ToggleButton) findViewById(R.id.btnAuto);
        this.manButton = (ToggleButton) findViewById(R.id.btnMan);
        this.ventButton = (ToggleButton) findViewById(R.id.btnVent);
        this.komButton = (ToggleButton) findViewById(R.id.btnKominek);
        this.manSeekBar = (SeekBar) findViewById(R.id.seekBarMan);
        this.manSeekBar.setVisibility(4);
        this.valueTz = (TextView) findViewById(R.id.txtTz_value);
        this.valueTz.setTextColor(-1);
        this.valueTg = (TextView) findViewById(R.id.txtTg_value);
        this.valueTg.setTextColor(-1);
        this.valueTw = (TextView) findViewById(R.id.txtTw_value);
        this.valueTw.setTextColor(-1);
        this.valueTn = (TextView) findViewById(R.id.txtTn_value);
        this.valueTn.setTextColor(-1);
        this.clockView = (TextView) findViewById(R.id.txtViewTime);
        this.clockView.setTextColor(-1);
        this.valveState = (TextView) findViewById(R.id.txtContrState_value);
        this.valveState.setTextColor(-1);
        this.manSeekBar.setProgress(0);
        this.manTxtView.setText("0%");
        this.ventSeekBar = (SeekBar) findViewById(R.id.seekBarVentTime);
        this.ventSeekBar.setVisibility(4);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        pref.edit();
        this.net_config = pref.getInt("config_ip", 0);
        this.SERVER_IP = pref.getString("network_ip", "0.0.0.0");
        this.contr_id = pref.getInt("msr_password", 0);
        this.ipTxtView.setText(this.SERVER_IP);
        int idx = this.SERVER_IP.indexOf(":");
        if (idx >= 0) {
            String substr = this.SERVER_IP.substring(idx + 1);
            this.SERVERPORT = Integer.parseInt(substr);
            this.SERVER_IP = this.SERVER_IP.substring(0, idx);
        } else {
            this.SERVERPORT = 502;
        }
        this.counter_wifi_error = 4;
        if (this.net_config == 6524) {
            this.handler = new Handler();
            this.clientThread = new ClientThread();
            this.thread = new Thread(this.clientThread);
            this.thread.start();
            this.intent = getIntent();
            this.cfg_update = this.intent.getIntExtra("update_settings", 0);
            updateControllerConfig();
            prepareModbus();
        }
        this.valueTz.setText("--.-°C");
        this.valueTg.setText("--.-°C");
        this.valueTw.setText("--.-°C");
        this.valueTn.setText("--.-°C");
        this.manSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: pl.alres.controller_1.MainActivity.1
            int loc_prog;

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                if (progress <= 10) {
                    MainActivity.this.manTxtView.setText("STOP");
                } else {
                    MainActivity.this.manTxtView.setText(progress + "%");
                }
                this.loc_prog = progress;
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                MainActivity.this.reg_val_l[1] = this.loc_prog;
                MainActivity.this.cfg_update = 8;
            }
        });
        this.ventSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: pl.alres.controller_1.MainActivity.2
            int loc_prog;

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                MainActivity.this.ventTxtView.setText((progress * 15) + "min");
                this.loc_prog = progress * 15;
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                MainActivity.this.reg_val_l[1] = this.loc_prog;
                MainActivity.this.cfg_update = 9;
            }
        });
    }

    private void updateControllerConfig() {
        int i = this.cfg_update;
        if (i == 1) {
            this.reg_val_l[0] = this.intent.getIntExtra("gc_set_gwc_tryb", 0);
            this.reg_val_h[0] = 0;
            this.modbus_fun = 6;
            this.reg_numb = 1;
            this.reg_adr = 4;
            return;
        }
        if (i == 2) {
            this.reg_val_l[0] = this.intent.getIntExtra("bc_set_bps_tryb", 0);
            this.reg_val_h[0] = 0;
            this.modbus_fun = 6;
            this.reg_numb = 1;
            this.reg_adr = 12;
            return;
        }
        if (i == 3) {
            this.reg_val_l[0] = this.intent.getIntExtra("hc_set_heat_tryb", 0);
            this.reg_val_h[0] = 0;
            this.modbus_fun = 6;
            this.reg_numb = 1;
            this.reg_adr = 17;
            return;
        }
        if (i == 4) {
            this.reg_val_l[0] = this.intent.getIntExtra("dc_set_din_tryb", 0);
            this.reg_val_h[0] = 0;
            this.modbus_fun = 6;
            this.reg_numb = 1;
            this.reg_adr = 16;
        }
    }

    private void prepareModbus() {
        this.vhandler = new Handler();
        this.vhandler.post(new Runnable() { // from class: pl.alres.controller_1.MainActivity.3
            @Override // java.lang.Runnable
            public void run() throws Resources.NotFoundException {
                MainActivity mainActivity = MainActivity.this;
                int i = mainActivity.counter_wifi_error + 1;
                mainActivity.counter_wifi_error = i;
                if (i > 10) {
                    MainActivity.this.connectstatusTxtView.setText("offline");
                    MainActivity.this.connectstatusTxtView.setTextColor(SupportMenu.CATEGORY_MASK);
                    if (MainActivity.this.cfg_update != 0) {
                        MainActivity mainActivity2 = MainActivity.this;
                        mainActivity2.ColoredToast(mainActivity2.getString(R.string.str_No_connect), SupportMenu.CATEGORY_MASK, -1);
                    }
                    if (MainActivity.this.counter_wifi_error > 15 && MainActivity.this.cfg_update != 0) {
                        MainActivity.this.onCreate(null);
                    }
                    if (MainActivity.this.counter_wifi_error > 100) {
                        MainActivity.this.counter_wifi_error = 100;
                    }
                }
                if ((MainActivity.this.buf_in[5] & 255) == 3) {
                    if ((MainActivity.this.buf_in[7] & 255) > 127) {
                        int i2 = MainActivity.this.buf_in[8] & 255;
                        if (i2 == 1) {
                            MainActivity.this.ColoredToast("Functin error", SupportMenu.CATEGORY_MASK, -1);
                            MainActivity.this.buf_in[8] = 0;
                        } else if (i2 == 2) {
                            MainActivity.this.ColoredToast("Register error", SupportMenu.CATEGORY_MASK, -1);
                            MainActivity.this.buf_in[8] = 0;
                        } else if (i2 == 3) {
                            MainActivity.this.ColoredToast("Data error", SupportMenu.CATEGORY_MASK, -1);
                            MainActivity.this.buf_in[8] = 0;
                        }
                        MainActivity.this.connectstatusTxtView.setText("online");
                        MainActivity.this.connectstatusTxtView.setTextColor(-16711936);
                        MainActivity.this.counter_wifi_error = 0;
                    }
                } else if ((MainActivity.this.buf_in[7] & 255) == 3) {
                    if ((MainActivity.this.buf_in[106] & 255) > 0) {
                        MainActivity mainActivity3 = MainActivity.this;
                        mainActivity3.ColoredToast(mainActivity3.getString(R.string.str_Comm_error), InputDeviceCompat.SOURCE_ANY, ViewCompat.MEASURED_STATE_MASK);
                    }
                    MainActivity mainActivity4 = MainActivity.this;
                    mainActivity4.dt_value = mainActivity4.buf_in[10] & 255;
                    String[] tmp_str = MainActivity.this.getResources().getStringArray(R.array.weekdays);
                    MainActivity mainActivity5 = MainActivity.this;
                    mainActivity5.dt_str = tmp_str[mainActivity5.dt_value];
                    MainActivity mainActivity6 = MainActivity.this;
                    mainActivity6.h_value = mainActivity6.buf_in[12] & 255;
                    int tmp_i = MainActivity.this.buf_in[14] & 255;
                    MainActivity.this.clockView.setText(MainActivity.this.dt_str + "  " + String.format("%d", Integer.valueOf(MainActivity.this.h_value)) + ":" + String.format("%02d", Integer.valueOf(tmp_i)));
                    MainActivity mainActivity7 = MainActivity.this;
                    mainActivity7.gwc_tryb = mainActivity7.buf_in[16] & 255;
                    MainActivity mainActivity8 = MainActivity.this;
                    mainActivity8.gwc_comfort = mainActivity8.buf_in[18] & 255;
                    MainActivity mainActivity9 = MainActivity.this;
                    mainActivity9.gwc_heat_tz = mainActivity9.buf_in[20] & 255;
                    MainActivity mainActivity10 = MainActivity.this;
                    mainActivity10.gwc_cool_tz = mainActivity10.buf_in[22] & 255;
                    MainActivity mainActivity11 = MainActivity.this;
                    mainActivity11.bps_tryb = mainActivity11.buf_in[32] & 255;
                    MainActivity mainActivity12 = MainActivity.this;
                    mainActivity12.bps_tz = mainActivity12.buf_in[34] & 255;
                    MainActivity mainActivity13 = MainActivity.this;
                    mainActivity13.bps_tw = mainActivity13.buf_in[36] & 255;
                    MainActivity mainActivity14 = MainActivity.this;
                    mainActivity14.bps_redtw = (short) (((mainActivity14.buf_in[37] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (MainActivity.this.buf_in[38] & 255));
                    MainActivity.this.valveState.setText((CharSequence) null);
                    if ((MainActivity.this.buf_in[90] & 255) == 1) {
                        MainActivity.this.valveState.append("G  ");
                    }
                    if ((MainActivity.this.buf_in[92] & 255) == 1) {
                        MainActivity.this.valveState.append("B  ");
                    }
                    for (int i3 = 0; i3 < 5; i3++) {
                        MainActivity.this.obr[i3] = MainActivity.this.buf_in[((i3 + 29) * 2) + 8] & 255;
                    }
                    for (int i4 = 0; i4 < 5; i4++) {
                        MainActivity.this.dpz[i4] = (short) ((MainActivity.this.buf_in[((i4 + 51) * 2) + 8] & 255) | ((MainActivity.this.buf_in[((i4 + 51) * 2) + 7] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK));
                    }
                    for (int i5 = 0; i5 < 5; i5++) {
                        MainActivity.this.dpg[i5] = (short) (((MainActivity.this.buf_in[((i5 + 56) * 2) + 7] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (MainActivity.this.buf_in[((i5 + 56) * 2) + 8] & 255));
                    }
                    MainActivity.this.heat[0] = MainActivity.this.buf_in[42] & 255;
                    MainActivity.this.heat[1] = MainActivity.this.buf_in[44] & 255;
                    MainActivity.this.heat[2] = MainActivity.this.buf_in[52] & 255;
                    MainActivity.this.heat[3] = (short) (((MainActivity.this.buf_in[53] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (MainActivity.this.buf_in[54] & 255));
                    MainActivity mainActivity15 = MainActivity.this;
                    mainActivity15.we5 = mainActivity15.buf_in[40] & 255;
                    MainActivity mainActivity16 = MainActivity.this;
                    mainActivity16.defr = mainActivity16.buf_in[58] & 255;
                    for (int i6 = 0; i6 < 6; i6++) {
                        MainActivity.this.harm[i6] = ((MainActivity.this.buf_in[(((i6 + 61) + (MainActivity.this.dt_value * 6)) * 2) + 7] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (MainActivity.this.buf_in[((i6 + 61 + (MainActivity.this.dt_value * 6)) * 2) + 8] & 255);
                    }
                    int tmp_i2 = (short) (((MainActivity.this.buf_in[95] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (MainActivity.this.buf_in[96] & 255));
                    if (tmp_i2 >= -500 && tmp_i2 <= 600) {
                        MainActivity.this.valueTz.setText(String.format("%.1f°C", Float.valueOf(tmp_i2 / 10.0f)));
                    } else {
                        MainActivity.this.valueTz.setText("--.-°C");
                    }
                    int tmp_i3 = (short) (((MainActivity.this.buf_in[97] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (MainActivity.this.buf_in[98] & 255));
                    if (tmp_i3 >= -500 && tmp_i3 <= 600) {
                        MainActivity.this.valueTg.setText(String.format("%.1f°C", Float.valueOf(tmp_i3 / 10.0f)));
                    } else {
                        MainActivity.this.valueTg.setText("--.-°C");
                    }
                    int tmp_i4 = (short) (((MainActivity.this.buf_in[99] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (MainActivity.this.buf_in[100] & 255));
                    if (tmp_i4 >= -500 && tmp_i4 <= 600) {
                        MainActivity.this.valueTw.setText(String.format("%.1f°C", Float.valueOf(tmp_i4 / 10.0f)));
                    } else {
                        MainActivity.this.valueTw.setText("--.-°C");
                    }
                    int tmp_i5 = (short) (((MainActivity.this.buf_in[101] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (MainActivity.this.buf_in[102] & 255));
                    if (tmp_i5 >= -500 && tmp_i5 <= 600) {
                        MainActivity.this.valueTn.setText(String.format("%.1f°C", Float.valueOf(tmp_i5 / 10.0f)));
                    } else {
                        MainActivity.this.valueTn.setText("--.-°C");
                    }
                    if (MainActivity.this.cfg_update == 0) {
                        int i7 = MainActivity.this.buf_in[82] & 255;
                        if (i7 == 0) {
                            MainActivity.this.autoButton.setChecked(false);
                            MainActivity.this.autoTxtView.setVisibility(4);
                            MainActivity.this.ventButton.setChecked(false);
                            MainActivity.this.ventTxtView.setVisibility(4);
                            MainActivity.this.ventSeekBar.setVisibility(4);
                            MainActivity.this.manButton.setChecked(true);
                            MainActivity.this.manTxtView.setVisibility(0);
                            MainActivity.this.manSeekBar.setVisibility(0);
                            if (!MainActivity.this.manSeekBar.isPressed()) {
                                MainActivity.this.manSeekBar.setProgress(MainActivity.this.buf_in[76] & 255);
                                MainActivity.this.manTxtView.setText((MainActivity.this.buf_in[76] & 255) + "%");
                            }
                        } else if (i7 == 1) {
                            MainActivity.this.autoButton.setChecked(false);
                            MainActivity.this.autoTxtView.setVisibility(4);
                            MainActivity.this.manButton.setChecked(false);
                            MainActivity.this.manTxtView.setVisibility(4);
                            MainActivity.this.manSeekBar.setVisibility(4);
                            MainActivity.this.ventButton.setChecked(true);
                            MainActivity.this.ventTxtView.setVisibility(0);
                            MainActivity.this.ventSeekBar.setVisibility(0);
                            int tmp_i6 = MainActivity.this.buf_in[56] & 255;
                            if (tmp_i6 > 0) {
                                if (!MainActivity.this.ventSeekBar.isPressed()) {
                                    MainActivity.this.ventTxtView.setText(tmp_i6 + "min");
                                }
                            } else {
                                MainActivity.this.ventButton.setChecked(false);
                                MainActivity.this.ventSeekBar.setVisibility(4);
                            }
                        } else if (i7 == 2) {
                            MainActivity.this.manButton.setChecked(false);
                            MainActivity.this.manTxtView.setVisibility(4);
                            MainActivity.this.manSeekBar.setVisibility(4);
                            MainActivity.this.ventButton.setChecked(false);
                            MainActivity.this.ventTxtView.setVisibility(4);
                            MainActivity.this.ventSeekBar.setVisibility(4);
                            MainActivity.this.autoButton.setChecked(true);
                            MainActivity.this.autoTxtView.setVisibility(0);
                            String speed_str = BuildConfig.FLAVOR;
                            int i8 = MainActivity.this.buf_in[76] & 255;
                            if (i8 == 0) {
                                speed_str = "STOP";
                            } else if (i8 == 1) {
                                speed_str = "I";
                            } else if (i8 == 2) {
                                speed_str = "II";
                            } else if (i8 == 3) {
                                speed_str = "III";
                            } else if (i8 == 4) {
                                speed_str = "IV";
                            } else if (i8 == 5) {
                                speed_str = "V";
                            }
                            MainActivity.this.autoTxtView.setText(speed_str + " " + MainActivity.this.getString(R.string.str_gear));
                        }
                        MainActivity mainActivity17 = MainActivity.this;
                        mainActivity17.old_ver = 0;
                        int tmp_i7 = mainActivity17.buf_in[108] & 255;
                        if (tmp_i7 == 0) {
                            MainActivity.this.komButton.setChecked(false);
                        } else if (tmp_i7 == 1) {
                            MainActivity.this.komButton.setChecked(true);
                        } else {
                            MainActivity.this.komButton.setChecked(false);
                            MainActivity.this.old_ver = 1;
                        }
                    }
                    MainActivity mainActivity18 = MainActivity.this;
                    mainActivity18.old_ver = 0;
                    mainActivity18.connectstatusTxtView.setText("online");
                    MainActivity.this.connectstatusTxtView.setTextColor(-16711936);
                    MainActivity mainActivity19 = MainActivity.this;
                    mainActivity19.counter_wifi_error = 0;
                    mainActivity19.flaga_connected = 1;
                } else if ((MainActivity.this.buf_in[7] & 255) == 6 || (MainActivity.this.buf_in[7] & 255) == 16) {
                    MainActivity mainActivity20 = MainActivity.this;
                    mainActivity20.ColoredToast(mainActivity20.getString(R.string.str_Updated), -16711936, -1);
                    MainActivity mainActivity21 = MainActivity.this;
                    mainActivity21.counter_wifi_error = 0;
                    mainActivity21.cfg_update = 0;
                    mainActivity21.flaga_connected = 1;
                    mainActivity21.buf_in[7] = 0;
                }
                int i9 = MainActivity.this.cfg_update;
                if (i9 == 0) {
                    MainActivity mainActivity22 = MainActivity.this;
                    mainActivity22.modbus_fun = 3;
                    mainActivity22.reg_adr = 1;
                    mainActivity22.reg_val_l[0] = 102;
                    MainActivity.this.reg_val_h[0] = 0;
                } else {
                    switch (i9) {
                        case 5:
                            if (MainActivity.this.ventButton.isChecked()) {
                                MainActivity mainActivity23 = MainActivity.this;
                                mainActivity23.modbus_fun = 16;
                                mainActivity23.reg_adr = 37;
                                mainActivity23.reg_numb = 2;
                                mainActivity23.reg_val_l[0] = 2;
                                MainActivity.this.reg_val_h[0] = 0;
                                MainActivity.this.reg_val_l[1] = 0;
                                MainActivity.this.reg_val_h[1] = 0;
                                break;
                            } else {
                                MainActivity mainActivity24 = MainActivity.this;
                                mainActivity24.modbus_fun = 6;
                                mainActivity24.reg_adr = 37;
                                mainActivity24.reg_val_l[0] = 2;
                                MainActivity.this.reg_val_h[0] = 0;
                                break;
                            }
                        case 6:
                            MainActivity mainActivity25 = MainActivity.this;
                            mainActivity25.modbus_fun = 6;
                            mainActivity25.reg_adr = 37;
                            mainActivity25.reg_val_l[0] = 0;
                            MainActivity.this.reg_val_h[0] = 0;
                            break;
                        case 7:
                            if (MainActivity.this.old_ver == 0) {
                                MainActivity mainActivity26 = MainActivity.this;
                                mainActivity26.modbus_fun = 16;
                                mainActivity26.reg_adr = 37;
                                mainActivity26.reg_numb = 2;
                                mainActivity26.reg_val_l[0] = 1;
                                MainActivity.this.reg_val_h[0] = 0;
                                MainActivity.this.reg_val_l[1] = 60;
                                MainActivity.this.reg_val_h[1] = 0;
                                break;
                            } else {
                                MainActivity mainActivity27 = MainActivity.this;
                                mainActivity27.modbus_fun = 6;
                                mainActivity27.reg_adr = 24;
                                mainActivity27.reg_val_l[0] = 60;
                                MainActivity.this.reg_val_h[0] = 0;
                                break;
                            }
                        case 8:
                            if (MainActivity.this.old_ver == 0) {
                                MainActivity mainActivity28 = MainActivity.this;
                                mainActivity28.modbus_fun = 16;
                                mainActivity28.reg_adr = 37;
                                mainActivity28.reg_numb = 2;
                                mainActivity28.reg_val_l[0] = 0;
                                MainActivity.this.reg_val_h[0] = 0;
                                MainActivity.this.reg_val_h[1] = 0;
                                break;
                            } else {
                                MainActivity mainActivity29 = MainActivity.this;
                                mainActivity29.modbus_fun = 6;
                                mainActivity29.reg_adr = 34;
                                mainActivity29.reg_val_l[0] = MainActivity.this.reg_val_l[1];
                                MainActivity.this.reg_val_h[0] = 0;
                                break;
                            }
                        case 9:
                            if (MainActivity.this.old_ver == 0) {
                                MainActivity mainActivity30 = MainActivity.this;
                                mainActivity30.modbus_fun = 16;
                                mainActivity30.reg_adr = 37;
                                mainActivity30.reg_numb = 2;
                                mainActivity30.reg_val_l[0] = 1;
                                MainActivity.this.reg_val_h[0] = 0;
                                MainActivity.this.reg_val_h[1] = 0;
                                break;
                            } else {
                                MainActivity mainActivity31 = MainActivity.this;
                                mainActivity31.modbus_fun = 6;
                                mainActivity31.reg_adr = 24;
                                mainActivity31.reg_val_l[0] = MainActivity.this.reg_val_l[1];
                                MainActivity.this.reg_val_h[0] = 0;
                                break;
                            }
                        case 10:
                            MainActivity mainActivity32 = MainActivity.this;
                            mainActivity32.modbus_fun = 6;
                            mainActivity32.reg_adr = 50;
                            if (mainActivity32.komButton.isChecked()) {
                                MainActivity.this.reg_val_l[0] = 1;
                            } else {
                                MainActivity.this.reg_val_l[0] = 0;
                            }
                            MainActivity.this.reg_val_h[0] = 0;
                            break;
                    }
                }
                if (MainActivity.this.clientThread != null) {
                    MainActivity.this.clientThread.sendMessage(null);
                }
                MainActivity.this.vhandler.postDelayed(this, MainActivity.this.delay_time);
            }
        });
    }

    public void onClickAutoMode(View view) {
        this.autoButton.setChecked(true);
        this.manButton.setChecked(false);
        this.ventButton.setChecked(false);
        this.cfg_update = 5;
    }

    public void onClickManualMode(View view) {
        if (!this.ventButton.isChecked()) {
            this.autoButton.setChecked(false);
            this.manButton.setChecked(true);
            this.ventButton.setChecked(false);
            this.cfg_update = 6;
        }
    }

    public void onClickVentMode(View view) {
        if (this.old_ver == 0) {
            this.autoButton.setChecked(false);
            this.manButton.setChecked(false);
            this.ventButton.setChecked(true);
            this.ventSeekBar.setProgress(4);
            this.ventTxtView.setText("60min");
            this.cfg_update = 7;
            return;
        }
        ColoredToast(getString(R.string.str_Noactive), InputDeviceCompat.SOURCE_ANY, ViewCompat.MEASURED_STATE_MASK);
    }

    public void onConfigNetwork(View view) {
        this.intent = new Intent(this, (Class<?>) ConfigNetworkActivity.class);
        startActivity(this.intent);
    }

    public void onSetupControllerMode(View view) throws IOException {
        this.vhandler.removeCallbacksAndMessages(null);
        socket.close();
        this.intent = new Intent(this, (Class<?>) SetupControllerActivity.class);
        this.intent.setType("text/plain");
        this.intent.putExtra("ma_cfg_gwc_tryb", this.gwc_tryb);
        this.intent.putExtra("ma_cfg_gwc_comfort", this.gwc_comfort);
        this.intent.putExtra("ma_cfg_gwc_heat_tz", this.gwc_heat_tz);
        this.intent.putExtra("ma_cfg_gwc_cool_tz", this.gwc_cool_tz);
        this.intent.putExtra("ma_cfg_bps_tryb", this.bps_tryb);
        this.intent.putExtra("ma_cfg_bps_tz", this.bps_tz);
        this.intent.putExtra("ma_cfg_bps_tw", this.bps_tw);
        this.intent.putExtra("ma_cfg_bps_redtw", this.bps_redtw);
        this.intent.putExtra("ma_cfg_obroty", this.obr);
        this.intent.putExtra("ma_cfg_dpz", this.dpz);
        this.intent.putExtra("ma_cfg_dpg", this.dpg);
        this.intent.putExtra("ma_cfg_heat", this.heat);
        this.intent.putExtra("ma_cfg_we5", this.we5);
        this.intent.putExtra("ma_cfg_defr", this.defr);
        this.intent.putExtra("ma_cfg_day", this.dt_str);
        this.intent.putExtra("ma_cfg_hour", this.h_value);
        this.intent.putExtra("ma_cfg_harm", this.harm);
        startActivity(this.intent);
    }

    public void onKominekOpen(View view) {
        ColoredToast(getString(R.string.str_Noactive), InputDeviceCompat.SOURCE_ANY, ViewCompat.MEASURED_STATE_MASK);
    }

    class ClientThread implements Runnable {
        ClientThread() {
        }

        @Override // java.lang.Runnable
        public void run() throws IOException {
            try {
                InetAddress serverAddr = InetAddress.getByName(MainActivity.this.SERVER_IP);
                Socket unused = MainActivity.socket = new Socket(serverAddr.getHostAddress(), MainActivity.this.SERVERPORT);
                while (!Thread.currentThread().isInterrupted()) {
                    InputStream input = MainActivity.socket.getInputStream();
                    input.read(MainActivity.this.buf_in);
                }
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e12) {
                e12.printStackTrace();
            }
        }

        void sendMessage(String message) {
            new Thread(new Runnable() { // from class: pl.alres.controller_1.MainActivity.ClientThread.1
                @Override // java.lang.Runnable
                public void run() throws IOException {
                    int bufsize;
                    try {
                        if (MainActivity.socket != null) {
                            OutputStream out = MainActivity.socket.getOutputStream();
                            if (MainActivity.this.modbus_fun == 3 || MainActivity.this.modbus_fun == 6) {
                                bufsize = 12;
                            } else {
                                bufsize = (MainActivity.this.reg_numb * 2) + 13;
                            }
                            byte[] buf_out = new byte[bufsize];
                            if (MainActivity.this.modbus_fun == 16) {
                                buf_out[5] = (byte) ((MainActivity.this.reg_numb * 2) + 7);
                                buf_out[10] = 0;
                                buf_out[11] = (byte) MainActivity.this.reg_numb;
                                buf_out[12] = (byte) (MainActivity.this.reg_numb * 2);
                                for (int i = 0; i < MainActivity.this.reg_numb; i++) {
                                    buf_out[(i * 2) + 13] = (byte) MainActivity.this.reg_val_h[i];
                                    buf_out[(i * 2) + 14] = (byte) MainActivity.this.reg_val_l[i];
                                }
                            } else {
                                buf_out[5] = 6;
                                buf_out[10] = (byte) MainActivity.this.reg_val_h[0];
                                buf_out[11] = (byte) MainActivity.this.reg_val_l[0];
                            }
                            for (int i2 = 0; i2 < 5; i2++) {
                                buf_out[i2] = 0;
                            }
                            buf_out[6] = (byte) MainActivity.this.contr_id;
                            buf_out[7] = (byte) MainActivity.this.modbus_fun;
                            buf_out[8] = 0;
                            buf_out[9] = (byte) MainActivity.this.reg_adr;
                            out.write(buf_out);
                            out.flush();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        ClientThread clientThread = this.clientThread;
        if (clientThread != null) {
            clientThread.sendMessage("Disconnect");
            this.clientThread = null;
        }
        this.toast.cancel();
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
    }

    @Override // android.app.Activity
    protected void onRestart() {
        super.onRestart();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void ColoredToast(String str, int bgc, int fgc) {
        Toast toast = this.toast;
        if (toast != null) {
            toast.cancel();
        }
        this.toast = Toast.makeText(getApplicationContext(), str, 0);
        View view = this.toast.getView();
        view.getBackground().setColorFilter(bgc, PorterDuff.Mode.SRC_IN);
        TextView text = (TextView) view.findViewById(android.R.id.message);
        text.setTextColor(fgc);
        this.toast.show();
    }
}
