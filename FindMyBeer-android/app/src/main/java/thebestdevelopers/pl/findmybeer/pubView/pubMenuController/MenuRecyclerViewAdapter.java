package thebestdevelopers.pl.findmybeer.pubView.pubMenuController;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import thebestdevelopers.pl.findmybeer.R;

public class MenuRecyclerViewAdapter extends RecyclerView.Adapter {

    private ArrayList<MenuData> mMenuList;
    private RecyclerView mRecyclerView;

    // implementacja wzorca ViewHolder
    // każdy obiekt tej klasy przechowuje odniesienie do layoutu elementu listy
    // dzięki temu wywołujemy findViewById() tylko raz dla każdego elementu
    private class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mName;
        public TextView mPrice;

        public MyViewHolder(View v) {
            super(v);
            mName = v.findViewById(R.id.fav_name);
            mPrice = v.findViewById(R.id.fav_address);
        }
    }

    // konstruktor adaptera
    public MenuRecyclerViewAdapter(ArrayList<MenuData> menuList, RecyclerView recyclerView) {
        mMenuList = menuList;
        mRecyclerView = recyclerView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        // tworzymy layout artykułu oraz obiekt ViewHoldera
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fav_layout, viewGroup, false);
        // tworzymy i zwracamy obiekt ViewHolder
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        // uzupełniamy layout artykułu
        MenuData data = mMenuList.get(i);
        String str = data.getName();
        str = str.replace('_', ' ');
        ((MyViewHolder) viewHolder).mName.setText(str);
        ((MyViewHolder) viewHolder).mPrice.setText("Cena: " + data.getAdress() + " zł");
    }

    @Override
    public int getItemCount() {
        return mMenuList.size();
    }
}
