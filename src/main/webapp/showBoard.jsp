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
		var showBoard = "showBoard.jsp";
		var baseUrl = url.substring(0, url.length - showBoard.length);
		
		
		xhttp = new XMLHttpRequest();
		xhttp.open("GET", baseUrl + "sudokuboard.json", true);
		xhttp.send();

		xhttp.onreadystatechange = function() {
			if (xhttp.readyState == 4 && xhttp.status == 200) {
			
				var s = $('sudoku').empty;
		        makeSudoku('sudoku');
		        
                var jsonData = JSON.parse(xhttp.responseText);
                populateSudokuFromObject('sudoku', jsonData);
			}
		};

		$(":button").click(function(event) {
			var myData = getBoardMovesJSON('sudoku');
			
			req = new XMLHttpRequest();
			req.open("POST", baseUrl + "sudokumoves.json", true);
			req.setRequestHeader("Content-type", "application/json");
			req.setRequestHeader("Accept", "application/json");
			req.send(myData);

			req.onreadystatechange = function() {
				if (req.readyState == 4 && req.status == 200) {
					var s = $('sudoku').empty;
			        makeSudoku('sudoku');
			        
			        
			        var jsonData = JSON.parse(req.responseText);
			        populateSudokuFromObject('sudoku', jsonData);
				}
			};

			event.preventDefault();
		});
	});
</script>  
 
</head>
<body>
	<center><h1>TEST SUDOKU</h1>
	
	<div id="sudoku"></div>
	
	<form>
		<br /> 
		<button>Validate moves</button>
	</form>
	
	</center>

</body>
</html>