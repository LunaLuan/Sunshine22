package learnenglishhou.bluebirdaward.com.sunshine2.data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.format.Time;
import android.util.Log;

/**
 * Created by SVTest on 15/08/2016.
 */

public class WeatherProvider extends ContentProvider {

    public static final String authority="learnenglishhou.bluebirdaward.com.sunshine2";
    public static final Uri baseContentUri= Uri.parse("content://"+authority);

    private static final int city=1;
    private static final int weather=2;
    private static final int weather_with_date=3;
    private static final int weather_with_cityId_and_date=4;

    private static final UriMatcher uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(WeatherContract.authority, WeatherContract.pathCity, city);
        uriMatcher.addURI(WeatherContract.authority, WeatherContract.pathhWeather, weather);
        uriMatcher.addURI(WeatherContract.authority, WeatherContract.pathhWeather+"/*/*", weather_with_cityId_and_date);
    }

    private WeatherDbHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper=new WeatherDbHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        int match= uriMatcher.match(uri);
        switch (match) {
            case city:
                return ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+WeatherContract.authority+"/"+WeatherContract.pathCity;
            case weather:
                return ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+WeatherContract.authority+"/"+WeatherContract.pathhWeather;
        }
        return null;
    }


    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int match= uriMatcher.match(uri);
        Cursor cursorReturn = null;
        SQLiteDatabase db;
        db=dbHelper.getReadableDatabase();

        switch (match) {
            case city:
                cursorReturn= db.query(WeatherContract.CityEntry.tableName, projection, selection,
                        selectionArgs, null, null, sortOrder, null);
                break;
            case weather:
                cursorReturn=db.query(WeatherContract.WeatherEntry.tableName, projection, selection,
                        selectionArgs, null, null, sortOrder, null);
                break;

        }
        return cursorReturn;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int match= uriMatcher.match(uri);
        SQLiteDatabase db= dbHelper.getWritableDatabase();

        if(match==weather) {
            Time dayTime = new Time();
            dayTime.setToNow();
            int julianStartDay = Time.getJulianDay(System.currentTimeMillis(), dayTime.gmtoff);

            long dateTime;
            // Cheating to convert this to UTC time, which is what we want anyhow
            dateTime = dayTime.setJulianDay(julianStartDay-1);

            int deleteRows= db.delete(WeatherContract.WeatherEntry.tableName,
                    WeatherContract.WeatherEntry.date+"="+dateTime, null);
            return deleteRows;
        }

        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        return 0;
    }

    @Override
    public void shutdown() {
        super.shutdown();
        dbHelper.close();
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        int numInsertRows=0;

        int match = uriMatcher.match(uri);
        switch (match) {
            case weather:
                try {
                    db.beginTransaction();
                    for(ContentValues value: values) {
                        if(xuLyDuLieuMoi(value) != -1)
                            numInsertRows++;
                    }
                    db.setTransactionSuccessful();
                } catch (Exception e) {
                    Log.d("Lỗi thêm tập dữ liệu", e.toString());
                }
                finally {
                    db.endTransaction();
                }
                break;
            default:
                return super.bulkInsert(uri, values);
        }

        return numInsertRows;
    }

    private long xuLyDuLieuMoi(ContentValues value) {
        long dateTime= value.getAsLong(WeatherContract.WeatherEntry.date);
        Log.d("Time duoc them", dateTime+"");
        SQLiteDatabase db= dbHelper.getReadableDatabase();
        Cursor c= db.query(WeatherContract.WeatherEntry.tableName, null,
                WeatherContract.WeatherEntry.date+"="+dateTime, null, null, null, null, null);
        // Log.e("Da qua day", date);
        long _id=-1;
        db=dbHelper.getWritableDatabase();

        if(c.getCount()==0) {
            Log.d("-----Da them 1", dateTime+"");
            _id=db.insert(WeatherContract.WeatherEntry.tableName, null ,value);
        }
        else {
            Log.d("-----Da sua 1", dateTime+"");
            _id=db.update(WeatherContract.WeatherEntry.tableName, value,
                    WeatherContract.WeatherEntry.date+"="+dateTime, null);
        }

        return _id;
    }


}
