package esprit.tn.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import esprit.tn.Entities.MaPublication;
import esprit.tn.jobchain.R;

public class MaPublicationAdapter extends RecyclerView.Adapter<MaPublicationAdapter.ViewHolder> {
    Context context;

    List<MaPublication> lstevenements;
    ImageLoader imageLoader;
    ImageLoader imageLoader2;



    public MaPublicationAdapter(Context context , List<MaPublication> getDataAdapter){
        super();
        this.lstevenements = getDataAdapter;

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MaPublication getDataAdapter1 =  lstevenements.get(position);
        imageLoader= ImageAdapter.getInstance(context).getImageLoader();
        imageLoader.get(getDataAdapter1.getImg(),
                ImageLoader.getImageListener(
                        holder.VollyImageView,//Server Image
                        R.mipmap.ic_launcher,//Before loading server image the default showing image.
                        android.R.drawable.ic_dialog_alert //Error image if requested image dose not found on server.
                )
        );


        holder.pubNom.setText(getDataAdapter1.getNom());
        holder.pubPrenom.setText(getDataAdapter1.getPrenom());
        holder.pubRegion.setText(getDataAdapter1.getRegion());
        holder.pubDescription.setText(getDataAdapter1.getDescription());
        holder.pubSpec.setText(getDataAdapter1.getSpec());
        holder.pubNb.setText(getDataAdapter1.getNbrfois());


        holder.VollyImageView.setImageUrl(getDataAdapter1.getImg(),imageLoader);


    }

    @Override
    public int getItemCount() {
        return lstevenements.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView pubNom , pubPrenom , pubRegion ,pubDescription, pubSpec , pubNb;
        public NetworkImageView VollyImageView, VollyImageView2 ;

        public ViewHolder(View itemView) {

            super(itemView);

            pubNom = (TextView) itemView.findViewById(R.id.firstprofil) ;
            pubPrenom = (TextView) itemView.findViewById(R.id.lastprofil) ;
            pubRegion = (TextView) itemView.findViewById(R.id.regionprofil) ;
            pubDescription = (TextView) itemView.findViewById(R.id.descprofil) ;
            pubSpec = (TextView) itemView.findViewById(R.id.specprofil) ;
            pubNb = (TextView) itemView.findViewById(R.id.nbprofil) ;

            VollyImageView =(NetworkImageView)itemView.findViewById(R.id.imageViewprofil);
            // VollyImageView2 =(NetworkImageView)itemView.findViewById(R.id.ipro);

        }
    }
}