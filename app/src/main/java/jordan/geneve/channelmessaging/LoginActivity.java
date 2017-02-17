package jordan.geneve.channelmessaging;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;

public class LoginActivity extends Activity implements View.OnClickListener, OnDownloadCompleteListener {


    private Button btnValider;
    private EditText etId;
    private EditText etMdp;

    public static final String PREFS_NAME = "MyPrefsFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnValider = (Button) findViewById(R.id.btnValider);
        btnValider.setOnClickListener(this);

        etId = (EditText) findViewById(R.id.editTextId);
        etMdp = (EditText) findViewById(R.id.editTextMdp);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnValider)
        {
            HashMap<String, String> loginParams = new HashMap<>();
            loginParams.put("username", etId.getText().toString());
            loginParams.put("password", etMdp.getText().toString());
            LoginAsync login = new LoginAsync(getApplicationContext(), loginParams);
            login.setOnDownloadCompleteListener(this);
            login.execute();
        }
    }

    @Override
    public void onDownloadComplete(String result, Integer requestCode) {
        if(result!=null)
        {
            Gson gson = new Gson();
            JsonAccess accessLogin = gson.fromJson(result, JsonAccess.class);

            if(accessLogin.getAccesstoken() != null)
            {
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("access", accessLogin.getAccesstoken());
                editor.commit();

                Intent newActivity = new Intent(getApplicationContext(), ChannelListActivity.class);
                startActivity(newActivity);

            }
            else
            {
                Toast.makeText(getApplicationContext(), accessLogin.getResponse(), Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Veuillez vérifier votre connexion internet", Toast.LENGTH_SHORT).show();
        }





    }





}
