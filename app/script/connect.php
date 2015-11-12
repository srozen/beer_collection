<?php
include("config.php");

class DB_CONNECT {
 
    // constructeur
    function __construct() {
        $this->connect();
    }
 
    //ferme la connection
    function __destruct() {
        $this->close();
    }

	//connection à la base de données. 
    function connect() {
 
        $con = mysql_connect(DB_SERVER, DB_USER, DB_PASSWORD) or die(mysql_error());
 
        $db = mysql_select_db(DB_DATABASE) or die(mysql_error()) or die(mysql_error());
 
        return $con;
    }
 
     //ferme la base de données.
    function close() {
        mysql_close();
    }
 
}
 
?>
