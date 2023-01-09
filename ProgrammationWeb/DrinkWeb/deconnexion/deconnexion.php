<?php
    session_start();

    // Déconnexion en détruisant la session
    session_destroy();
    session_write_close();
    header('Location: ../index.php');

?>