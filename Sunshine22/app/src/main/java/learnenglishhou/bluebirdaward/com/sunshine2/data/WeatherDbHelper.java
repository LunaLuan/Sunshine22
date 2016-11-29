package learnenglishhou.bluebirdaward.com.sunshine2.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by SVTest on 15/08/2016.
 */

public class WeatherDbHelper extends SQLiteOpenHelper {

    public static final String name="Weather.s3db";
    public static String DB_PATH;
    private Context context;

    public WeatherDbHelper(Context context) {
        super(context, name, null, 3);
        if(Build.VERSION.SDK_INT>=17)
            DB_PATH=context.getApplicationInfo().dataDir+"/databases/";
        else
            DB_PATH="/data/data/"+context.getPackageName()+"/databases/";
        this.context=context;
        createDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if(!checkDatabases())
            createDatabase();
    }

    private boolean checkDatabases() {
        File f=new File(DB_PATH+name);
        return f.exists();
    }

    private void copyDatabase() throws IOException {
        InputStream inputStream=context.getAssets().open(name);
        String outFileName=DB_PATH+name;
        OutputStream outputStream=new FileOutputStream(outFileName);

        byte mBuffer[]=new byte[1024];
        int mLength;
        while ((mLength=inputStream.read(mBuffer))>0) {
            outputStream.write(mBuffer, 0, mLength);
        }

        outputStream.flush();
        outputStream.close();
        inputStream.close();

    }

    private void createDatabase() {
        if(!checkDatabases()) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDatabase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
