package jordan.geneve.channelmessaging;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by genevejo on 30/01/2017.
 */
public class ArrayAdapterMessage extends ArrayAdapter<JsonMessage> {
    private final Context context;
    private final ArrayList<JsonMessage> values;

    public ArrayAdapterMessage(Context context, ArrayList<JsonMessage> values) {
        super(context, R.layout.message_layout, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.message_layout, parent, false);

        TextView txtViewMessage = (TextView) rowView.findViewById(R.id.textViewMessage);
        TextView txtViewDate = (TextView) rowView.findViewById(R.id.textViewDate);
        ImageView imgView = (ImageView) rowView.findViewById(R.id.imageView);


        File dir = new File (context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)+"/imagesPersonnes/");
        if(!dir.exists())
        {
            dir.mkdirs();
        }

        int lastIndex = getItem(position).getImageUrl().lastIndexOf("/");
        File image = new File(dir, getItem(position).getImageUrl().substring(lastIndex));

        if(!image.exists())
        {
            ImageAsync i = new ImageAsync(getContext(), getItem(position).getImageUrl(), image.toString(), imgView);
            i.execute();
        }
        else
        {
            imgView.setImageBitmap(BitmapFactory.decodeFile(image.toString()));
        }


        txtViewMessage.setText(getItem(position).getUsername() + " : " + getItem(position).getMessage());
        txtViewDate.setText(getItem(position).getDate());


        return rowView;
    }

}
