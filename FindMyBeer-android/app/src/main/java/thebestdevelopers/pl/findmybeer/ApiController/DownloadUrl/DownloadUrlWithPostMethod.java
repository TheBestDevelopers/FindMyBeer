package thebestdevelopers.pl.findmybeer.ApiController.DownloadUrl;

import android.content.Context;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class DownloadUrlWithPostMethod implements IDownloadUrl {

    private String username, password;

    public DownloadUrlWithPostMethod(String _username, String _password) {
        username = _username;
        password = _password;
    }

    public String readUrl(String myUrl) throws IOException
    {
        String data = "";
        InputStream inputStream = null;


//        Map<String,String> params = new LinkedHashMap<>();
//        params.put("username", username);
//        params.put("password", password);
//        params.put("role", "client");
//
//        StringBuilder postData = new StringBuilder();
//        for (Map.Entry<String,String> param : params.entrySet()) {
//            if (postData.length() != 0) postData.append('&');
//            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
//            postData.append('=');
//            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
//        }
//        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        try {
            URL url = new URL(myUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("POST");
            String urlParameters = "username="+username+"&password="+password+"&role=client";
            urlConnection.setDoOutput(true);
            OutputStream os = urlConnection.getOutputStream();
            os.write(urlParameters.getBytes());
            os.flush();
            os.close();
            urlConnection.connect();
            int responseCode = urlConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) { //success
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // print result
                System.out.println(response.toString());
            } else {
                System.out.println("POST request not worked");
            }
            //  urlConnection.setRequestMethod("POST");
            //urlConnection.setDoInput(true);
            //  urlConnection.setDoOutput(true);
            //urlConnection.setConnectTimeout(10000);
            //   urlConnection.setInstanceFollowRedirects(false);
//            urlConnection.setRequestProperty("username", username);
//            urlConnection.setRequestProperty("password", password);
//            urlConnection.setRequestProperty("role", "client");


        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
