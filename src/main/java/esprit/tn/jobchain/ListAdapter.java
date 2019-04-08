package esprit.tn.jobchain;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import esprit.tn.DataStorage.DbController;

import static esprit.tn.jobchain.ViewRecommandationsFragment.url;

public class ListAdapter  extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<ListItem> listItems;
    private Context context;
    public static DbController controller ;

    public ListAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_items, viewGroup, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        ListItem listItem = listItems.get(i);


        String inom = listItem.getItemUserName();
        String ipnom = listItem.getItemLastName();
        String ireg = listItem.getItemRegion();
        String ispec = listItem.getItemSpec();
        String idesc = listItem.getItemDesc();
        String inb = listItem.getNbrRecom();
        String toto = listItem.getItemIduser();

        int img = listItem.getImg1();

        viewHolder.tvSpec.setText(ispec);
        viewHolder.tvRegion.setText(ireg);
        viewHolder.tvUsername.setText(inom);
        viewHolder.tvDesc.setText(idesc);

        viewHolder.imageView.setImageResource(img);
        viewHolder.idSeek.setText(listItem.getIdSeek());
        viewHolder.idUserTv.setText(listItem.getItemIduser());




    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvSpec;
        public TextView tvRegion;
        public TextView tvUsername,tvUserlast;
        public TextView tvDesc;
        public TextView idSeek;
        public TextView idUserTv;
        public TextView nbrrecomTv;
        public CardView card;
        public Button btnRecom;
        public ImageView imageView;
        public ImageView fav;
        public ImageView delete;
        public String idUsercon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //find views of the items

            tvUserlast = itemView.findViewById(R.id.textView2);
            delete = itemView.findViewById(R.id.delSeek);
            tvSpec = itemView.findViewById(R.id.itmSpecEt);
            tvRegion = itemView.findViewById(R.id.itmRegEt);
            tvUsername = itemView.findViewById(R.id.userNameTv);
            tvDesc = itemView.findViewById(R.id.kiki);
            //nbrrecomTv = itemView.findViewById(R.id.nbrrecomTv);
            card = itemView.findViewById(R.id.cardViewClick);
            idSeek = itemView.findViewById(R.id.idSeek);
            idUserTv = itemView.findViewById(R.id.iduser);
            //btnRecom = itemView.findViewById(R.id.btnRecom);
            imageView = itemView.findViewById(R.id.imageView);
            fav = (ImageView) itemView.findViewById(R.id.favid);
            SharedPreferences userId;

            userId = context.getSharedPreferences("info", Context.MODE_PRIVATE); //1
            idUsercon = userId.getString("idUserCon", null);

            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle bundle = new Bundle();
                    bundle.putString("spec", tvSpec.getText().toString());
                    bundle.putString("region", tvRegion.getText().toString());
                    bundle.putString("dsc", tvDesc.getText().toString());
                    bundle.putString("idSeek", idSeek.getText().toString());
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    DetailSearchF myFragment = new DetailSearchF();
                    myFragment.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();

                }
            });


            fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int i =0;
                    String id =idSeek.getText().toString();

                    String n =tvUsername.getText().toString();
                    String p =tvSpec.getText().toString();
                    String r =tvRegion.getText().toString();
                    String s = tvSpec.getText().toString();
                    String d =tvDesc.getText().toString();

                    controller = new DbController(context);
                    try{
                        //Toast.makeText(context,id,Toast.LENGTH_SHORT).show();
                        controller.insert_contact(id,idUsercon,i,n,p,r,s,d);
                        Toast.makeText(context,"ajout avec succe",Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(context,"Favorites existante",Toast.LENGTH_SHORT).show();

                    }

                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (idUserTv.getText().toString().equals(idUsercon))
                    {
                        AlertDialog.Builder builder;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                        } else {
                            builder = new AlertDialog.Builder(context);
                        }
                        builder.setTitle("Supprimer la publication?")
                                .setMessage("Êtes-vous sûr de vouloir supprimer cette publication?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        final ProgressDialog progressDialog = new ProgressDialog(context);
                                        progressDialog.setMessage("Loading data ...");
                                        progressDialog.show();
                                        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, "https://jobchain-tn.000webhostapp.com/jobscript/v1/?op=deleteSeek&id="+idSeek.getText().toString()+"&id_user="+idUsercon, response ->
                                        {
                                            progressDialog.dismiss();
                                            try
                                            {
                                                JSONArray seek = new JSONArray(response);
                                                for (int i = 0; i < seek.length(); i++)
                                                {



                                                }
                                            }
                                            catch (JSONException e)
                                            {
                                                e.printStackTrace();
                                            }
                                        }, error -> {
                                            //  Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                        });
                                        Volley.newRequestQueue(context).add(stringRequest);
                                        HomeFragment myFragment = new HomeFragment();
                                        AppCompatActivity activity = (AppCompatActivity) v.getContext();
                                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();

                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // do nothing
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                    else
                    {
                        AlertDialog.Builder builder;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                        } else {
                            builder = new AlertDialog.Builder(context);
                        }
                        builder.setTitle("Delete entry")
                                .setMessage("vous ne pouvez pas supprimer ceci ")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })

                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                }

            });



        }
    }
}
