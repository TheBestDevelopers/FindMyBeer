package thebestdevelopers.pl.findmybeer.favController;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import thebestdevelopers.pl.findmybeer.R;
import thebestdevelopers.pl.findmybeer.pubInfo.PubInfo;

public class FavRecyclerViewAdapter extends RecyclerView.Adapter {

    private ArrayList<PubData> mFavList;
    private RecyclerView mRecyclerView;

    // implementacja wzorca ViewHolder
    // każdy obiekt tej klasy przechowuje odniesienie do layoutu elementu listy
    // dzięki temu wywołujemy findViewById() tylko raz dla każdego elementu
    private class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mName;
        public TextView mAddress;

        public MyViewHolder(View v) {
            super(v);
            mName = (TextView) v.findViewById(R.id.fav_name);
            mAddress = (TextView) v.findViewById(R.id.fav_address);
        }
    }

    // konstruktor adaptera
    public FavRecyclerViewAdapter(ArrayList<PubData> favList, RecyclerView recyclerView){
        mFavList = favList;
        mRecyclerView = recyclerView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        // tworzymy layout artykułu oraz obiekt ViewHoldera
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fav_layout, viewGroup, false);

        //przejscie do widoku pubu
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // odnajdujemy indeks klikniętego elementu
                int position = mRecyclerView.getChildAdapterPosition(v);
                PubData data = mFavList.get(position);
                if (!data.getId().equals("")) {
                    Intent myIntent = new Intent(mRecyclerView.getContext(), PubInfo.class);
                    myIntent.putExtra("placeID", data.getId());
                    mRecyclerView.getContext().startActivity(myIntent);
                }
            }
        });

        // tworzymy i zwracamy obiekt ViewHolder
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        // uzupełniamy layout artykułu
        PubData data = mFavList.get(i);
        ((MyViewHolder) viewHolder).mName.setText(data.getName());
        ((MyViewHolder) viewHolder).mAddress.setText(data.getAdress());
    }

    @Override
    public int getItemCount() {
        return mFavList.size();
    }
}