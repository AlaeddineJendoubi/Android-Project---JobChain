package esprit.tn.jobchain;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import esprit.tn.Adapter.ImageAdapter;
import esprit.tn.Adapter.MaPublicationAdapter;
import esprit.tn.Entities.Favoris;
import esprit.tn.Entities.MaPublication;
import esprit.tn.utile.Url;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilFragment extends Fragment {
    String HTTP_JSON_URL;
    String GET_JSON_FROM_SERVER_NAME_NOM = "nom";
    String GET_JSON_FROM_SERVER_NAME_PRENOM = "prenom";
    String GET_JSON_FROM_SERVER_NAME_REGION = "region";
    String GET_JSON_FROM_SERVER_NAME_DESCRIPTION = "description";
    String GET_JSON_FROM_SERVER_NAME_SPEC = "speciality";
    String GET_JSON_FROM_SERVER_NAME_NB = "nbrRecom";
    private static final String URL_READ =Url.URL+"profilDetail.php";
    SessionManager sessionManager;
    TextView nom,pre,em, numtel,address ;

    String Image_URL_JSON ="photo";
    private static final String URL_IMAGE ="";
    RecyclerView recyclerView;
    String respoonsee;
    ArrayList<MaPublication> evenementsListe;
    ArrayList<MaPublication> idlist;
    JsonArrayRequest jsonArrayRequest ;
    RequestQueue requestQueue ;
    View ChildView ;
    String getId;
    int GetItemPosition ;
    RecyclerView.Adapter recyclerViewadapter;
    RecyclerView.LayoutManager layoutManagerOfrecyclerView;
    //ArrayList<String> eventImage;
    ArrayList<String> eventDescriptions;
    ArrayList<String> eventRegion;
    ArrayList<String> eventSpec;
    ArrayList<String> eventNb;
    ArrayList<String> eventNom;
    ArrayList<String> eventPrenom;
    ArrayList<String> eventImage;
    ArrayList<String> eventImage2;
    ImageLoader imageLoader;

    List<MaPublication> listOfEventAdapter;
    TextView edit ;
    public NetworkImageView VollyImageView2;
    public ProfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.fragment_profil);
        HTTP_JSON_URL = Url.URL+"AfficherListEvenement.php?id_user="+((Constants) getActivity().getApplication()).getIdConnectedUser();
        System.out.println( "hhh"+((Constants) getActivity().getApplication()).getIdConnectedUser());
        View root = inflater.inflate(R.layout.fragment_profil, container, false);
        evenementsListe = new ArrayList<>();
        eventRegion= new ArrayList<>();
        eventNom= new ArrayList<>();
        eventNb= new ArrayList<>();
        eventSpec= new ArrayList<>();


        eventPrenom= new ArrayList<>();
        eventImage= new ArrayList<>();
        eventImage2= new ArrayList<>();

        eventDescriptions= new ArrayList<>();
        listOfEventAdapter= new ArrayList<>();
        idlist= new ArrayList<>();
       // VollyImageView2 =(NetworkImageView)root.findViewById(R.id.ipro);

        recyclerView = (RecyclerView) root.findViewById(R.id.evenement_recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManagerOfrecyclerView = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManagerOfrecyclerView);


        sessionManager = new SessionManager(getContext());
        // sessionManager.checkLogin();
        HashMap<String,String> user = sessionManager.getUserDetail();
        getId= user.get(sessionManager.ID);
        nom =(TextView) root.findViewById(R.id.nomP);
        pre =(TextView) root.findViewById(R.id.prenomP);
        em =(TextView) root.findViewById(R.id.emailP);
        numtel =(TextView) root.findViewById(R.id.numP);
        address =(TextView) root.findViewById(R.id.adP);
        System.out.println( "hne");
        JSON_DATA_WEB_CALL();
        getUserD();
        FloatingActionButton fab = root.findViewById(R.id.fabprofiil);
        fab.setOnClickListener(v -> nextScreen());


        System.out.println( "hne3");
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onSingleTapUp(MotionEvent motionEvent) {

                    return true;
                }

            });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {


                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        return root ;
    }


    public void JSON_DATA_WEB_CALL(){

        jsonArrayRequest = new JsonArrayRequest(HTTP_JSON_URL,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        //   progressBar.setVisibility(View.GONE);


                        JSON_PARSE_DATA_AFTER_WEBCALL(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestQueue = Volley.newRequestQueue(this.getContext());

        requestQueue.add(jsonArrayRequest);
        System.out.println( "9bal il try ...");


    }



    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array){

        for(int i = 0; i<array.length(); i++) {

            MaPublication GetDataAdapter2 = new MaPublication();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                System.out.println( "f try ...");


                GetDataAdapter2.setRegion(json.getString("region"));
                GetDataAdapter2.setNbrfois(json.getString("nbrRecom"));
                GetDataAdapter2.setSpec(json.getString("speciality"));
                GetDataAdapter2.setDescription(json.getString("description"));
                GetDataAdapter2.setNom(json.getString("nom"));
                GetDataAdapter2.setPrenom(json.getString("prenom"));
                GetDataAdapter2.setImg(URL_IMAGE+json.getString(Image_URL_JSON));


                eventRegion.add(json.getString(GET_JSON_FROM_SERVER_NAME_REGION));
                eventDescriptions.add(json.getString(GET_JSON_FROM_SERVER_NAME_DESCRIPTION));
                eventSpec.add(json.getString(GET_JSON_FROM_SERVER_NAME_SPEC));
                eventNb.add(json.getString(GET_JSON_FROM_SERVER_NAME_NB));
                eventNom.add(json.getString("nom"));
                eventPrenom.add(json.getString("prenom"));
                eventImage.add(URL_IMAGE+json.getString(Image_URL_JSON));
                eventImage2.add(URL_IMAGE+json.getString(Image_URL_JSON));


            } catch (JSONException e) {

                e.printStackTrace();
            }
            evenementsListe.add(GetDataAdapter2);
            System.out.println( "listaa"+evenementsListe.toString());
        }

        recyclerViewadapter = new MaPublicationAdapter(this.getContext(),evenementsListe);

        recyclerView.setAdapter(recyclerViewadapter);

    }
    public boolean noData(){

        if (evenementsListe.isEmpty()){
            return true;
        }
        return false;
    }

    private void  getUserD( ){
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_READ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        //  Log.i(TAG, response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if( success.equals("1")){
                                System.out.println("fil 1 jaw");

                                for (int i =0 ; i < jsonArray.length();i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String  strnom = object.getString("nom").trim();
                                    String  strprenom = object.getString("prenom").trim();
                                    String  strnum = object.getString("numtel").trim();
                                    String  stremail = object.getString("email").trim();
                                    String  straddress = object.getString("address").trim();

                                    nom.setText(strnom);
                                    pre.setText(strprenom);
                                    numtel.setText(strnum);
                                    em.setText(stremail);
                                    address.setText(straddress);

                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            System.out.println("fil e");


                            Toast.makeText(getContext(),"Error  .... "+e.toString(),Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        System.out.println("fil Error");

                        Toast.makeText(getContext(),"Error Reading "+error.toString(),Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>  params = new HashMap<>();
                params.put("id",getId);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }
    void nextScreen() {
        Intent intent = new Intent(getContext(), Search_Submit.class);
        startActivity(intent);
    }
}