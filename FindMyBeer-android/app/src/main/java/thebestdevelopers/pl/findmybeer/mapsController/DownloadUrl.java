package thebestdevelopers.pl.findmybeer.mapsController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadUrl {

    public String readUrl(String mUrl) throws IOException {
        String data = "";
        InputStream inputSream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(mUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            inputSream = urlConnection.getInputStream();
            BufferedReader buf = new BufferedReader(new InputStreamReader(inputSream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = buf.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            inputSream.close();
            urlConnection.disconnect();
        }
        return data;
    }
}
