package pl.alres.controller_1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/* loaded from: /Users/patrykgalazka/Downloads/msr23nikol/classes.dex */
public class ConfigNetworkActivity extends AppCompatActivity {
    CheckBox activeConstIP;
    CheckBox activeLocalIP;
    EditText editTextConst;
    EditText editTextLoc;
    EditText editTextPass;
    Boolean flaga_local_ip = false;
    Boolean flaga_const_ip = false;
    String local_ip_str = BuildConfig.FLAVOR;
    String const_ip_str = BuildConfig.FLAVOR;
    String msr_id_str = BuildConfig.FLAVOR;

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_network);
        this.editTextLoc = (EditText) findViewById(R.id.txtLocalIP);
        this.editTextConst = (EditText) findViewById(R.id.txtConstIP);
        this.editTextPass = (EditText) findViewById(R.id.txtPassMSR);
        this.activeLocalIP = (CheckBox) findViewById(R.id.chkBoxLocal);
        this.activeLocalIP.setChecked(false);
        this.activeConstIP = (CheckBox) findViewById(R.id.chkBoxConst);
        this.activeConstIP.setChecked(false);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        pref.edit();
        this.local_ip_str = pref.getString("tmp_network_ip_local", BuildConfig.FLAVOR);
        this.editTextLoc.setText(this.local_ip_str);
        this.const_ip_str = pref.getString("tmp_network_ip_const", BuildConfig.FLAVOR);
        this.editTextConst.setText(this.const_ip_str);
        this.msr_id_str = pref.getString("tmp_msr_id_const", BuildConfig.FLAVOR);
        this.editTextPass.setText(this.msr_id_str);
    }

    public void onCheckLocal(View view) {
        this.activeLocalIP.setChecked(true);
        this.activeConstIP.setChecked(false);
        this.flaga_local_ip = true;
        this.flaga_const_ip = false;
    }

    public void onCheckConst(View view) {
        this.activeConstIP.setChecked(true);
        this.activeLocalIP.setChecked(false);
        this.flaga_const_ip = true;
        this.flaga_local_ip = false;
    }

    public void ReturnToMain(View view) {
        int msr_id = 0;
        String str_local_ip = this.editTextLoc.getText().toString();
        String str_const_ip = this.editTextConst.getText().toString();
        String str_pass = this.editTextPass.getText().toString();
        if ((str_local_ip.equals(BuildConfig.FLAVOR) && str_const_ip.equals(BuildConfig.FLAVOR)) || str_pass.equals(BuildConfig.FLAVOR)) {
            Toast.makeText(getApplicationContext(), "Proszę uzupełnić dane", 0).show();
            return;
        }
        if (!this.activeLocalIP.isChecked() && !this.activeConstIP.isChecked()) {
            Toast.makeText(getApplicationContext(), "Proszę wybrać IP", 0).show();
            return;
        }
        if (this.activeLocalIP.isChecked() && this.activeConstIP.isChecked()) {
            Toast.makeText(getApplicationContext(), "Niepoprawny wybór IP", 0).show();
            return;
        }
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("tmp_network_ip_local", str_local_ip);
        editor.putString("tmp_network_ip_const", str_const_ip);
        editor.putString("tmp_msr_id_const", str_pass);
        char[] letters = str_pass.toCharArray();
        for (char c : letters) {
            msr_id += c;
        }
        int msr_id2 = msr_id / 6;
        Intent intent = new Intent(this, (Class<?>) MainActivity.class);
        if (this.flaga_local_ip.booleanValue()) {
            editor.putString("network_ip", str_local_ip);
        } else if (this.flaga_const_ip.booleanValue()) {
            int idx = str_const_ip.indexOf(":") + 1;
            if (idx == str_const_ip.length()) {
                Toast.makeText(getApplicationContext(), "Proszę wpisać numer portu", 0).show();
                return;
            }
            editor.putString("network_ip", str_const_ip);
        }
        editor.putInt("msr_password", msr_id2);
        editor.putInt("config_ip", 6524);
        editor.apply();
        startActivity(intent);
    }
}
