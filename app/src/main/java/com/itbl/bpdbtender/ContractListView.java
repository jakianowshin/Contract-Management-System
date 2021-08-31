package com.itbl.bpdbtender;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class ContractListView extends AppCompatActivity {
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    ContractListAdapter contractListAdapter;
    ListView listView;
    ContractList contractList;
    ArrayList<ContractList> contractLists=new ArrayList<>();
    SharedPreferences sharedPreferences;
    String strLoction="",projID="";


    Bundle bundle=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_contract_list);
        listView = (ListView) findViewById(R.id.table_conlist);

        bundle = getIntent().getExtras();
        if(bundle!=null){
            projID = bundle.getString("projID");
            Supply task = new Supply(ContractListView.this);
            task.execute();
        }

    }

    public class Supply extends AsyncTask<Void, Void, String> {
        @SuppressWarnings("unused")
        private Activity context;

        @SuppressWarnings("unused")
        ProgressDialog pd=null;


        public Supply(Activity context) {
            this.context = context;

        }

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(ContractListView.this, " Processing",
                    "Please wait...");
        }

        @Override
        protected String doInBackground(Void... params) {
//
            String result = "";

            try {


                int count = 0;

                String contract_currType,contract_price,contract_value,contract_No,contract_issueD,
                        contract_type,contract_bankName,contract_pkgDetails,contract_RefNo,
                        contract_suppName,contract_securityInfo,contract_Date,cs_exp_date,contract_branchName,contract_expiryD;

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


                    contract_currType = JO.getString("currency_DESC");
                    contract_price = JO.getString("contr_PRICE");
                    contract_value = JO.getString("cs_VALUE");
                    contract_No = JO.getString("contract_NO");
                    contract_issueD = JO.getString("cs_ISSUE_DATE");
                    contract_type = JO.getString("cntr_TYPE_DESC");
                    contract_pkgDetails = JO.getString("pk_DESC");
                    contract_bankName = JO.getString("bank_NAME");
                    contract_RefNo = JO.getString("cs_REF_NO");
                    contract_suppName = JO.getString("sup_NAME");
                    contract_securityInfo = JO.getString("bi_DESC");
                    contract_Date = JO.getString("contract_DATE");
                    cs_exp_date=JO.getString("cs_EXP_DATE");
                    contract_branchName = JO.getString("branch_NAME");
                    contract_expiryD = JO.getString("contr_EXP_DATE");



                    ContractList contractList = new ContractList( contract_currType,contract_price,contract_value,contract_No,
                            contract_issueD,contract_type,contract_pkgDetails,contract_bankName,
                            contract_RefNo,contract_suppName,contract_securityInfo,contract_Date,cs_exp_date,contract_branchName,contract_expiryD);

                    contractLists.add(contractList);
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


            contractListAdapter =new ContractListAdapter(ContractListView.this,contractLists);
            listView.setAdapter(contractListAdapter);


          
        }

    }
    
}
