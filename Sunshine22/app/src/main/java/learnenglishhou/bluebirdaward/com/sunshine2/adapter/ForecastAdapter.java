package learnenglishhou.bluebirdaward.com.sunshine2.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import learnenglishhou.bluebirdaward.com.sunshine2.R;
import learnenglishhou.bluebirdaward.com.sunshine2.data.WeatherContract;
import learnenglishhou.bluebirdaward.com.sunshine2.utils.Utility;

/**
 * Created by SVTest on 16/08/2016.
 */

public class ForecastAdapter extends CursorAdapter {

    private Context context;
    private static LayoutInflater inflater=null;



    public ForecastAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        this.context=context;
        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(R.layout.list_item_weather, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        long dateTime= cursor.getLong(cursor.getColumnIndex(WeatherContract.WeatherEntry.date));
        String dateTimeString = Utility.getReadableDateString(dateTime);

        String descriptionString= cursor.getString(
                cursor.getColumnIndex(WeatherContract.WeatherEntry.description));

        int maxTemp= (int) (cursor.getDouble(
                cursor.getColumnIndex(WeatherContract.WeatherEntry.max_temp)));
        int minTemp= (int) (cursor.getDouble(
                cursor.getColumnIndex(WeatherContract.WeatherEntry.min_temp)));

        String iconString= cursor.getString(
                cursor.getColumnIndex(WeatherContract.WeatherEntry.icon));
        iconString= iconString.charAt(2)+""+iconString;


        TextView tvDateTime= (TextView) view.findViewById(R.id.tvDateTime);
        tvDateTime.setText(dateTimeString);

        TextView tvDescription= (TextView) view.findViewById(R.id.tvDescription);
        tvDescription.setText(descriptionString);

        TextView tvTemp= (TextView) view.findViewById(R.id.tvTemp);
        tvTemp.setText("Nhiệt độ: "+minTemp+" - "+maxTemp);

        ImageView ivIcon= (ImageView) view.findViewById(R.id.ivIcon);
        int imageIconId= context.getResources().
                getIdentifier(iconString, "drawable", context.getPackageName());
        ivIcon.setImageResource(imageIconId);


    }
}
