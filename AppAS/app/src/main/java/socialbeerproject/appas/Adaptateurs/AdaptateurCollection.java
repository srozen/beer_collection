package socialbeerproject.appas.Adaptateurs;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import socialbeerproject.appas.Elements.ElementCollection;
import socialbeerproject.appas.R;

/*
 * Classe permettant de customizer les éléments de la collection.
 * Chaque item sera affiché selon elem_collection.xml
 */

public class AdaptateurCollection extends BaseAdapter {

    Context context;
    List<ElementCollection> elements;

    public AdaptateurCollection(Context context, List<ElementCollection> elements) {
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
            convertView = mInflater.inflate(R.layout.elem_collection, null);
        }

        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.Image);
        TextView Titre = (TextView) convertView.findViewById(R.id.Nom);

        ElementCollection row_pos = elements.get(position);
        // setting the image resource and title

        /*
         * TODO: A terminer avec les progressBar (rating)
         */
        ProgressBar pBRatingGlo = (ProgressBar) convertView.findViewById(R.id.RatingGlo);
        ProgressBar pBRatingPer = (ProgressBar) convertView.findViewById(R.id.RatingPer);



        if (row_pos.getRatingPer() != -1){
            pBRatingPer.setProgress((int)(row_pos.getRatingPer()*10));
        } else {
            pBRatingPer.setVisibility(View.INVISIBLE);
        }
        if (row_pos.getRatingGlo() != -1){
            pBRatingGlo.setProgress((int) (row_pos.getRatingGlo() * 10));
        } else {
            pBRatingGlo.setVisibility(View.INVISIBLE);
        }

        imgIcon.setImageResource(row_pos.getIcon());
        Titre.setText(row_pos.getNom());

        return convertView;
    }
}
