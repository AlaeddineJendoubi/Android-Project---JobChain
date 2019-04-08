package esprit.tn.jobchain;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.support.v4.os.LocaleListCompat.create;

public class Search_Submit extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner specialties;
    private Spinner regions;
    private EditText descreption;
    // private EditText image ;
    private Button validateSbTn;
    String id_user;
    String nomUser;
    String speciality, regionin, descriptionin, imagein;
    String seekUrl = "https://jobchain-tn.000webhostapp.com/jobscript/v1/?op=addSeek";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__submit);
        specialties = findViewById(R.id.spr_spec);
        regions = findViewById(R.id.spr_region);
        descreption = findViewById(R.id.descEt);
        //image=findViewById(R.id.imageEt);
        validateSbTn = findViewById(R.id.validateSearchBtn);
        initSpinerSpec();
        initSpinerReg();

        Context context = this;
        SharedPreferences settings;
        String id, nom;
        settings = context.getSharedPreferences("info", Context.MODE_PRIVATE); //1
        id = settings.getString("idUserCon", null); //2
        nom = settings.getString("nomUserCon", null); //2


        validateSbTn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSeek();
            }

            void addSeek() {
                id_user = id;
                nomUser = nom;
                nomUser = nom;
                //createDialog();


                descriptionin = descreption.getText().toString();
                imagein = "no image ";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, seekUrl, response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String code = jsonObject.getString("code");
                        String message = jsonObject.getString("message");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {

                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {


                        Map<String, String> params = new HashMap<String, String>();
                        params.put("id_user", id_user);
                        params.put("nomUser", nomUser);
                        params.put("speciality", speciality);
                        params.put("region", regionin);
                        params.put("description", descriptionin);
                        params.put("image", imagein);
                        // params.put("nbrRecom", DefnbrRecom);

                        return params;
                    }
                };

                MySingleton.getInstance(Search_Submit.this).addToRequestQue(stringRequest);
                Toast.makeText(getApplicationContext(), "Search Request Added", Toast.LENGTH_SHORT).show();

            }
        });


    }

    void initSpinerSpec() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.specialities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        specialties.setAdapter(adapter);
        specialties.setOnItemSelectedListener(this);
    }

    void initSpinerReg() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.regions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regions.setAdapter(adapter);
        regions.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner sp1 = (Spinner) parent;
        Spinner sp2 = (Spinner) parent;
        if (sp1.getId() == R.id.spr_spec) {
            speciality = parent.getItemAtPosition(position).toString();

        }
        if (sp2.getId() == R.id.spr_region) {
            regionin = parent.getItemAtPosition(position).toString();

        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        speciality = parent.getItemAtPosition(0).toString();
        regionin = parent.getItemAtPosition(0).toString();
    }
}