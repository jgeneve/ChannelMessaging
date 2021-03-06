package jordan.geneve.channelmessaging;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
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

import jordan.geneve.fragments.ChannelListFragment;
import jordan.geneve.fragments.MessageFragment;

public class ChannelListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_list);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ChannelListFragment fragA = (ChannelListFragment)getSupportFragmentManager().findFragmentById(R.id.frag_channel);
        MessageFragment fragB = (MessageFragment)getSupportFragmentManager().findFragmentById(R.id.frag_message);
        if(fragB == null|| !fragB.isInLayout()){
            Intent intent = new Intent(getApplicationContext(),Channel.class);
            intent.putExtra("idChan",fragA.listChan.get(position).getChannelID());
            startActivity(intent);
        } /*else {
            fragB.fillTextView(fragA.listItems[position]);
        }*/
    }
}

