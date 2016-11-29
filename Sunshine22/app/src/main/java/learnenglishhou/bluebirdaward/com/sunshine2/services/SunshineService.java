package learnenglishhou.bluebirdaward.com.sunshine2.services;

import android.app.IntentService;
import android.content.Intent;


public class SunshineService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public SunshineService(String name) {
        super("SunshineService");

    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

}
