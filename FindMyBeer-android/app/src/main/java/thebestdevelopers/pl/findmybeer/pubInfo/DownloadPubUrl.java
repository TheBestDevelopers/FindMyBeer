package thebestdevelopers.pl.findmybeer.pubInfo;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import thebestdevelopers.pl.findmybeer.SessionController;

public class DownloadPubUrl {

    private SessionController sessionController;
    private Context context;
    String methode;

    // Application context needed to manage session and get cookies for authorization
    public DownloadPubUrl(Context _context, String meth) {
        context = _context;
        sessionController = new SessionController(context);
        methode = meth;
    }

    public String readUrl(String myUrl) throws Exception {
        String data = "";
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(myUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            // Set cookie as a request property to authorize
            urlConnection.setRequestProperty(sessionController.KEY_COOKIE, sessionController.getCookie());
            urlConnection.setRequestMethod(methode);
            urlConnection.connect();

            inputStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            br.close();

        } catch (MalformedURLException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        } finally {
            inputStream.close();
            urlConnection.disconnect();
        }
        Log.d("DownloadURL", "Returning data= " + data);

        return data;
    }
}
