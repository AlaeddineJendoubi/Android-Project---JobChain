package esprit.tn.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import esprit.tn.DataStorage.DbController;
import esprit.tn.Entities.Favoris;
import esprit.tn.jobchain.DetailSearchF;
import esprit.tn.jobchain.FavoritesFragment;
import esprit.tn.jobchain.R;

public class FavorisAdapter extends ArrayAdapter {
    Context context;
    public ImageView img;
    public TextView nom, tvSpec, tvRegion,idSeekTv;
    public ImageView delete;
    String idUsercon;
    int resource;
    List<Favoris> favoris;
    DbController controller;

    public FavorisAdapter(@NonNull Context context, int resource, @NonNull List<Favoris> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.favoris = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, null);
        controller = new DbController(context);


        nom = convertView.findViewById(R.id.userNameTv);
        tvSpec = convertView.findViewById(R.id.itmSpecEt);
        tvRegion = convertView.findViewById(R.id.itmRegEt);
        idSeekTv = convertView.findViewById(R.id.idSeek);
        img = convertView.findViewById(R.id.img1);
        delete = convertView.findViewById(R.id.favdel);



        img.setImageResource(favoris.get(position).getImg());
        nom.setText(favoris.get(position).getPrenom());
        tvSpec.setText(favoris.get(position).getRegion());
        tvRegion.setText(favoris.get(position).getNbrfois());
        idSeekTv.setText(favoris.get(position).getIdSeek());

        SharedPreferences userId;

        userId = getContext().getSharedPreferences("info", Context.MODE_PRIVATE); //1
        idUsercon = userId.getString("idUserCon", null);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.deletefav(idUsercon);
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                FavoritesFragment myFragment = new FavoritesFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();

            }
        });

        return convertView;
    }
}