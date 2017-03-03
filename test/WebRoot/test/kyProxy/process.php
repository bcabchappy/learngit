 <?php 
2 $data = json_decode(file_get_contents("php://input"));
3 header("Content-Type: application/json; charset=utf-8");
4 echo ('{"id" : ' . $data->id . ', "age" : 24, "sex" : "boy", "name" : "huangxueming"}');
5 ?>