package thebestdevelopers.pl.findmybeer.ApiController;

import android.content.Context;

import java.util.ArrayList;

import thebestdevelopers.pl.findmybeer.R;

public class HttpRequests {
    private String apiUrl;

    public HttpRequests(Context _context) {
        apiUrl = _context.getResources().getString(R.string.databaseIP);
    }

    public String getNearbyPubsUrl(Double longitude, Double latitude) {
        StringBuilder getPubsUrl = new StringBuilder(apiUrl);
        getPubsUrl.append("/api/pubs/getNearestPubs?longitude=");
        getPubsUrl.append(longitude);
        getPubsUrl.append("&latitude=");
        getPubsUrl.append(latitude);
        return getPubsUrl.toString();
    }

    public String getPubsWithConveniencesUrl(Double longitude, Double latitude, ArrayList<String> conveniences) {
        StringBuilder url = new StringBuilder(apiUrl);
        url.append("/api/pubs/getPubsWithConveniences?longitude=");
        url.append(longitude);
        url.append("&latitude=");
        url.append(latitude);
        url.append("&conveniences=");
        for (String conv : conveniences) {
            url.append(conv.toUpperCase());
            url.append(",");
        }
        url.deleteCharAt(url.length()-1);

        return url.toString().replace(" ", "%20");
    }


    public String getConveniencesUrl() {
        StringBuilder url = new StringBuilder(apiUrl);
        url.append("/api/conveniences/getConveniences");
        return url.toString();
    }

}
