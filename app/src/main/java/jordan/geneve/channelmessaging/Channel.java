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

public class Channel extends AppCompatActivity {

    private ListView listView;
    private EditText txtMessage;
    private Button btnSendMessage;
    private String accessToken;
    private String chanId;
    private ImageView imgView;
    private Handler handler;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 5;

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
