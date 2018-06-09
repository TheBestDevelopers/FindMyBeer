package thebestdevelopers.pl.findmybeer.menuController;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import thebestdevelopers.pl.findmybeer.R;

public class GetJsonResult extends AsyncTask<Object, String, String> {

    private String googlePlacesData;
    private String url;

    private String menu, size;
    public ArrayList<MenuData> mMenuList;


    WeakReference<Activity> mWeakActivity;

    public GetJsonResult(Activity activity) {
        mWeakActivity = new WeakReference<Activity>(activity);
    }

    @Override
    protected String doInBackground(Object... objects) {
        url = (String)objects[0];
        DownloadMenuUrl downloadUrl = new DownloadMenuUrl();
        try {
            googlePlacesData = downloadUrl.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s){
        HashMap<String, String> menuList;
        DataMenuParser parser = new DataMenuParser();
        menuList = parser.parse(s);
        Log.d("placedata","called parse method");
        showMenu(menuList);
        Activity activity = mWeakActivity.get();
        if (activity != null) {
            RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.menu_list);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(activity));
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            recyclerView.setAdapter(new MenuRecyclerViewAdapter(mMenuList, recyclerView));
        }
    }

    //{"result":{"menu": "beer1:7 beer2:6 beer3:8", "size" : "3"}}
    private void showMenu(HashMap<String, String> googlePlace)
    {
        menu = googlePlace.get("menu");
        size = googlePlace.get("size");
        mMenuList = new ArrayList<>();
        String type, price;

        //wyodrebnienie każdego produktu z otrzymanego menu
        for (int temp = 0; temp < Integer.parseInt(size) - 1; temp++) {
            int i = 0;
            int space = menu.indexOf(' ');
            String str = menu.substring(0,space);
            menu = menu.substring(space+1, menu.length());
            int loc = str.indexOf(':');
            type = str.substring(0,loc);
            price = str.substring(loc+1,str.length());
            mMenuList.add(new MenuData(type, price));
        }
            int loc = menu.indexOf(':');
            type = menu.substring(0, loc);
            price = menu.substring(loc + 1, menu.length());
            mMenuList.add(new MenuData(type, price));
    }
}
