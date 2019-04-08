package esprit.tn.jobchain;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class RecomAdapter extends RecyclerView.Adapter<RecomAdapter.RecomViewHolder> {

    private Context context;
    private List<Recommandations> recommandationsList;

    public RecomAdapter(Context context, List<Recommandations> recommandationsList) {
        this.context = context;
        this.recommandationsList = recommandationsList;
    }

    @Override
    public RecomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.listlayoutmap,null);
        return new RecomViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecomViewHolder holder, int position) {
        Recommandations recommandations = recommandationsList.get(position);
        holder.textViewTitle.setText(recommandations.getNom());
        holder.textViewDesc.setText(recommandations.getDescription());
        holder.textViewRating.setText(recommandations.getNumero());
        holder.lat.setText(recommandations.getLatitude());
        holder.lag.setText(recommandations.getLongitude());
    }

    @Override
    public int getItemCount() {
        return  recommandationsList.size();
    }

    class RecomViewHolder extends RecyclerView.ViewHolder{

        TextView textViewTitle,textViewDesc,textViewRating,lat,lag;
        Button button;

        public RecomViewHolder(View itemView) {
            super(itemView);
            textViewTitle=itemView.findViewById(R.id.nomAfter);
            textViewDesc=itemView.findViewById(R.id.desAfter);
            textViewRating=itemView.findViewById(R.id.numAfter);
            button=itemView.findViewById(R.id.goTomap);
            lat=itemView.findViewById(R.id.lat);
            lag=itemView.findViewById(R.id.lag);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = context.getSharedPreferences("MarkerRecom", MODE_PRIVATE).edit();
                    editor.putString("latrecom", lat.getText().toString());
                    editor.putString("lagrecom", lag.getText().toString());
                    editor.putString("nom", textViewTitle.getText().toString());
                    editor.putString("num", textViewRating.getText().toString());
                    editor.apply();


                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    RecomMap myFragment = new RecomMap();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();

                }
            });
        }
    }
}
