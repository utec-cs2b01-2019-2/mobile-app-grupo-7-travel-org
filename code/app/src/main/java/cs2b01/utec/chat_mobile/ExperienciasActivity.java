package cs2b01.utec.chat_mobile;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ExperienciasActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiencias);
        mRecyclerView = findViewById(R.id.main_recycler_view);
        setTitle("¡Bienvenido "+getIntent().getExtras().get("nombre").toString()+"!");
    }

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
    protected void onResume(){
        super.onResume();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getExperiencias();
    }

    public Activity getActivity(){
        return this;
    }


    //Obtener los usuarios del servidor
    public void getExperiencias(){
        String url = "https://travelorg2.herokuapp.com/experiencias";
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONArray parameters = new JSONArray();
        final int userId = getIntent().getExtras().getInt("user_id");
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                parameters,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Paso 1: Crear la vista para cada usuario (element_viewAdd.xmlxml)
                        //Paso 2: Crear la vista de los elementos dinamicos e injectarlo en Rcyc view
                        mAdapter = new ExperienciasAdapter(response, getActivity(), userId);
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

