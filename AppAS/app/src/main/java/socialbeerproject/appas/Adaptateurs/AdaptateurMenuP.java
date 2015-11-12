package socialbeerproject.appas.Adaptateurs;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import socialbeerproject.appas.Elements.ElementMenuP;
import socialbeerproject.appas.R;

/*
 * Classe permettant de customizer les éléments du menu principal.
 * Chaque item sera affiché selon elem_menu.xml
 */

public class AdaptateurMenuP extends BaseAdapter {

    Context context;
    List<ElementMenuP> elements;

    public AdaptateurMenuP(Context context, List<ElementMenuP> elements) {
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
            convertView = mInflater.inflate(R.layout.elem_menu, null);
        }

        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.Image);
        TextView Titre = (TextView) convertView.findViewById(R.id.Titre);
        TextView Descr = (TextView) convertView.findViewById(R.id.Descr);

        ElementMenuP row_pos = elements.get(position);
        // setting the image resource and title
        imgIcon.setImageResource(row_pos.getIcon());

        Titre.setText(row_pos.getTitle());
        Descr.setText(row_pos.getDescription());

        return convertView;

    }
}
