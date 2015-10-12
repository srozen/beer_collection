package be.example.socialbeer;

import org.w3c.dom.Text;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Login extends Activity {
	private String nom;
	private String pass;
	TextView mdp;
	TextView pseudo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		setUpPseudoText();
		setUpMdpText();
		setUpConnectButton();
	}

	private void setUpConnectButton() {
		Button connection = (Button) findViewById(R.id.connection);
		connection.setOnClickListener(new View.OnClickListener() {
            
			
			  public void onClick(View v) {
				nom=pseudo.getText().toString();
				pass=mdp.getText().toString();
				
				Intent intent = new Intent(Login.this, MenuP.class);
				startActivity(intent);
			  }
			});
	}

	private void setUpMdpText() {
		mdp = (TextView) findViewById(R.id.mdp);
	}

	private void setUpPseudoText() {
		pseudo = (TextView) findViewById(R.id.mail);
	}
}
