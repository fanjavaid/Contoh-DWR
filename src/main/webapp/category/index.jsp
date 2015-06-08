<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Form Category</title>

<style>
	body {
		font-family:Arial, Helvetica, sans-serif;
		font-size:12px;
	}
	h1.title {
		color: #333;
	}
	#page a {
		text-decoration:none;
		color:#ddd;
		float:left;
		display:block;
		background: #666;
		width:20px;
		margin:0 2px 0 0;
	}
	#page a:hover {
		color:#fff;
	}
	
	#category {
		border:1px solid #666;
		padding:1px;
	}
	#category thead th {
		color : #FFF;
		text-align: left;
	}
	#category tbody td {
		border-bottom : 1px solid #eee;
	}
	
	input[type=button], button {
		font-size:11px;
		background:#aaa;
		border:1px solid transparent;
		color:#FFF;
	}
	input[type=button]:active, button:active {
		background:#666;
	}
</style>

<!-- Use JQuery -->
<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.min.js"></script>

<!-- 
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery.dynatable.js"></script>
<link href="${pageContext.request.contextPath}/jquery.dynatable.css" rel="stylesheet" type="text/css" />
 -->

<!-- Data Tables Library -->
<script type="text/javascript" src="${pageContext.request.contextPath}/datatables/media/js/jquery.dataTables.js"></script>
<link href="${pageContext.request.contextPath}/datatables/media/css/jquery.dataTables.css" rel="stylesheet" type="text/css" />

<!-- DWR Includes -->
<script type='text/javascript' src='/itemcategory/dwr/interface/Category.js'></script>
<script type='text/javascript' src='/itemcategory/dwr/engine.js'></script>
<script type='text/javascript' src='/itemcategory/dwr/util.js'></script>

<script>
	function insert() {
		var name = $("#name").val();
		
		if (name == null || name == "" || name == undefined) {
			alert("Category Name is required!");
		} else {
			// Ambil ID from hidden textfield
			var catId = $("#cat_id").val();
			
			if (catId == "" || catId == null || catId == undefined) {
				// Do DWR Job...
				Category.create(name, {
					callback : function (str) {
						if (str == true) {
							alert("Successfully saved!");
						} else{
							alert("Error saving data!");
						}
					}
				});
			} else {
				// Update Data...
				Category.update(name, catId, {
					callback : function (str) {
						if (str == true) {
							alert("Successfully updated!");
						} else{
							alert("Error updating data!");
						}
					}
				});
			}
			
			//location.reload();
			getAll(0, 5);
			generatePagination();
		}
		
		$("#name").val("");
	}
	
	function edit(id) {
		Category.fetchById(id, {
			callback : function(data) {
				editCallFunc(data);
			}
		})
	}
	
	function editCallFunc(dataFromServer) {
		$("#name").val(dataFromServer.name);
		$("#cat_id").val(dataFromServer.id);
	}
	
	function del(id) {
		if (confirm("Are you yakin to hapus ini data ?? Beneran?? Heh??")) {
			// Delete Data...
			Category.deleteCat(id, {
				callback : function (str) {
					if (str == true) {
						alert("Successfully deleted!");
						
					} else{
						alert("Error deleting data!");
					}
				}
			});
			
			//location.reload();
			getAll(0, 5);
			generatePagination();
		}
	}
	
	function getAll(limit, offset) {
		Category.fetchAll(limit, offset, {
			callback : function (data) {
				callbackFunction(data);
			}
		});
	}
	
	function callbackFunction(dataFromServer) {
		$('#category tbody').empty();
		
		drawTable(dataFromServer);
	}
	
	function generatePagination() {
		Category.fetchCount({
			callback : function (data) {
				callbackFunctionCount(data);
			}
		});
	}
	function callbackFunctionCount(dataFromServer) {
		buildPagination(dataFromServer, 5);
	}
	
	/* Generate Table function */
	function drawTable(data) {
	    for (var i = 0; i < data.length; i++) {
	        drawRow(data[i], i);
	    }
	}
	
	function drawRow(rowData, num) {
	    var row = $("<tr />")
	    $("#category").append(row); //this will append tr element to table... keep its reference for a while since we will add cels into it
	    //row.append($("<td>" + (num+1) + "</td>"));
	    row.append($("<td>" + rowData.name + "</td>"));
	    row.append($("<td><button onclick=\"edit('" + rowData.id + "')\">Edit</button> <button onclick=\"del('" + rowData.id + "')\">Delete</button></td>"));
	}
	/* End generate table */
	
	function clearField() {
		$("#name").val("");
	}
	
	function buildPagination(totalData, maxPage) {
		// Generate page
		var pages = Math.ceil(totalData / maxPage);
		var strPages = "";
		var firstIndex;
		for (var i = 1; i <= pages; i++) {
			firstIndex = (i-1) * maxPage;
			strPages += "<a href='#' onclick='getAll(" + firstIndex + "," + maxPage + ")'>" + i + "</a>";
			strPages += "&nbsp;";
		}
		$("#page").html(strPages);
	}
	
	$(document).ready(function(){
		getAll(0, 5);
		
		generatePagination();
	});
</script>

</head>
<body>
	<h1 class="title">Category</h1>
	<table border="0" cellpadding="5">
		<tr>
			<td><strong>Catgory Name</strong></td>
			<td>
				<input type="hidden" name="id" id="cat_id" size="5" />
				<input type="text" name="name" id="name" />
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>
				<input type="button" id="btnsave_category" onclick="insert()" value="Submit" />
				<input type="button" id="btnclear" onclick="clearField()" value="Clear" />
			</td>
		</tr>
	</table>
	
	<table id="category" border="0" cellpadding="5" cellspacing="0" width="500px">
	      <thead>
		    <!-- <th bgcolor="#CCF5FF" width="20px">No</th> -->
		    <th bgcolor="#199BD6" width="300px">Category</th>
		    <th bgcolor="#199BD6" width="80px">Action</th>
		  </thead>
		  <tbody>
		  </tbody>
		  
		  <tfoot align="center">
		  	<td colspan="3" bgcolor="#333" id="page"></td>
		  </tfoot>
	</table>
	
</body>
</html>