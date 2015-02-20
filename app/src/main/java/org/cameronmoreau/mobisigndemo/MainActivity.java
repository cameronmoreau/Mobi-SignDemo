package org.cameronmoreau.mobisigndemo;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.larswerkman.holocolorpicker.ColorPicker;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

/**
 * Created by Cameron on 2/19/15.
 */
public class MainActivity extends ActionBarActivity
    implements View.OnClickListener {

    public static final int CODE_COLOR = 1;

    private EditText etMessage;
    private Button bSubmit;
    private SeekBar sbFontSize;
    private ColorPicker colorPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "Pq7MJhCDgXpw9i4a3nXVnl3HREmFWypnbtSAIRS3",
                "XEGSXLy5qli63ljnNUFmJsQbjy1EtYBjEJBOPe8s");

        etMessage = (EditText) findViewById(R.id.et_message);
        bSubmit = (Button) findViewById(R.id.button_submit);
        sbFontSize = (SeekBar) findViewById(R.id.sb_font_size);
        colorPicker = (ColorPicker) findViewById(R.id.picker);

        colorPicker.setShowOldCenterColor(false);
        colorPicker.setNewCenterColor(Color.BLACK);

        bSubmit.setOnClickListener(this);
    }

    private boolean messageIsValid() {
        if(!etMessage.getText().toString().isEmpty())
            return true;
        return false;
    }

    private void saveMessage() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading to THE CLOUD!!");
        pd.setMessage("Please be patient");
        pd.setCancelable(false);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        ParseObject messageObject = new ParseObject("UploadedText");
        messageObject.put("message", etMessage.getText().toString());
        messageObject.put("animation", 0);
        messageObject.put("textColor", Integer.toHexString(colorPicker.getColor()));
        messageObject.put("fontSize", sbFontSize.getProgress());
        pd.show();
        messageObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                etMessage.setText("");
                etMessage.clearFocus();
                pd.dismiss();
                Toast.makeText(getBaseContext(), "Message sent", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_submit:
                if(messageIsValid()) saveMessage();
                else
                    Toast.makeText(this, "You need a message buddy", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
