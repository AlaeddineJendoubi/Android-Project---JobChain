package esprit.tn.jobchain;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import static android.content.Context.MODE_PRIVATE;


public class ViewRecommandationsFragment extends Fragment {

     public static final String url="https://jobchain-tn.000webhostapp.com/jobscript/v1/?op=getRecoms&id=";
     String seekId ;
    RecyclerView recyclerView ;


    private List<Recommandations> recommandationsList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View root =inflater.inflate(R.layout.fragment_view_recommandations, container, false);

         recyclerView= root.findViewById(R.id.recyclerViewMap);
         recyclerView.setHasFixedSize(true);
         recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
         loadRecommandations();

        SharedPreferences userId;

        userId = getContext().getSharedPreferences("info", Context.MODE_PRIVATE); //1
        String iduser = userId.getString("idUserCon", null);

        SharedPreferences prefs = getActivity().getSharedPreferences("idRecom", MODE_PRIVATE);
        String restoredText = prefs.getString("idRecomm", null);
        if (restoredText != null)
        {
            seekId = prefs.getString("idRecomm", "Couldn't retreive recommandation id");//"No name defined" is the default value.
        }
        //String nId=getArguments().getString("idSeek");
       // Toast.makeText(getContext(),seekId,Toast.LENGTH_SHORT).show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url+seekId, response ->
        {
            try {

                JSONArray recoms = new JSONArray(response);
                for (int i=0 ;i<recoms.length();i++)
                {

                    JSONObject recomObjet = recoms.getJSONObject(i);

                    int id =recomObjet.getInt("id");
                    int idUser =recomObjet.getInt("idUser");
                    int idSeek =recomObjet.getInt("idSeek");
                    String description=recomObjet.getString("description");
                    String nom =recomObjet.getString("nom");
                    String numero =recomObjet.getString("numero");
                    String latitude =recomObjet.getString("latitude");
                    String longitude =recomObjet.getString("longitude");

                    // Toast.makeText(getContext(),"jebthom",Toast.LENGTH_SHORT).show();
                    Recommandations recom=new Recommandations(id,idUser,idSeek,description,nom,numero,latitude,longitude);
                    recommandationsList.add(recom);

                }

                RecomAdapter adapter=new RecomAdapter(getContext(),recommandationsList);
                recyclerView.setAdapter(adapter);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_SHORT).show());

        Volley.newRequestQueue(getContext()).add(stringRequest);
        return root;
    }

    private void loadRecommandations()
    {

    }

}
