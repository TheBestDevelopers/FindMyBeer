package thebestdevelopers.pl.findmybeer.ApiController.AsyncTasks;

import android.os.AsyncTask;
import java.io.IOException;

import thebestdevelopers.pl.findmybeer.ApiController.DownloadUrl.IDownloadUrl;

public class GetDataAsyncTask extends AsyncTask<Object, String, String> {

    public IAsyncResponse asyncsResponseDelegate = null;
    IDownloadUrl downloadUrlDelegate = null;

    private String apiData ="";
    private String url;

    Boolean timeout = false;

    public GetDataAsyncTask(IAsyncResponse _asyncsResponseDelegate, IDownloadUrl _downloadUrlDelegate) {
        asyncsResponseDelegate = _asyncsResponseDelegate;
        downloadUrlDelegate = _downloadUrlDelegate;
    }


    @Override
    protected String doInBackground(Object... objects) {
        url = (String)objects[0];
        try {
            apiData = downloadUrlDelegate.readUrl(url);
        } catch (java.net.SocketTimeoutException e) {
                timeout = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return apiData;
    }

    @Override
    protected void onPostExecute(String s){
        asyncsResponseDelegate.processFinish(s, timeout);
    }


}
