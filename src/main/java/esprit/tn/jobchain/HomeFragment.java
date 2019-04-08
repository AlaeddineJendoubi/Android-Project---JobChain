package esprit.tn.jobchain;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment  {
    private static final String url = "https://jobchain-tn.000webhostapp.com/jobscript/v1/?op=getSeek";
    private RecyclerView recyclerView;
    //  private RecyclerView.Adapter adapter ;
    private List<ListItem> listItems;

    public String speciality, regionin;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_home, container, false);
        /*
         Init values of the recyclerView
        */
        recyclerView = root.findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        listItems = new ArrayList<>();
        loadSeek();
        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(v -> nextScreen());

        return root ;
    }



    private void loadSeek()  {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading data ...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            progressDialog.dismiss();
            try {
                JSONArray seek = new JSONArray(response);
                for (int i = 0; i < seek.length(); i++) {
                    JSONObject seekObject = seek.getJSONObject(i);

                    String special = seekObject.getString("speciality");
                    String reg = seekObject.getString("region");
                    String des = seekObject.getString("description");
                    String userName = seekObject.getString("nomUser");
                    Integer nbrrecomTv = seekObject.getInt("nbrRecom");

                    Integer idSeekint= seekObject.getInt("id");
                    String idUser= seekObject.getString("id_user");
                    String nbrString= nbrrecomTv.toString();
                    String idSeek=idSeekint.toString();

                //    Toast.makeText(getContext(),idSeek, Toast.LENGTH_SHORT).show();

                    special = "Je cherche un :" + " " + special;
                    reg = "dans la region de :" + " " + reg;
                    des = "avec la descreption Suivante :" + " " + des;

                    ListItem item = new ListItem(userName,special, reg , des ,nbrString,idSeek,idUser);
                    listItems.add(item);
                }
                ListAdapter adapter = new ListAdapter(listItems, getContext());
                recyclerView.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
          //  Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
        });
        Volley.newRequestQueue(getContext()).add(stringRequest);

    }

    void nextScreen() {
        Intent intent = new Intent(getContext(), Search_Submit.class);
        startActivity(intent);
    }


}
