package be.example.socialbeer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Acceuil extends Activity {
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_acceuil);
		
		setUpLoginButton();
		setUpInscriptionButton();
		setUpIntroText();
	}

	private void setUpIntroText() {
		TextView intro = (TextView) findViewById(R.id.intro);
		
		intro.setText("Bienvenue sur SocialBeer.");
	}

	private void setUpInscriptionButton() {
		Button inscription = (Button) findViewById(R.id.confirmation);
		
		
		inscription.setOnClickListener(new View.OnClickListener() {
            
			
			  public void onClick(View v) {
			    Intent intent = new Intent(Acceuil.this, Inscription.class);
			    startActivity(intent);
			    }
			});
	}

	private void setUpLoginButton() {
		// get reference
		Button login = (Button) findViewById(R.id.login);
		
		// set listener
		login.setOnClickListener(new View.OnClickListener() {
            
			
			  public void onClick(View v) {
			    Intent intent = new Intent(Acceuil.this, Login.class);
			    startActivity(intent);
			    }
			});
	}
}
