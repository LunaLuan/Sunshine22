package learnenglishhou.bluebirdaward.com.sunshine2.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import learnenglishhou.bluebirdaward.com.sunshine2.R;
import learnenglishhou.bluebirdaward.com.sunshine2.data.WeatherContract;
import learnenglishhou.bluebirdaward.com.sunshine2.utils.Utility;

/**
 * Created by SVTest on 22/08/2016.
 */

public class ForecastRecyclerAdapter extends
        RecyclerView.Adapter<ForecastRecyclerAdapter.ForecastHolder> {

    public class ForecastHolder extends RecyclerView.ViewHolder {

        private TextView tvDateTime;
        private TextView tvDescription;
        private TextView tvTemp;
        private ImageView ivIcon;

        public ForecastHolder(View itemView) {
            super(itemView);

            tvDateTime=(TextView) itemView.findViewById(R.id.tvDateTime);
            tvDescription=(TextView) itemView.findViewById(R.id.tvDescription);
            tvTemp=(TextView) itemView.findViewById(R.id.tvTemp);
            ivIcon=(ImageView) itemView.findViewById(R.id.ivIcon);
        }
    }

    private Cursor data;
    private Context context;

    public ForecastRecyclerAdapter(Context context, Cursor data) {
        this.context= context;
        this.data=data;
    }

    @Override
    public ForecastHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_item_weather, parent, false);
        return new ForecastHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ForecastHolder holder, int position) {
        data.moveToPosition(position);

        long dateTime= data.getLong(data.getColumnIndex(WeatherContract.WeatherEntry.date));
        String dateTimeString = Utility.getReadableDateString(dateTime);

        String descriptionString= data.getString(
                data.getColumnIndex(WeatherContract.WeatherEntry.description));

        int maxTemp= (int) (data.getDouble(
                data.getColumnIndex(WeatherContract.WeatherEntry.max_temp)));
        int minTemp= (int) (data.getDouble(
                data.getColumnIndex(WeatherContract.WeatherEntry.min_temp)));

        String iconString= data.getString(
                data.getColumnIndex(WeatherContract.WeatherEntry.icon));
        iconString= iconString.charAt(2)+""+iconString;
        int imageIconId= context.getResources().
                getIdentifier(iconString, "drawable", context.getPackageName());

        holder.tvDateTime.setText(dateTimeString);
        holder.tvDescription.setText(descriptionString);
        holder.tvTemp.setText("Nhiệt độ: "+minTemp+"°C - "+maxTemp+"°C");
        holder.ivIcon.setImageResource(imageIconId);
    }

    @Override
    public int getItemCount() {
        return data.getCount();
    }



}
