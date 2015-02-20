package org.cameronmoreau.mobisigndemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class LoginActivity extends Activity implements View.OnClickListener {

    private Button bLogin;
    private EditText etUser, etPassword;
    private TextView tvFlashMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bLogin = (Button) findViewById(R.id.button_login);
        tvFlashMessage = (TextView) findViewById(R.id.tv_flash_message);
        etUser = (EditText) findViewById(R.id.et_user);
        etPassword = (EditText) findViewById(R.id.et_password);

        bLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_login:
                if(loginIsValid()) {
                    Intent i = new Intent(this, MainActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    tvFlashMessage.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    boolean loginIsValid() {
        if(etUser.getText().toString().equals("user") && etPassword.getText().toString().equals("swag"))
            return true;

        return false;
    }
}
