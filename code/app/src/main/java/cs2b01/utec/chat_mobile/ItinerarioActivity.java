package cs2b01.utec.chat_mobile;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class ItinerarioActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;

    public void onBtnItinerario(View view){
        Intent intent = new Intent(this, ItinerarioActivity.class);
        String nombre = getIntent().getExtras().get("nombre").toString();
        int user_id = getIntent().getExtras().getInt("user_id");
        intent.putExtra("user_id",user_id);
        intent.putExtra("nombre",nombre);
        startActivity(intent);
    }

    public void onExperiencias(View view){
        Intent intent = new Intent(this, CrearExperiencias.class);
        String nombre = getIntent().getExtras().get("nombre").toString();
        int user_id = getIntent().getExtras().getInt("user_id");
        intent.putExtra("user_id",user_id);
        intent.putExtra("nombre",nombre);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiencias);
        mRecyclerView = findViewById(R.id.main_recycler_view);
        setTitle("Este es tu itinerario "+getIntent().getExtras().get("nombre").toString());
    }

    @Override
    protected void onResume(){
        super.onResume();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getItinerario();
    }

    public Activity getActivity(){
        return this;
    }

    public ItinerarioActivity getItinerarioActivity(){return this;}


    public void getItinerario(){
        final int userId = getIntent().getExtras().getInt("user_id");
        String url = "https://travelorg2.herokuapp.com/itinerarioDatos/"+userId;
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONArray parameters = new JSONArray();
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                parameters,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        mAdapter = new ItinerarioAdapter(response, getActivity(), userId, getItinerarioActivity());
                        mRecyclerView.setAdapter(mAdapter);

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                error.printStackTrace();

            }
        });
        queue.add(jsonObjectRequest);

    }

}
