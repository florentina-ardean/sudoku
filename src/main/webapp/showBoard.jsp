<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sudoku</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script>
	$(document).ready(function() {

		xhttp = new XMLHttpRequest();
		xhttp.open("GET", "/sudoku/board.json", true);
		xhttp.send();

		xhttp.onreadystatechange = function() {
			if (xhttp.readyState == 4 && xhttp.status == 200) {
				$('#boarddiv').html(xhttp.responseText);
			}
		};

		$(":button").click(function(event) {
			var myData = "98";

			req = new XMLHttpRequest();
			req.open("POST", "/sudoku/moves.json", true);
			req.setRequestHeader("Content-type", "application/json");
			req.setRequestHeader("Accept", "application/json");
			req.send(myData);

			req.onreadystatechange = function() {
				if (req.readyState == 4 && req.status == 200) {
					$('#movesdiv').html(req.responseText);
				}
			};

			event.preventDefault();
		});
	});
</script>
</head>
<body>
	<h1>Hello Sudoku!</h1>

	<form>
		Board: <span id="boarddiv">Empty</span> <br /> Moves: <span
			id="movesdiv">Empty</span> <br />
		<button>Check moves</button>
	</form>

</body>
</html>