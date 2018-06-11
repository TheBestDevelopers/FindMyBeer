package thebestdevelopers.pl.findmybeer.pubListController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadNearbyPubsUrl {
    public String readUrl(String myUrl) throws IOException
    {
        String data = "";
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(myUrl);
            urlConnection=(HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("longitude", "18.976369");
            urlConnection.setRequestProperty("latitude", "50.2301888");
            urlConnection.setConnectTimeout(10000);
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
                    e.printStackTrace();
                }
                finally {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                urlConnection.disconnect();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (java.net.SocketTimeoutException e) {
                throw e;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
