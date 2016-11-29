package learnenglishhou.bluebirdaward.com.sunshine2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import learnenglishhou.bluebirdaward.com.sunshine2.data.WeatherContract;

public class DetailActivity extends AppCompatActivity {

    private Cursor mForecast;

    private ShareActionProvider mShareActionProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent=getIntent();
        mForecast=(Cursor) intent.getSerializableExtra(MainActivity.Extra_Forecast);

        TextView tvForecast=(TextView) findViewById(R.id.tvForecast);
        int desColumn= mForecast.getColumnIndex(WeatherContract.WeatherEntry.description);
        tvForecast.setText(mForecast.getString(desColumn));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId= item.getItemId();
        if(itemId== R.id.action_share) {
            shareIntent();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void shareIntent(){
        Intent shIntent=new Intent(Intent.ACTION_SEND);
        shIntent.setType("text/plain");

        int desColumn= mForecast.getColumnIndex(WeatherContract.WeatherEntry.description);

        startActivity(Intent.createChooser(shIntent, "Share via"));
    }
}
