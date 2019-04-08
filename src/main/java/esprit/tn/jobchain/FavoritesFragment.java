package esprit.tn.jobchain;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import esprit.tn.Adapter.FavorisAdapter;
import esprit.tn.DataStorage.DbController;
import esprit.tn.Entities.Favoris;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {
    public static Context context;
    public ImageView delete;
    String idUsercon;

    public FavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_favorites, container, false);
        DbController controll = new DbController(getContext());
        SharedPreferences userId;

        userId = getContext().getSharedPreferences("info", Context.MODE_PRIVATE); //1
        idUsercon = userId.getString("idUserCon", null);

        ArrayList<Favoris> lm;
        //Toast.makeText(getContext(),idUsercon,Toast.LENGTH_SHORT).show();

        lm=controll.affichertout(idUsercon);

        ListView lstMatch = (ListView)root.findViewById(R.id.lsfav);
        FavorisAdapter adapter = new FavorisAdapter(getContext(), R.layout.list_fav, lm);

        lstMatch.setAdapter(adapter);









        return root;
    }



}