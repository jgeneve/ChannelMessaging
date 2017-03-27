package jordan.geneve.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

import jordan.geneve.channelmessaging.ArrayAdapterChannel;
import jordan.geneve.channelmessaging.ArrayAdapterMessage;
import jordan.geneve.channelmessaging.Channel;
import jordan.geneve.channelmessaging.ChannelAsync;
import jordan.geneve.channelmessaging.ChannelListActivity;
import jordan.geneve.channelmessaging.JsonChannel;
import jordan.geneve.channelmessaging.JsonChannels;
import jordan.geneve.channelmessaging.JsonMessages;
import jordan.geneve.channelmessaging.LoginActivity;
import jordan.geneve.channelmessaging.MessageAsync;
import jordan.geneve.channelmessaging.OnDownloadCompleteListener;
import jordan.geneve.channelmessaging.R;
import jordan.geneve.channelmessaging.SendMessageAsync;

/**
 * Created by genevejo on 09/03/2017.
 */
public class MessageFragment extends Fragment implements View.OnClickListener, OnDownloadCompleteListener{

    private ListView listView;
    private EditText txtMessage;
    private Button btnSendMessage;
    private String accessToken;
    private String chanId;
    private ImageView imgView;
    private Handler handler;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 5;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_activity_list_channel,container);
        listView = (ListView) v.findViewById(R.id.listViewMessages);
        txtMessage = (EditText) v.findViewById(R.id.editTextMessage);
        btnSendMessage = (Button) v.findViewById(R.id.buttonSendMessage);
        btnSendMessage.setOnClickListener(this);
        imgView = (ImageView) v.findViewById(R.id.imageView);

        String ChannelId = getActivity().getIntent().getStringExtra("ChannelId");
        SharedPreferences settings = getActivity().getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        String token = settings.getString("access", "token");

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
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

                MessageAsync channel = new MessageAsync(getActivity().getApplicationContext(), accessChan);
                channel.setOnDownloadCompleteListener(MessageFragment.this);
                channel.execute();
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(r, 1000);

        return v;
    }

    @Override
    public void onClick(View v) {
        if(R.id.buttonSendMessage == v.getId())
        {
            HashMap<String, String> accessSendMessage = new HashMap<String, String>();
            accessSendMessage.put("accesstoken",accessToken);
            accessSendMessage.put("channelid", chanId);
            accessSendMessage.put("message", txtMessage.getText().toString());

            SendMessageAsync channel = new SendMessageAsync(getActivity().getApplicationContext(), accessSendMessage);
            channel.setOnDownloadCompleteListener(this);
            channel.execute();

            txtMessage.setText("");
        }
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //listView.setOnItemClickListener((AdapterView.OnItemClickListener) getActivity());
    }

    @Override
    public void onDownloadComplete(String result, Integer requestCode) {
        if(requestCode == 1)
        {
            Gson gson = new Gson();
            JsonMessages messages = gson.fromJson(result, JsonMessages.class);
            listView.setAdapter(new ArrayAdapterMessage(getActivity().getApplicationContext(), messages.getListMessage()));
        }
        else
        {
            Toast.makeText(getActivity().getApplicationContext(), "Message envoyé", Toast.LENGTH_SHORT).show();
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
