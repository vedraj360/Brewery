package com.brewery;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.brewery.Adapters.BeerAdapter;
import com.brewery.Models.BeerModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    private RecyclerView beerlist;
    private ArrayList<BeerModel> ModelArrayList;
    private BeerAdapter beerAdapter;
    private SearchView searchView;
    private Button bth_sort;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        bth_sort = findViewById(R.id.sort);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        initViews();

    }

    void initViews() {
        beerlist = findViewById(R.id.beerlist);
        beerlist.setLayoutManager(new LinearLayoutManager(this));
        requestUrl();
        bth_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSort();
            }
        });
    }

    private void requestUrl() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://starlord.hackerearth.com/beercraft";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response == null) {
                    Toast.makeText(getApplicationContext(), "Couldn't fetch the Details! Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                BeerModel[] beerModel = gson.fromJson(response, BeerModel[].class);
                ModelArrayList = new ArrayList<>(Arrays.asList(beerModel));

                beerAdapter = new BeerAdapter(MainActivity.this, ModelArrayList);

                beerlist.setAdapter(beerAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Main", String.valueOf(error));
            }
        });
        requestQueue.add(stringRequest);
    }

    private void dialogSort() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialogbox);
        dialog.setTitle("Sort By");
        Button ass = dialog.findViewById(R.id.ascending);
        Button des = dialog.findViewById(R.id.descending);
        ass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sorting("ass");
                dialog.dismiss();
            }
        });
        des.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sorting("des");
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (beerAdapter != null) beerAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    void sorting(final String ord) {
        Collections.sort(ModelArrayList, new Comparator<BeerModel>() {
            @Override
            public int compare(BeerModel o1, BeerModel o2) {
                double a1, a2;
                if (!o1.getAbv().equals("")) {
                    a1 = Double.parseDouble(o1.getAbv());
                } else {
                    a1 = 0.06;
                }
                if (!o2.getAbv().equals("")) {
                    a2 = Double.parseDouble(o2.getAbv());
                } else {
                    a2 = 0.06;
                }
                if (ord.equals("ass")) {
                    return Double.compare(a1, a2);

                } else {
                    return Double.compare(a2, a1);

                }
            }

        });
        beerlist.getAdapter().notifyDataSetChanged();
    }
}
