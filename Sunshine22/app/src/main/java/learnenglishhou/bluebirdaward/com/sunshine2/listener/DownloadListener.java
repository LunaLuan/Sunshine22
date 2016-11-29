package learnenglishhou.bluebirdaward.com.sunshine2.listener;

/**
 * Created by SVTest on 18/08/2016.
 */

public interface DownloadListener {

    public void onPreDownload();
    public void onDownloading();
    public void onDownloadSuccess();
    public void onDisconnect();
}
