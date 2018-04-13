<?php
        //addscore.php?username=abcdef123&score=24&max_distance=19&distance=55&games_played=10&difficulty=1

        // Configuration
        $hostname = 'localhot';
        $port     = '3312';
        $username = 'yourusername';
        $password = 'yourpassword';
        $database = 'yourdatabase';

        $secretKey = "totojenajtajnejsikluc"; // Change this value to match the value stored in the app

        /*$realHash = md5($_GET['name'] . $_GET['score'] . $secretKey);
        if($realHash != $hash) {
            echo json_encode(array(
                'success' => false,
                'messages' => array(
                    'message' => 'Bad app key'
                )
            ));
            return;
        }*/

        try {
            $dbCon = new PDO('mysql:host='. $hostname .';port='.$port.';dbname='. $database, $username, $password);
        } catch(PDOException $e) {
            echo json_encode(array(
                'success' => false,
                'messages' => array(
                    'message' => 'Database connection error'
                )
            ));
            return;
        }

        $new_score = (int) $_GET['score'];
        $new_max_distance = (int) $_GET['max_distance'];
        $new_distance = (int) $_GET['distance'];
        $new_games_played = (int) $_GET['games_played'];
        $new_username = $_GET['username'];
        $difficulty = (int) $_GET['difficulty'];
        $last_update = date('Y-m-d H:i:s');
        $messages = array();

        $sql = 'SELECT * FROM scores WHERE username = :username AND difficulty = :difficulty';
        $stmt = $dbCon->prepare($sql);
        $stmt->execute(array(':username' => $new_username, ':difficulty' => $difficulty));
        $row = $stmt->fetch(PDO::FETCH_ASSOC);

        if($row){
            if($new_score > $row['score']){
                $row['score'] = $new_score;
                $message = 'New best score !';
                array_push($messages, $message);
            }
            if($new_max_distance > $row['max_distance']){
                $row['max_distance'] = $new_max_distance;
                $message = 'New best max distance !';
                array_push($messages, $message);
            }
            if($new_games_played > $row['games_played']) $row['games_played'] = $new_games_played;
            if($new_distance > $row['distance']) $row['distance'] = $new_distance;

            $sql = 'UPDATE scores SET score=?, max_distance=?, distance=?, games_played=?, last_update=? WHERE id=? AND difficulty=?';
            $stmt= $dbCon->prepare($sql);
            $stmt->execute([$row['score'],  $row['max_distance'],  $row['distance'],  $row['games_played'], $last_update, $row['id'], $difficulty]);
        }else{
            $sql = 'INSERT INTO scores (score, max_distance, distance, games_played, username, last_update, difficulty) VALUES (?,?,?,?,?,?,?)';
            $stmt= $dbCon->prepare($sql);
            $stmt->execute([$new_score, $new_max_distance,  $new_distance, $new_games_played, $new_username, $last_update, $difficulty]);
        }

        echo json_encode(array(
            'success' => true,
            'messages' => $messages
        ));
?>