package jordan.geneve.channelmessaging;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ChannelListActivity extends AppCompatActivity implements OnDownloadCompleteListener, AdapterView.OnItemClickListener {

    private ListView listView;
    private List<JsonChannel> listChan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        String access = settings.getString("access", "token");
        HashMap<String, String> token = new HashMap<>();
        token.put("accesstoken", access);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_list);
        listView = (ListView) findViewById(R.id.listViewChannel);
        ChannelAsync channel = new ChannelAsync(getApplicationContext(), token);
        channel.setOnDownloadCompleteListener(this, 1);
        channel.execute();
    }


    @Override
    public void onDownloadComplete(String result, Integer requestCode) {
         Gson gson = new Gson();
        JsonChannels accessChannels = gson.fromJson(result, JsonChannels.class);

        accessChannels.toString();
        listChan = accessChannels.getChannels();

        listView.setAdapter(new ArrayAdapterChannel(getApplicationContext(), accessChannels.getChannels()));
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(getApplicationContext(), Channel.class);
        i.putExtra("ChannelId" , listChan.get(position).getChannelID());
        startActivity(i);
    }
}

