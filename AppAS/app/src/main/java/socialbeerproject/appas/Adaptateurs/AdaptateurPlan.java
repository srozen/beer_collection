package socialbeerproject.appas.Adaptateurs;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import socialbeerproject.appas.Elements.ElementPlan;
import socialbeerproject.appas.R;

/*
 * Classe permettant de customizer les éléments du menu principal.
 * Chaque item sera affiché selon elem_menu.xml
 */

public class AdaptateurPlan extends BaseAdapter {


    Context context;
    List<ElementPlan> elements;

    public AdaptateurPlan(Context context, List<ElementPlan> elements) {
        this.context = context;
        this.elements = elements;
    }

    @Override
    public int getCount() {

        return elements.size();
    }

    @Override
    public Object getItem(int position) {

        return elements.get(position);
    }

    @Override
    public long getItemId(int position) {

        return elements.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.elem_plan, null);
        }

        TextView BP_titre= (TextView) convertView.findViewById(R.id.bp_title);
        TextView BP_desc = (TextView) convertView.findViewById(R.id.bp_desc);
        TextView BP_dateDebut = (TextView) convertView.findViewById(R.id.bp_dateB);
        TextView BP_dateFin = (TextView) convertView.findViewById(R.id.bp_dateF);
        TextView BP_reference = (TextView) convertView.findViewById(R.id.bp_ref);

        ElementPlan row_pos = elements.get(position);
        // setting the image resource and title

        BP_titre.setText(row_pos.getTitle());
        BP_desc.setText(row_pos.getDescription());
        BP_dateDebut.setText("A partir du : " + row_pos.getDateDebut());
        BP_dateFin.setText("Jusqu'au : " + row_pos.getDateFin());
        BP_reference.setText("Référence : " + row_pos.getReference());

        return convertView;

    }
}
