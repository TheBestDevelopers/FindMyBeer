package thebestdevelopers.pl.findmybeer.ApiController.DownloadUrl;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import thebestdevelopers.pl.findmybeer.SessionController;

public class DownloadUrlWithGetMethod implements IDownloadUrl {

    private Context applicationContext;
    private SessionController sessionController;

    public DownloadUrlWithGetMethod(Context _applicationContext) {
        applicationContext = _applicationContext;
        sessionController = new SessionController(applicationContext);
    }

    public String readUrl(String myUrl) throws Exception
    {
        String data = "";
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(myUrl);
            urlConnection=(HttpURLConnection) url.openConnection();
           // urlConnection.setRequestMethod("GET");
          //  urlConnection.setDoInput(true);
            urlConnection.setConnectTimeout(10000);
            //urlConnection.setInstanceFollowRedirects(false);
            // Set cookie as a request property to authorize
            urlConnection.setRequestProperty(sessionController.KEY_COOKIE, sessionController.getCookie());
            urlConnection.connect();

            try {
                inputStream = urlConnection.getInputStream();
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

                    StringBuffer sb = new StringBuffer();

                    String line = "";
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }

                    data = sb.toString();
                    br.close();
                } catch (IOException e) {
                    throw e;
                }
                finally {
                    inputStream.close();
                }
            } catch (IOException e) {
               throw e;
            }
            finally {
                urlConnection.disconnect();
            }

        } catch (MalformedURLException e) {
            throw e;
        } catch (java.net.SocketTimeoutException e) {
            throw e;
        } catch (IOException e) {
           throw e;
        } catch (Exception e) {
            throw e;
        }
        return data;
    }
}
