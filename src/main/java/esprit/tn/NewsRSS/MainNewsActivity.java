package esprit.tn.NewsRSS;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import esprit.tn.jobchain.R;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class MainNewsActivity extends Fragment {

    RecyclerView recyclerView ;

 /*   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_news);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewRSS);
        Feed feed = new Feed(getContext() , recyclerView);
        feed.execute();
    }*/

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root =  inflater.inflate(R.layout.activity_main_news, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerViewRSS);
        Feed feed = new Feed(getContext() , recyclerView);
        feed.execute();
        return root ;
    }
}