package br.com.opet.tds.appvolleyopet;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RequestMovie rMovies = new RequestMovie();

        String url = "https://swapi.co/api/films";

        MyRequest moviesAPI = new MyRequest(url,rMovies);

        MySingleton.getInstance(this).addToRequestQueue(moviesAPI.getRequest());
    }

    private class RequestMovie implements Response.Listener<JSONObject>{

        private List<StarWarsInfo> sw;

        public RequestMovie(){
            sw = new ArrayList<>();
        }

        @Override
        public void onResponse(JSONObject response) {
            try {
                JSONArray movies = response.getJSONArray("results");
                String movieString = "";
                for(int i = 0; i < movies.length(); i++){
                    movieString = movies.getJSONObject(i).getString("title");
                    sw.add(new StarWarsInfo(movieString,new ArrayList<Chars>()));

                    JSONArray charsArray = movies.getJSONObject(i).getJSONArray("characters");

                    for(int j = 0; j < charsArray.length(); j++) {
                        RequestChars rChars = new RequestChars(sw,i);
                        String url = charsArray.getString(j);

                        MyRequest charsAPI = new MyRequest(url,rChars);
                        MySingleton.getInstance(MainActivity.this).addToRequestQueue(charsAPI.getRequest());
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private class RequestChars implements Response.Listener<JSONObject>{

        private List<StarWarsInfo> sw;
        int index;
        public RequestChars(List<StarWarsInfo> sw, int index){
            this.sw = sw;
            this.index = index;
        }

        @Override
        public void onResponse(JSONObject response) {
            try {
                String charString = response.getString("name");
                RequestPlanets rPlanet = new RequestPlanets(sw,index,charString);
                String url = response.getString("homeworld");

                MyRequest planetAPI = new MyRequest(url,rPlanet);

                MySingleton.getInstance(MainActivity.this).addToRequestQueue(planetAPI.getRequest());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private class RequestPlanets implements Response.Listener<JSONObject>{

        private TextView textJSON;
        private List<StarWarsInfo> sw;
        private String charName;
        int index;
        public RequestPlanets(List<StarWarsInfo> sw, int index, String charName){
            this.sw = sw;
            this.charName = charName;
            this.index = index;
            textJSON = (TextView) findViewById(R.id.textJSON);
        }
        @Override
        public void onResponse(JSONObject response) {
            try {
                String planetName = response.getString("name");
                sw.get(index).getCharacters().add(new Chars(charName,planetName));
                textJSON.setText(sw.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class MyRequest{

        private JsonObjectRequest request;

        public MyRequest(String url, Response.Listener<JSONObject> listener){
            request = new JsonObjectRequest(Request.Method.GET, url, null,
                    listener, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
        }

        public JsonObjectRequest getRequest() {
            return request;
        }

        public void setRequest(JsonObjectRequest request) {
            this.request = request;
        }
    }
}
