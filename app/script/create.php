<?php
 
// tableau avec la réponse de Jason.
$response = array();
 
// Crée un nouveau compte.
function create_compte($login, $password, $mail){

	if (isset($login) && isset($password) && isset($mail)) {
 
		//connection à la bdd.
		$db = new DB_CONNECT();
	 
		// mysql inserting a new row
		$result = mysql_query("INSERT INTO compte(login, password, mail) VALUES('$login', '$password', '$mail')");
	 
		if ($result) {
	       	 // la requête a réussi
		}

		// echo jason.
		echo json_encode($response);

		} else {
		//champs manquant
	 
		// echoing JSON response
		echo json_encode($response);
		}
	}
}

 

function get_biere($nom){
	//connection à la bdd.
	$db = new DB_CONNECT();
	 
	// check for post data
	if (isset($nom)) {

	 
	    // retourne une bière.
	    $result = mysql_query("SELECT * FROM biere WHERE nom = $nom");
	 
	    if (!empty($result)) {
		// on vérifie qu'il y a bien un résultat
		if (mysql_num_rows($result) > 0) {
	 
		    $result = mysql_fetch_array($result);
	 
		    $product = array();
		    $product["nom"] = $result["nom"];
		    $product["prix"] = $result["prix"];
		    $product["gout"] = $result["gout"];

		    $response["success"] = 1;
	 

		    $response["product"] = array();
	 
		    array_push($response["product"], $product);
	 
		    // echo jason.
		    echo json_encode($response);
		} else {
		    // pas de bière trouvée
		    $response["success"] = 0;
	 
		    // echo Jason.
		    echo json_encode($response);
		}

	    } else {
		//pas de produit trouvé
		//qqchose à faire?

		//echo Jason.
		echo json_encode($response);
	    }

	} else {
	    // champs manquants.
	 
	    //echo Jason.
	    echo json_encode($response);
	}
}
?>
