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

import jordan.geneve.fragments.MessageFragment;

public class Channel extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);
        String texteAAfficher =
                getIntent().getStringExtra("monTextAAfficher");
        MessageFragment fragB =
                (MessageFragment) getSupportFragmentManager().findFragmentById(R.id.frag_message);
        //fragB.fillTextView(texteAAfficher);
    }




}
