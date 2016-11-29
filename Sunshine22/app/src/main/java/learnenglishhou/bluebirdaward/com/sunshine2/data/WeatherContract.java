package learnenglishhou.bluebirdaward.com.sunshine2.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by SVTest on 15/08/2016.
 */

public class WeatherContract {

    public static final String authority="learnenglishhou.bluebirdaward.com.sunshine2";
    public static final Uri baseContentUri=Uri.parse("content://"+authority);

    public static final String pathhWeather="weather";
    public static final String pathCity="city";

    public static class CityEntry implements BaseColumns {

        public static final String tableName="City";
        public static final String _id = "_id";
        public static final String city_weather_id = "city_weather_id";
        public static final String name = "name";
        public static final String lon = "lon";
        public static final String lat = "lat";
        public static final String country = "country";
        public static final String population = "population";

    }

    public static class WeatherEntry implements BaseColumns {

        public static final String tableName="Weather";
        public static final String _id="_id";
        public static final String city_id="city_id";
        public static final String date="date";
        public static final String description="description";
        public static final String max_temp="max_temp";
        public static final String min_temp="min_temp";
        public static final String humidity="humidity";
        public static final String pressure="pressure";
        public static final String speech="speech";
        public static final String deg="deg";
        public static final String icon="icon";

    }

}
