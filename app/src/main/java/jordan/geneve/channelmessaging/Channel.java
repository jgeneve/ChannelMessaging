package jordan.geneve.channelmessaging;

import android.Manifest;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.Image;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import com.google.gson.Gson;

import java.util.HashMap;

public class Channel extends AppCompatActivity  implements OnDownloadCompleteListener, View.OnClickListener {

    private ListView listView;
    private EditText txtMessage;
    private Button btnSendMessage;
    private String accessToken;
    private String chanId;
    private ImageView imgView;
    private Handler handler;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);

        String ChannelId = getIntent().getStringExtra("ChannelId");
        SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        String token = settings.getString("access", "token");

        if (ContextCompat.checkSelfPermission(Channel.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(Channel.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(Channel.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        }


        accessToken = token;
        chanId = ChannelId;


        handler = new Handler();
        Runnable r = new Runnable() {
            public void run() {
                HashMap<String, String> accessChan = new HashMap<>();
                accessChan.put("accesstoken", accessToken);
                accessChan.put("channelid",chanId);

                MessageAsync channel = new MessageAsync(getApplicationContext(), accessChan);
                channel.setOnDownloadCompleteListener(Channel.this);
                channel.execute();
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(r, 1000);


        listView = (ListView) findViewById(R.id.listViewMessages);
        txtMessage = (EditText) findViewById(R.id.editTextMessage);
        btnSendMessage = (Button) findViewById(R.id.buttonSendMessage);
        btnSendMessage.setOnClickListener(this);
        imgView = (ImageView) findViewById(R.id.imageView);
    }

    @Override
    public void onDownloadComplete(String result, Integer requestCode) {

        if(requestCode == 1)
        {
            Gson gson = new Gson();
            JsonMessages messages = gson.fromJson(result, JsonMessages.class);
            listView.setAdapter(new ArrayAdapterMessage(getApplicationContext(), messages.getListMessage()));
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Message envoyé", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if(R.id.buttonSendMessage == v.getId())
        {
            HashMap<String, String> accessSendMessage = new HashMap<String, String>();
            accessSendMessage.put("accesstoken",accessToken);
            accessSendMessage.put("channelid", chanId);
            accessSendMessage.put("message", txtMessage.getText().toString());

            SendMessageAsync channel = new SendMessageAsync(getApplicationContext(), accessSendMessage);
            channel.setOnDownloadCompleteListener(this);
            channel.execute();

            txtMessage.setText("");
        }
    }

        @Override
        public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
        {
            switch (requestCode) {
                case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE :
                {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    {

                    } else {

                    }
                    return;
                }
            }
        }


}
