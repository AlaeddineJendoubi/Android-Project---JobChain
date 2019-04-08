package esprit.tn.jobchain;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Menu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView mail ;
    TextView prof ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
      navigationView.setNavigationItemSelectedListener(this);
       /* mail= (TextView) findViewById(R.id.emailTV);
        prof= (TextView) findViewById(R.id.profil);


        SharedPreferences sharedPref = getSharedPreferences("codeInfo", Context.MODE_PRIVATE);
        final String coooode = sharedPref.getString("email","");
        mail.setText(coooode);


        prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Menu.this,Profil.class);
                startActivity(i);
            }
        });*/
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        Bundle bundle = new Bundle();
        if (id == R.id.profilmenu) {



        }

        return true;
    }
}
