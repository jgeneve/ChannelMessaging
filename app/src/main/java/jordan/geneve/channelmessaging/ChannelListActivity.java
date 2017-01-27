package jordan.geneve.channelmessaging;

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

public class ChannelListActivity extends AppCompatActivity implements View.OnClickListener, OnDownloadCompleteListener, AdapterView.OnItemClickListener {

    private ListView listView;

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
        channel.setOnDownloadCompleteListener(this);
        channel.execute();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDownloadComplete(String result) {
        Gson gson = new Gson();
        JsonChannels accessChannels = gson.fromJson(result, JsonChannels.class);

        listView.setAdapter(new ArrayAdapterChannel(getApplicationContext(), accessChannels.getChannels()));



    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
