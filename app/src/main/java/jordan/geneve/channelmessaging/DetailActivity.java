package jordan.geneve.channelmessaging;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        String texteAAfficher =
                getIntent().getStringExtra("monTextAAfficher");
        FragmentB fragB =
                (FragmentB)getSupportFragmentManager().findFragmentById(R.id.fr
                        agmentB_ID);
        fragB.fillTextView(texteAAfficher);
    }
}
