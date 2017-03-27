package jordan.geneve.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

import jordan.geneve.channelmessaging.ArrayAdapterChannel;
import jordan.geneve.channelmessaging.Channel;
import jordan.geneve.channelmessaging.ChannelAsync;
import jordan.geneve.channelmessaging.ChannelListActivity;
import jordan.geneve.channelmessaging.JsonChannel;
import jordan.geneve.channelmessaging.JsonChannels;
import jordan.geneve.channelmessaging.LoginActivity;
import jordan.geneve.channelmessaging.OnDownloadCompleteListener;
import jordan.geneve.channelmessaging.R;

/**
 * Created by genevejo on 27/02/2017.
 */
public class ChannelListFragment extends Fragment implements OnDownloadCompleteListener, AdapterView.OnItemClickListener{

    private ListView listViewFragment;
    public List<JsonChannel> listChan;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_activity_list_channel,container);
        listViewFragment = (ListView)v.findViewById(R.id.listViewChannelFrag);


        SharedPreferences settings = getActivity().getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        String access = settings.getString("access", "token");
        HashMap<String, String> token = new HashMap<>();
        token.put("accesstoken", access);

        ChannelAsync channel = new ChannelAsync(getActivity(), token);
        channel.setOnDownloadCompleteListener(this,1);
        channel.execute();

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listViewFragment.setOnItemClickListener((AdapterView.OnItemClickListener) getActivity());
    }

    @Override
    public void onDownloadComplete(String result, Integer requestCode) {
        Gson gson = new Gson();
        JsonChannels accessChannels = gson.fromJson(result, JsonChannels.class);

        accessChannels.toString();
        listChan = accessChannels.getChannels();

        listViewFragment.setAdapter(new ArrayAdapterChannel( getActivity(), accessChannels.getChannels()));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent((ChannelListActivity)getActivity(), Channel.class);
        i.putExtra("ChannelId" , listChan.get(position).getChannelID());
        startActivity(i);
    }

}
