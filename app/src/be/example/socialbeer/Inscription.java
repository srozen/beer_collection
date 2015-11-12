package be.example.socialbeer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Inscription extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inscription);
		
		setUpPseudoText();
		setUpmdpText();
		setUpMailtext();
		setUpInscriptionButton();
	}

	private void setUpInscriptionButton() {
		Button inscription = (Button) findViewById(R.id.confirmation);
		
		inscription.setOnClickListener(new View.OnClickListener() {
            
			
			  public void onClick(View v) {
			    Intent intent = new Intent(Inscription.this, MenuP.class);
			    startActivity(intent);
			    }
			});
		
	}

	private void setUpMailtext() {
		EditText mail = (EditText) findViewById(R.id.mail);
		
	}

	private void setUpmdpText() {
		EditText mdp = (EditText) findViewById(R.id.psw);
		
	}

	private void setUpPseudoText() {
		EditText pseudo = (EditText) findViewById(R.id.nickname);
		
	}
}
