package jordan.geneve.channelmessaging;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by genevejo on 27/01/2017.
 */
public class ArrayAdapterChannel  extends ArrayAdapter<JsonChannel> {
    private final Context context;
    private final List<JsonChannel> values;

    public ArrayAdapterChannel(Context context, List<JsonChannel> values) {
        super(context, R.layout.channel_layout, values);
        this.context = context;
        this.values = values;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.channel_layout, parent, false);

        TextView textViewChannel = (TextView) rowView.findViewById(R.id.textViewChannel);
        TextView textViewUsers = (TextView) rowView.findViewById(R.id.textViewUser);

        textViewChannel.setText(textViewChannel.getText() + " " + getItem(position).getChannelID());
        textViewUsers.setText(textViewUsers.getText() + " " + getItem(position).getConnectedusers());

        return rowView;
    }
}
