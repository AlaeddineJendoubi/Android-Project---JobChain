package esprit.tn.jobchain;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

public class DetailSearchF extends Fragment {
    private static final String url = "https://jobchain-tn.000webhostapp.com/v1/?op=getSeek";
    public String idSeekUrl;
    private String url1 ="https://jobchain-tn.000webhostapp.com/jobscript/v1/?op=getNbrRecom&"+idSeekUrl;
    public TextView tvSpec, tvRegion,  tvDesc,tvNbr,aff;
    public TextView btnRecom;
    public String nbrRecomOut;


     public DetailSearchF() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         Bundle bundle = null;
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_detail_search, container, false);
        tvSpec = root.findViewById(R.id.itmSpecEt);
        tvRegion = root.findViewById(R.id.itmRegEt);
        tvDesc = root.findViewById(R.id.itmDescEt);
        tvNbr = root.findViewById(R.id.itmNbrRecom);
        aff = root.findViewById(R.id.afficherBtn);

        btnRecom = root.findViewById(R.id.ajouterBtn);
        String desss = getArguments().getString("dsc");
        String specc = getArguments().getString("spec");
        String regg = getArguments().getString("region");
        //Integer nbrRe=getArguments().getString("revomNbr",0);
        String nId=getArguments().getString("idSeek");
        //Toast.makeText(getContext(),nId,Toast.LENGTH_LONG).show();

        idSeekUrl=nId;


        tvSpec.setText(specc);
        tvRegion.setText(regg);
        tvDesc.setText(desss);

        SharedPreferences.Editor editor = getActivity().getSharedPreferences("idRecom", MODE_PRIVATE).edit();
        editor.putString("idRecomm", nId);
        editor.apply();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://jobchain-tn.000webhostapp.com/jobscript/v1/?op=getNbrRecom&id="+idSeekUrl, response -> {

            try {
                JSONArray seek = new JSONArray(response);
                for (int i = 0; i < seek.length(); i++) {
                    JSONObject seekObject = seek.getJSONObject(i);


                    Integer suh = (Integer) seekObject.get("count");

                    nbrRecomOut=suh.toString();

                   //Toast.makeText(getContext(),nbrRecomOut, Toast.LENGTH_SHORT).show();
                    tvNbr = root.findViewById(R.id.itmNbrRecom);
                    tvNbr.setText(nbrRecomOut);


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
        });
        Volley.newRequestQueue(getContext()).add(stringRequest);
        getNbrRecom();


        btnRecom.setOnClickListener(v -> {




            RecommanderF nextFrag= new RecommanderF();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, nextFrag, "detailSearch")
                    .addToBackStack(null)
                    .commit();

        });

        aff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewRecommandationsFragment nextFrag= new ViewRecommandationsFragment();
                Bundle bundle1 = new Bundle();
               // Toast.makeText(getContext(),nId,Toast.LENGTH_SHORT).show();

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, nextFrag, "aff")
                        .addToBackStack(null)
                        .commit();
            }
        });

        return root;
    }

        public void getNbrRecom()
        {

           // Toast.makeText(getContext(),nbrRecomOut, Toast.LENGTH_SHORT).show();

        }



}
