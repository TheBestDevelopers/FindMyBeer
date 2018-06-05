package thebestdevelopers.pl.findmybeer.pubList;

import java.util.ArrayList;
    import java.util.List;

    import android.location.Location;
    import android.support.v7.widget.RecyclerView;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.TextView;

    import android.widget.Filter;
    import android.widget.Filterable;

    import thebestdevelopers.pl.findmybeer.R;

public class MainMenuRecyclerViewerAdapter extends RecyclerView.Adapter<MainMenuRecyclerViewerAdapter.ViewHolder> implements Filterable{
    private List<Pub> pubs;
    private List<Pub> filteredPubs;
    Location userLocation;
    private ItemClickListener clickListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView textViewStars;
        public TextView textViewPubName;
        public TextView textViewDistance;
        public TextView textViewFreeTables;


        public ViewHolder(View v) {
            super(v);
            textViewStars = (TextView) v.findViewById(R.id.mTextViewStars);
            textViewPubName = (TextView) v.findViewById(R.id.mTextViewPubName);
            textViewFreeTables = (TextView) v.findViewById(R.id.mTextViewFreeTables);
            textViewDistance = (TextView) v.findViewById(R.id.mTextViewDistance);
            itemView.setOnClickListener(this);

        }
        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                clickListener.onClick(view, getAdapterPosition());
            }
        }
    }

    public void add(int position, Pub _pub) {
        pubs.add(position, _pub);
        notifyItemInserted(position);
    }

    public MainMenuRecyclerViewerAdapter(List<Pub> _pubs) {
        userLocation = new Location("userLocation");
        userLocation.setLatitude(0.0);
        userLocation.setLongitude(0.0);
        pubs = _pubs;
        filteredPubs = _pubs;
    }

    @Override
    public MainMenuRecyclerViewerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.pub_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final Pub currentPub = filteredPubs.get(position);
        Location pubLocation = new Location("pub");
        pubLocation.setLatitude(currentPub.getLatitude());
        pubLocation.setLongitude(currentPub.getLongitude());
        Integer distance = (int) userLocation.distanceTo(pubLocation);
        currentPub.setDistance(distance);
        setTextViews(currentPub, distance, holder);
    }

    private void setTextViews(Pub currentPub, Integer distance, ViewHolder holder) {
        holder.textViewPubName.setText(currentPub.getPubName());
        if (distance > 10000)
            setTextCountingDistance(holder);
        else if (distance > 1000)
            setDistanceKilometers(distance, holder);
        else
            holder.textViewDistance.setText(distance + "m");
        holder.textViewStars.setText(currentPub.getRating().toString());
        holder.textViewFreeTables.setText(currentPub.getFreeTablesCount().toString());
    }

    private void setTextCountingDistance(ViewHolder holder) {
        holder.textViewDistance.setText("Counting distance...");
    }

    private void setDistanceKilometers(Integer distance, ViewHolder holder) {
        holder.textViewDistance.setText(distance/1000 + "km " +distance % 1000 + "m");
    }

    @Override
    public int getItemCount() {
        return filteredPubs.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }
    public void updateLocation(Location _location) {
        this.userLocation = _location;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredPubs = pubs;
                } else {
                    ArrayList<Pub> filteredList = new ArrayList<>();

                    for (Pub p : pubs) {
                        if (containsCharString(p, charString)) { //tu mozna ew wsadzic wyszukiwanie po innych rzeczach, np po liczbie wolnych stolikow
                            filteredList.add(p);
                        }
                    }
                    filteredPubs = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredPubs;
                return filterResults;
            }

            private Boolean containsCharString(Pub currentPub, String charString) {
                return currentPub.getPubName().toLowerCase().contains(charString);
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredPubs = (ArrayList<Pub>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}