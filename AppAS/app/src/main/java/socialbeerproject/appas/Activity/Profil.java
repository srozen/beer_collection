package socialbeerproject.appas.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import socialbeerproject.appas.Divers.MD5Util;
import socialbeerproject.appas.R;
import socialbeerproject.appas.Serveur.ImageHTTP;
import socialbeerproject.appas.Serveur.ServeurCom;

public class Profil extends ActivityCom implements View.OnClickListener {

    private Button chgPass;
    private Button chgAvatar;
    private Button addFriend;
    private Button lookFriend;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        this.addListener();

        this.demandeProfil();
    }

    private void addListener(){
        chgPass = (Button) findViewById(R.id.button_Chg_Pass);
        chgPass.setOnClickListener(this);
        chgAvatar = (Button) findViewById(R.id.button_Chg_Avatar);
        chgAvatar.setOnClickListener(this);
        addFriend = (Button) findViewById(R.id.button_Friend_Add);
        addFriend.setOnClickListener(this);
        lookFriend = (Button) findViewById(R.id.button_Friends_View);
        lookFriend.setOnClickListener(this);
        back = (Button) findViewById(R.id.button_retour_profil);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_Chg_Pass:
                this.messBtnChgMdp();
                break;
            case R.id.button_Chg_Avatar:
                this.messBtnChgAvatar();
                break;
            case R.id.button_Friend_Add:
                this.messBtnAjAmi();
                break;
            case R.id.button_Friends_View:
                 /* **********************
                    TODO : Permettre la visualisation de ses amis
                   ***********************/
                break;

            case R.id.button_retour_profil:
                finish();
                break;
        }
    }

    private void messBtnChgMdp(){
        AlertDialog alertMdp = new AlertDialog.Builder(this,R.style.MyAlertDialogStyle).create();

        alertMdp.setTitle("Changement de mot de passe");
        alertMdp.setMessage("Connecter vous sur notre site web : www.beercollection.be");

        alertMdp.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertMdp.show();
    }

    private void messBtnChgAvatar(){
        AlertDialog alertAvatar = new AlertDialog.Builder(this,R.style.MyAlertDialogStyle).create();

        alertAvatar.setTitle("Changement d'avatar");
        alertAvatar.setMessage("Pour changer votre avatar,vous devez utiliser le système de gravatar");

        alertAvatar.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertAvatar.show();
    }

    private void messBtnAjAmi(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
        alert.setTitle("Ajouter un ami");
        alert.setMessage("Pour ajouter un ami, remplissez le champ ci-dessous avec son nom d'utilisateur.");
        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                requeteAjoutAmi(input.getText().toString());
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });

        alert.show();
    }

    private void requeteAjoutAmi(String name){
        SharedPreferences log = getSharedPreferences("Login", MODE_PRIVATE);
        ServeurCom ser = new ServeurCom((RelativeLayout) findViewById(R.id.rel_titre_profil), this);

        ser.ajoutAmi(name, log.getString("idUser", "0"));
    }

    private void demandeProfil(){
        SharedPreferences log = getSharedPreferences("Login", MODE_PRIVATE);
        if (log.getString("idUser","n/a")!="n/a"){
            ServeurCom ser = new ServeurCom((RelativeLayout) findViewById(R.id.rel_titre_profil), this);
            ser.profil(log.getString("idUser", "0"));
        }
    }

    private void modifProfil(JSONObject rep){
        TextView username = (TextView) findViewById(R.id.textView_username_profil);
        TextView mail = (TextView) findViewById(R.id.textView_mail_profil);
        TextView txtProgColl = (TextView) findViewById(R.id.textView_valeurCollection_profil);
        ProgressBar progressColl = (ProgressBar) findViewById(R.id.progress_collection_profil);
        ImageView avatar = (ImageView) findViewById(R.id.img_avatar_profil);

        try {
            username.setText(rep.getString("login"));
            mail.setText(rep.getString("email"));
            txtProgColl.setText(rep.getString("nbBeers") + "/" + rep.getString("totalBeers"));
            progressColl.setProgress(getValuePro(rep.getInt("nbBeers"),rep.getInt("totalBeers")));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.demandeGravatar(avatar, mail.getText().toString());
    }

    private void demandeGravatar(ImageView img, String mail){
        String hash = MD5Util.md5Hex(mail);
        new ImageHTTP(img).execute(ImageHTTP.cheminGravatar + hash + ".jpg?s=200");
        System.out.println(hash);
    }

    @Override
    public void communication(JSONObject rep) {
        if(rep != null && rep.has("login")){
            this.modifProfil(rep);
        } else if (false) { // Réponse ajout ami

        }
    }

    public static int getValuePro(int i, int i2){
        double res = ((double)i/(double)i2)*100;
        return (int)res;
    }
}