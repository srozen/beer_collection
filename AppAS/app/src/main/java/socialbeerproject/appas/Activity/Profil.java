package socialbeerproject.appas.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import socialbeerproject.appas.R;

public class Profil extends Activity implements View.OnClickListener {

    private Button chgPass;
    private Button chgMail;
    private Button chgAvatar;
    private Button addFriend;
    private Button lookFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        chgPass = (Button) findViewById(R.id.button_Chg_Pass);
        chgPass.setOnClickListener(this);
        chgMail = (Button) findViewById(R.id.button_Chg_Mail);
        chgMail.setOnClickListener(this);
        chgAvatar = (Button) findViewById(R.id.button_Chg_Avatar);
        chgAvatar.setOnClickListener(this);
        addFriend = (Button) findViewById(R.id.button_Friend_Add);
        addFriend.setOnClickListener(this);
        lookFriend = (Button) findViewById(R.id.button_Friends_View);
        lookFriend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_Chg_Pass:
                /* **********************
                    TODO : Changer le mot de passe d'un utilisateur

                      * 1 : Insérez ancien mot de passe
                      * 2 : Insérez 2x le nouveau mot de passe
                      * 3 : Vérifier si ancien mot de passe = correcte
                      * 4 : Modifier en base de données
                      * 5 : Envoyez un mail pour confirmer la modification

                   **********************
                 */
                break;
            case R.id.button_Chg_Mail:
                 /* **********************
                    TODO : Changer l'email d'un utilisateur

                      * 1 : Insérez mot de passe
                      * 2 : Insérez la nouvelle adresse mail
                      * 3 : Envoyez un mail à la nouvelle
                      * 4 : Recevoir une confirmation pour le changement de mail
                      * 5 : Envoyez un mail pour confirmer la modification

                   **********************
                 */
                break;
            case R.id.button_Chg_Avatar:
                 /* **********************
                    TODO : Changer l'avatar d'un utilisateur

                      * 1 : Checkez le level de l'utilisateur
                      * 2 : En f(x) du level, plusieur icônes disponibles
                      * 3 : Changer l'avatar principal en bd

                   **********************
                 */
                break;
            case R.id.button_Friend_Add:
                 /* **********************
                    TODO : Permettre l'ajout d'un ami
                   **********************
                 */
                break;
            case R.id.button_Friends_View:
                 /* **********************
                    TODO : Permettre la visualisation de ses amis
                   **********************
                */
                break;
        }
    }
}