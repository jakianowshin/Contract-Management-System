package com.itbl.bpdbtender;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ProjectNameAdapter2 extends ArrayAdapter<ProjectNameList2> {

    private List<ProjectNameList2> list;
    Button togoPackage;

    private LayoutInflater inflator;
    private List<ProjectNameList2> newlist = null;
    private ItemFilter mFilter = new ItemFilter();
    public ProjectNameAdapter2(Activity context, List<ProjectNameList2> newlist) {
        super(context, R.layout.row_project_name_list2, newlist);
        this.newlist = newlist;
        inflator = context.getLayoutInflater();
        this.list = new ArrayList<ProjectNameList2>();
        this.list.addAll(newlist);

    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ProjectNameList2 getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ProjectNameAdapter.ProjectNameHolder ProjectNameHolder;

        if(convertView == null){

            //row = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView= inflator.inflate(R.layout.row_tender_name_list,null);
            ProjectNameHolder = new ProjectNameAdapter.ProjectNameHolder();

            ProjectNameHolder.tx_project_name = (TextView)convertView.findViewById(R.id.proj_Pname);
            ProjectNameHolder.tx_project_id = (TextView)convertView.findViewById(R.id.proj_pId);




            convertView.setTag(ProjectNameHolder);
            convertView.setTag(R.id.proj_Pname, ProjectNameHolder.tx_project_name);
            convertView.setTag(R.id.proj_pId, ProjectNameHolder.tx_project_id);






        }

        else{
            ProjectNameHolder = (ProjectNameAdapter.ProjectNameHolder)convertView.getTag();

        }


        //BookHistory bookHistory = (BookHistory)this.getItem(position);


        ProjectNameHolder.tx_project_name.setText(String.valueOf(list.get(position).getProjectName()));
        ProjectNameHolder.tx_project_id.setText(String.valueOf(list.get(position).getProjectId()));





        return convertView;

    }


    public Filter getFilter() {
        return mFilter;
    }



    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<ProjectNameList2> orglist = newlist;

            int count = orglist.size();
            final ArrayList<ProjectNameList2> nlist = new ArrayList<ProjectNameList2>(count);

            String filterableString;

            for (int i = 0; i < count; i++) {
                filterableString = orglist.get(i).getProjectName();



                if (filterableString.toLowerCase().contains(filterString)) {


                    nlist.add(orglist.get(i));
                }
            }


            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list = (ArrayList<ProjectNameList2>) results.values;
            notifyDataSetChanged();
        }

    }

    static class ProjectNameHolder{

        protected static TextView tx_project_name;
        protected static TextView tx_project_id;


    }


}
