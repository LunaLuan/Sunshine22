package learnenglishhou.bluebirdaward.com.sunshine2;

import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import learnenglishhou.bluebirdaward.com.sunshine2.adapter.ForecastRecyclerAdapter;
import learnenglishhou.bluebirdaward.com.sunshine2.data.WeatherContract;
import learnenglishhou.bluebirdaward.com.sunshine2.data.WeatherProvider;
import learnenglishhou.bluebirdaward.com.sunshine2.sync.SunshineSyncAdapter;


public class MainActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<Cursor> {

    private Uri uri;

    private int loaderId=1010;

    public static final String Extra_Position="Extra_Position";
    public static final String Extra_Forecast="Extra_Forecast";

    private ArrayList<String> data;


    private RecyclerView lvForecast;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        if(toolbar!=null) {
            setSupportActionBar(toolbar);
        }

        WeatherProvider provider=new WeatherProvider();

        uri=Uri.parse("content://"+WeatherContract.authority+"/"+ WeatherContract.pathhWeather);
        lvForecast=(RecyclerView) findViewById(R.id.lvForecast);
        lvForecast.setHasFixedSize(true);

        mLayoutManager=new LinearLayoutManager(this);
        lvForecast.setLayoutManager(mLayoutManager);




    }


    @Override
    protected void onStart() {
        super.onStart();
        // updateWeather();
        SunshineSyncAdapter.initializeSyncAdapter(this);
        getLoaderManager().initLoader(loaderId, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId= item.getItemId();
        if(itemId == R.id.action_settinngs) {
            Intent intent=new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if(id==loaderId) {
            CursorLoader cursorLoader=new CursorLoader(this, uri, null, null, null, WeatherContract.WeatherEntry._id+" ASC");
            return cursorLoader;
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter=new ForecastRecyclerAdapter(this, data);
        lvForecast.setAdapter(adapter);
        if(data.getCount()==0) {
            Toast.makeText(this, "Vui lòng bật mạng và cập nhật dữ liệu...", Toast.LENGTH_LONG).show();
        }
        data.setNotificationUri(getContentResolver(), uri);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // getLoaderManager().restartLoader(loaderId, null, this);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // adapter.swapCursor(null);
    }



}
