package jordan.geneve.channelmessaging;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by genevejo on 06/02/2017.
 */
public class ImageAsync extends AsyncTask<Void,Void, Void> {

    private Context myContext;
    private String fileUrl;
    private String fileName;
    private ImageView imgView;
    private String Image;

    public ImageAsync(Context myContext, String myUrlFile, String myFileName, ImageView myImageView)
    {
        this.myContext = myContext;
        this.fileUrl = myUrlFile;
        this.fileName = myFileName;
        this.imgView = myImageView;

    }

    @Override
    protected Void doInBackground(Void... arg0)
    {
        downloadFromUrl(fileUrl, fileName);
        return null;
    }


    public void downloadFromUrl(String fileURL, String fileName) {
        try {
            URL url = new URL( fileURL);
            File file = new File(fileName);
            file.createNewFile();
            /* Open a connection to that URL. */
            URLConnection ucon = url.openConnection();
            /* Define InputStreams to read from the URLConnection.*/
            InputStream is = ucon.getInputStream();
            /* Read bytes to the Buffer until there is nothing more to read(-1) and write on the fly in the file.*/
            FileOutputStream fos = new FileOutputStream(file);
            final int BUFFER_SIZE = 23 * 1024;
            BufferedInputStream bis = new BufferedInputStream(is, BUFFER_SIZE);
            byte[] baf = new byte[BUFFER_SIZE];
            int actual = 0;
            while (actual != -1) {
                fos.write(baf, 0, actual);
                actual = bis.read(baf, 0, BUFFER_SIZE);
            }
            imgView.setImageBitmap(BitmapFactory.decodeFile(fileName+fileURL.substring(fileURL.lastIndexOf("/"))));
            fos.close();
        } catch (IOException e) {
            //TODO HANDLER
        }
    }
}
