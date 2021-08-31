package com.itbl.bpdbtender;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProjectNameVIew4 extends AppCompatActivity {

    ProjectNameAdapter4 projectNameAdapter4;
    ListView listView;
    ArrayList<ProjectNameList4> projectNameLists4=new ArrayList<>();
    TextView header;
    EditText inputSearch;
    SharedPreferences sharedPreferences;
    String strLoction="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_project_name_list4);
        listView = findViewById(R.id.table_namelist);
        inputSearch = findViewById(R.id.search_tname);


        // header = findViewById(R.id.header);
//        header.setTypeface(ResourcesCompat.getFont(this, R.font.aldrich));
//         json_string = getIntent().getExtras().getString("json_data");

        sharedPreferences = getSharedPreferences("project_NAME", Context.MODE_PRIVATE);
        strLoction = sharedPreferences.getString("PROJECT_NAME", "");
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                //Supplier.this.adapter.getFilter().filter(inputSearch.getText().toString());
                String text = inputSearch.getText().toString().toLowerCase(Locale.getDefault());
                projectNameAdapter4.getFilter().filter(text);

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });


        Display task = new Display(ProjectNameVIew4.this);
        task.execute();

//
    }

    public class Display extends AsyncTask<Void, Void, String> {
        @SuppressWarnings("unused")
        private Activity context;

        @SuppressWarnings("unused")
        ProgressDialog pd=null;

        public Display(Activity context){
            this.context = context;
        }

        @Override
        protected void onPreExecute(){
            pd = ProgressDialog.show(ProjectNameVIew4.this, " Processing",
                    "Please wait...");
        }

        @Override
        protected String doInBackground(Void... params){
            final List<Pair<String, String>> postParameters = new ArrayList<>();
            postParameters.add(new Pair("strLocation", strLoction));
            String result = "";

            try{

                int count = 0;

                String projectName,projectId;

                try {
                    String response = CustomHttpClientGet.execute("");
                    result = response.toString();
                    //result=result.replaceAll("[^a-zA-Z0-9]+","");

                } catch (Exception e) {
                    Log.e("log_tag", "Error in http connection!!" + e.toString());
                }

                JSONArray jsonArray = new JSONArray(result.toString());

                while (count < jsonArray.length()){

                    JSONObject JO = jsonArray.getJSONObject(count);


                    projectName = JO.getString("project_NAME");
                    projectId = JO.getString("project_ID");

                    ProjectNameList4 ProjectNameList4 = new ProjectNameList4(projectName,projectId);
                    projectNameLists4.add(ProjectNameList4);
                    count++;
                }
            }

            catch (Exception e) {
                Log.e("log_tag", "Error in http connection!!" + e.toString());
            }

            return result;
        }




        @Override
        protected void onPostExecute(String result) {
            pd.dismiss();

            projectNameAdapter4 =new ProjectNameAdapter4(ProjectNameVIew4.this,projectNameLists4);
            listView.setAdapter(projectNameAdapter4);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(ProjectNameVIew4.this, ProjectDetailView2.class);
                    intent.putExtra("projID",projectNameLists4.get(position).getProjectId());
                    startActivity(intent);

                }
            });
        }
    }

}
