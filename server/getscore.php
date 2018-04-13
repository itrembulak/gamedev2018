<?php
    //getscore.php?order_by=score&limit=5

    // Configuration
    $hostname = 'localhot';
    $port     = '3312';
    $username = 'yourusername';
    $password = 'yourpassword';
    $database = 'yourdatabase';

    $secretKey = "totojenajtajnejsikluc"; // Change this value to match the value stored in the app
 
    try {
        $dbh = new PDO('mysql:host='. $hostname .';port='.$port.';dbname='. $database, $username, $password);
    } catch(PDOException $e) {
        echo json_encode(array(
                'success' => false,
                'messages' => array(
                    'message' => 'Database connection error'
                )
            )); 
            return;
    }

    $order = 'id';
    switch ($_GET['order_by']) {
        case 'distance': $order = 'distance'; break;
        case 'max_distance': $order = 'max_distance'; break;
        case 'games_played': $order = 'games_played'; break;
        default: case 'score': $order = 'score'; break;
    }
    if($_GET['limit'] > 0)
        $limit = (int) $_GET['limit'];
    else
        $limit = 5;

    $stmt = $dbh->prepare('SELECT * FROM scores ORDER BY '.$order.' DESC LIMIT ' . $limit);
    $stmt->execute();
    $result = $stmt->fetchAll(); 
    
    $data = array();
    foreach($result as $row){
        array_push($data, [
            'id' => $row['id'],
            'score' => $row['score'],
            'max_distance' => $row['max_distance'],
            'distance' => $row['distance'],
            'games_played' => $row['games_played'],
            'username' => $row['username'],
            'last_update' => $row['last_update']
        ]);
    }

    echo json_encode(array(
        'success' => true,
        'data' => $data
    ));
?>