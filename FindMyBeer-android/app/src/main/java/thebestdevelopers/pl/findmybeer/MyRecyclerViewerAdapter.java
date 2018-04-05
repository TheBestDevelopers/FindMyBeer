package thebestdevelopers.pl.findmybeer;

    import java.util.List;

    import android.support.v7.widget.RecyclerView;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.View.OnClickListener;
    import android.view.ViewGroup;
    import android.widget.TextView;

public class MyRecyclerViewerAdapter extends RecyclerView.Adapter<MyRecyclerViewerAdapter.ViewHolder> {
    private List<Pub> pubs;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View layout;
        public TextView textViewStars;
        public TextView textViewPubName;
        public TextView textViewDistance;
        public TextView textViewFreeTables;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            textViewStars = (TextView) v.findViewById(R.id.mTextViewStars);
            textViewPubName = (TextView) v.findViewById(R.id.mTextViewPubName);
            textViewFreeTables = (TextView) v.findViewById(R.id.mTextViewFreeTables);
            textViewDistance = (TextView) v.findViewById(R.id.mTextViewDistance);
        }
    }

    public void add(int position, Pub _pub) {
        pubs.add(position, _pub);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        pubs.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyRecyclerViewerAdapter(List<Pub> _pubs) {
        pubs = _pubs;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyRecyclerViewerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.pub_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Pub currentPub = pubs.get(position);
        holder.textViewPubName.setText(currentPub.getPubName());
        holder.textViewDistance.setText(currentPub.getDistance().toString() + "m");
        holder.textViewStars.setText(currentPub.getStars().toString());
        holder.textViewFreeTables.setText(currentPub.getFreeTablesCount().toString());
        holder.textViewPubName.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(position);
           }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return pubs.size();
    }

}