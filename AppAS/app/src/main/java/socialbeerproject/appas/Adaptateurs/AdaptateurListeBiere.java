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

import socialbeerproject.appas.Elements.ElementListeBiere;
import socialbeerproject.appas.R;

/*
 * Classe permettant de customizer les éléments de la collection.
 * Chaque item sera affiché selon elem_liste_bieree.xml
 */

public class AdaptateurListeBiere extends BaseAdapter {

    Context context;
    List<ElementListeBiere> elements;

    public AdaptateurListeBiere(Context context, List<ElementListeBiere> elements) {
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
            convertView = mInflater.inflate(R.layout.elem_liste_biere, null);
        }

        ElementListeBiere elem = elements.get(position);

        this.updateName(convertView,elem);
        this.updateImage(convertView, elem);
        this.updateRating(convertView, elem);

        return convertView;
    }

    private void updateName(View convertView, ElementListeBiere elem){
        TextView titre = (TextView) convertView.findViewById(R.id.textView_nom_LB);
        titre.setText(elem.getNom());
    }

    private void updateImage(View convertView, ElementListeBiere elem){
        /*
         * TODO: Image?
         */
        // A discuter pour la suppresion totale de l'image dans une liste de bière
        // imgIcon.setImageResource(elem.getIcon());
        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.image_LB);
        imgIcon.setVisibility(View.GONE);
    }

    private void updateRating(View convertView, ElementListeBiere elem){
        ProgressBar pBRatingGlo = (ProgressBar) convertView.findViewById(R.id.progress_ratingGlo_LB);
        ProgressBar pBRatingPer = (ProgressBar) convertView.findViewById(R.id.progress_ratingPer_LB);
        TextView txtRatingGlo = (TextView) convertView.findViewById(R.id.textView_ratingGlo_LB);
        TextView txtRatingPer = (TextView) convertView.findViewById(R.id.textView_ratingPer_LB);

        if (elem.getRatingPer() != -1){
            pBRatingPer.setProgress((int)(elem.getRatingPer() * 10));
            txtRatingPer.setText("Personnelle : " + elem.getRatingPer() +" /10");
        } else {
            pBRatingPer.setVisibility(View.INVISIBLE);
            txtRatingPer.setVisibility(View.INVISIBLE);
        }
        if (elem.getRatingGlo() != -1){
            pBRatingGlo.setProgress((int) (elem.getRatingGlo() * 10));
            txtRatingGlo.setText("Globale : " + elem.getRatingGlo() + " /10");
        } else {
            pBRatingGlo.setProgress(0);
            txtRatingGlo.setText("Aucun avis!");
        }
    }
}
