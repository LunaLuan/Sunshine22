package learnenglishhou.bluebirdaward.com.sunshine2.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by SVTest on 17/08/2016.
 */

public class SunshineSyncService extends Service {

    private static final Object sSyncAdapterLock=new Object();
    private static SunshineSyncAdapter sSunshineSyncAdapter = null;

    @Override
    public void onCreate() {
        super.onCreate();
        synchronized (sSyncAdapterLock) {
            if(sSunshineSyncAdapter==null) {
                sSunshineSyncAdapter=new SunshineSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return sSunshineSyncAdapter.getSyncAdapterBinder();
    }
}
