<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sudoku</title>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>

<link rel="stylesheet" href="css/sudoku.css" type="text/css" />
<script type="text/javascript" src="js/sudoku.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		
		var url = document.URL;
		var showBoard = "showBoardText.jsp";
		var baseUrl = url.substring(0, url.length - showBoard.length);
		
		xhttp = new XMLHttpRequest();
		xhttp.open("GET", baseUrl +"board.json", true);
		xhttp.send();

		xhttp.onreadystatechange = function() {
			if (xhttp.readyState == 4 && xhttp.status == 200) {
				$('#boarddiv').html(xhttp.responseText);
				
				var s = $('sudoku').empty;
		        makeSudoku('sudoku');
		        
 		        populateSudoku('sudoku', xhttp.responseText);
		        
			}
		};

		$(":button").click(function(event) {
			var myData = getBoardMoves('sudoku');
			req = new XMLHttpRequest();
			req.open("POST", baseUrl + "moves.json", true);
			req.setRequestHeader("Content-type", "application/json");
			req.setRequestHeader("Accept", "application/json");
			req.send(myData);

			req.onreadystatechange = function() {
				if (req.readyState == 4 && req.status == 200) {
					$('#movesdiv').html(req.responseText);
					
					var s = $('sudoku').empty;
			        makeSudoku('sudoku');
			        
			        populateSudoku('sudoku', req.responseText);
				}
			};

			event.preventDefault();
		});
	});
</script>  
 
</head>
<body>
	<center><h1>TEST SUDOKU</h1></center>
	
	<div id="sudoku"></div>
	
	<form>
		Board:&nbsp; <span id="boarddiv">Empty</span> 
		<br /> 
		Moves: <span
			id="movesdiv">Empty</span> <br />
		<button>Check moves</button>
	</form>

</body>
</html>