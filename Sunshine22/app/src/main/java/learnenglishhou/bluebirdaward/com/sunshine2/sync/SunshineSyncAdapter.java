package learnenglishhou.bluebirdaward.com.sunshine2.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import learnenglishhou.bluebirdaward.com.sunshine2.R;
import learnenglishhou.bluebirdaward.com.sunshine2.utils.FetchWeatherTask;
import learnenglishhou.bluebirdaward.com.sunshine2.utils.Utility;

/**
 * Created by SVTest on 17/08/2016.
 */

public class SunshineSyncAdapter extends AbstractThreadedSyncAdapter {


    public SunshineSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }


    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult sncResult) {
        Log.d("   Thoi gian update dang update", System.currentTimeMillis()+"");
        FetchWeatherTask task=new FetchWeatherTask(getContext());
        task.execute(Utility.getCityIdPreferences(getContext()), Utility.getMetric(getContext()));
    }

    public static void syncImmediately(Account newAccount,Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(newAccount,
                context.getString(R.string.content_authority), bundle);


    }

    public static Account getSyncAccount(Context context) {
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(
                context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

        // If the password doesn't exist, the account doesn't exist
        if ( null == accountManager.getPassword(newAccount) ) {

            if (!accountManager.addAccountExplicitly(newAccount, "Sunshine2", null)) {
                return null;
            }


        }
        onAccountCreated(newAccount, context);
        return newAccount;
    }


    public static void configurePeriodicSync(Account newAccount, Context context) {
        String authority = context.getString(R.string.content_authority);
        Log.d("Thoi gian update:", Utility.getTimeUpdate(context)+"");

        int timeUpdate= Utility.getTimeUpdate(context);
        if(timeUpdate== -1) {
            ContentResolver.setSyncAutomatically(newAccount,
                    context.getString(R.string.content_authority), false);
        }
        else {
            ContentResolver.addPeriodicSync(newAccount,
                    authority, new Bundle(), timeUpdate);
            ContentResolver.setSyncAutomatically(newAccount,
                    context.getString(R.string.content_authority), true);
        }

    }


    private static void onAccountCreated(Account newAccount, Context context) {
        SunshineSyncAdapter.configurePeriodicSync(newAccount, context);
        syncImmediately(newAccount, context);
    }

    public static void initializeSyncAdapter(Context context) {
        getSyncAccount(context);
    }



}
