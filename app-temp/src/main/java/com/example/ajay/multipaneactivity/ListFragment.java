package com.example.ajay.multipaneactivity;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.android.volley.Request;
import com.android.volley.RequestQueue;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by ajay on 5/10/2016.
 */
public class ListFragment extends Fragment implements View.OnClickListener {

    private Communicator communicator;
    public ProgressDialog pDialog;



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof Communicator) {
            communicator = (Communicator) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implemenet MyListFragment.Communicator");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);


        pDialog = new ProgressDialog(getActivity().getBaseContext());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        return view;
    }


    @Override
    public void onClick(View v) {

    }










    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
