<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Masukkan Data Item</title>

<style>
	body {
		font-family:Arial, Helvetica, sans-serif;
		font-size:12px;
	}
	h1.title {
		color: #333;
	}
	a {
			color: red;
			text-decoration: none;
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
	
	#item {
		border:1px solid #666;
		padding:1px;
	}
	#item thead th {
		color : #FFF;
		text-align: left;
	}
	#item tbody td {
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
	code {
		background: #E95748;
		border: 1px solid #E95748;
		
		box-shadow: inset 0px 8px 0px -7px #FFFFFF;
		-webkit-box-shadow: inset 0px 8px 0px -7px #FFFFFF;
		-moz-box-shadow: inset 0px 8px 0px -7px #FFFFFF;
		-o-box-shadow: inset 0px 8px 0px -7px #FFFFFF;
		
		color: #fff;
		padding:2px 5px;
		border-radius:3px;
	}
</style>

<!-- Use JQuery -->
<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.min.js"></script>

<!-- DWR Includes -->
<script type='text/javascript' src='/itemcategory/dwr/interface/Item.js'></script>
<script type='text/javascript' src='/itemcategory/dwr/interface/Category.js'></script>
<script type='text/javascript' src='/itemcategory/dwr/engine.js'></script>
<script type='text/javascript' src='/itemcategory/dwr/util.js'></script>

<script>
	function clearField() {
		$("#item_id").val("");
		$("#name").val("");
		$("#qty").val("");
		$("#exp_date").val("");
		getCategories();
	}
	
	function reload() {
		clearField();
		getCategories();
		getAll(0, 10);
		generatePagination();
	}
	
	function getCategories() {
		Category.fetchAll(0, 200, {
			callback : function (data) {
				cfunctionCategory(data);
			}
		});
	}
	
	function cfunctionCategory(dataFromServer) {
		$("#category").empty();
		
		// Generate Option Field
		for (var i = 0; i < dataFromServer.length; i++) {
	        var option = "<option value='" + dataFromServer[i].id + "'>" + dataFromServer[i].name + "</option>";
	        $("#category").append(option);
	    }
	}
	
	function insert() {
		var itemId = $("#item_id").val();
		
		var name = $("#name").val();
		var qty = $("#qty").val();
		var expDate = $("#exp_date").val();
		var catIds = $("#category").val();
		
		if (name == null || name == "" || name == undefined) {
			alert("Name is required!");
		} else if (qty == null || qty == "" || qty == undefined) {
			alert("QTY is required!");
		} else if (expDate == null || expDate == "" || expDate == undefined) {
			alert("Expired Date is required!");
		} else if (catIds == null || catIds == "" || catIds == undefined) {
			alert("Category is required!");
		} else {
			if (itemId == null || itemId == "" || itemId == undefined) {
				Item.create(name, qty, expDate, catIds,{
					callback : function(str) {
						if (str == true) {
							alert("Successfully saved!");
							reload();
						} else {
							alert("Error saving data!");
						}
					}
				});	
			} else {
				Item.update(name, qty, expDate, catIds, itemId, {
					callback : function(str) {
						if (str == true) {
							alert("Successfully updated!");
							reload();
						} else {
							alert("Error updating data!");
						}
					}
				});
			}
		}
	}
	
	
	function formatDate(date) {
		var months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
		
		var strMonth;
		
		for(var i = 0; i < months.length; i++) {
			strMonth = months[date.getMonth()];
		}
		
		return strMonth + " " + date.getDate() + ", " + date.getFullYear();
	}
	
	function pad(n, width, z) {
	  z = z || '0';
	  n = n + '';
	  return n.length >= width ? n : new Array(width - n.length + 1).join(z) + n;
	}
	function formatDateEdit(date) {
		return date.getFullYear() + "-" + pad((date.getMonth()+1), 2) + "-" + date.getDate();
	}
	
	/* Generate Table function */
	function getAll(limit, offset) {
		Item.fetchAll(limit, offset, {
			callback : function (data) {
				callbackFunction(data);
			}
		});
	}
	
	function callbackFunction(dataFromServer) {
		$('#item tbody').empty();
		//console.log(dataFromServer)
		drawTable(dataFromServer);
	}
	
	function drawTable(data) {
	    for (var i = 0; i < data.length; i++) {
	        drawRow(data[i], i);
	    }
	}
	
	function drawRow(rowData, num) {
	    var row = $("<tr />")
	    $("#item").append(row); //this will append tr element to table... keep its reference for a while since we will add cels into it
	    //row.append($("<td>" + (num+1) + "</td>"));
	    row.append($("<td>" + rowData.name + "</td>"));
	    
	    var categories = rowData.categories;
	    var strCategory = "";
	    
	    for (var i = 0; i < categories.length; i++) {
	    	strCategory += categories[i].name;
	    	strCategory += ",";
	    }
	    strCategory = strCategory.substring(0, (strCategory.length-1));
	    
	    row.append($("<td>" + strCategory + "</td>"));
	    row.append($("<td>" + rowData.qty + "</td>"));
	    row.append($("<td>" + formatDate(rowData.expiredDate) + "</td>"));
	    row.append($("<td><button onclick=\"edit('" + rowData.id + "')\">Edit</button> <button onclick=\"del('" + rowData.id + "')\">Delete</button></td>"));
	}
	/* End generate table */
	
	function edit(id) {
		$("#item_id").val(id);
		
		Item.fetchById(id, {
			callback : function (data) {
				callbackFunctionEdit(data);
			}
		});
	}
	
	function callbackFunctionEdit(dataFromServer) {
		$("#name").val(dataFromServer.name);
		$("#qty").val(dataFromServer.qty);
		$("#exp_date").val(formatDateEdit(dataFromServer.expiredDate));
		
		var categories = dataFromServer.categories;
	    var selectedCat = [];
	    
	    for (var i = 0; i < categories.length; i++) {
	    	selectedCat.push(categories[i].id);
	    }
	    
	    $("#category").val(selectedCat);
	}
	
	function del(id) {
		if(confirm("Are you sure to delete this data?")) {
			Item.deleteItem(id, {
				callback : function (data) {
					if (data == true) {
						alert("Successfully deleted!");
						reload();
					} else {
						alert("Error deleting data!");
					}
				}
			});
		}
	}
	
	function generatePagination() {
		Item.fetchCount({
			callback : function (data) {
				callbackFunctionCount(data);
			}
		});
	}
	
	function callbackFunctionCount(dataFromServer) {
		buildPagination(dataFromServer, 5);
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
		reload();
	});
</script>

</head>
<body>
	<h1 class="title">Item</h1>
	<a href="/itemcategory">Home</a>
	<table border="0" cellpadding="5">
		<tr>
			<td><strong>Item Name</strong></td>
			<td>
				<input type="hidden" name="id" id="item_id" size="5" />
				<input type="text" name="name" id="name" />
			</td>
		</tr>
		<tr>
			<td valign="top"><strong>Category</strong><br/><br/><small>Use <code>Ctrl</code> or <code>⌘</code> to multiple select</small></td>
			<td>
				<select style="width:155px;height:150px;" name="category" id="category" multiple></select>
			</td>
		</tr>
		<tr>
			<td><strong>Qty</strong></td>
			<td>
				<input type="number" name="qty" id="qty" />
			</td>
		</tr>
		<tr>
			<td><strong>Expired Date</strong></td>
			<td>
				<input type="text" name="exp_date" id="exp_date" placeholder="yyyy-MM-dd" />
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>
				<input type="button" id="btnsave_item" onclick="insert()" value="Submit" />
				<input type="button" id="btnclear" onclick="clearField()" value="Clear" />
			</td>
		</tr>
	</table>
	
	<table id="item" border="0" cellpadding="5" cellspacing="0" width="900px">
	      <thead>
		    <!-- <th bgcolor="#CCF5FF" width="20px">No</th> -->
		    <th bgcolor="#199BD6" width="300px">Item Name</th>
		    <th bgcolor="#199BD6" width="300px">Categories</th>
		    <th bgcolor="#199BD6" width="30px">Qty</th>
		    <th bgcolor="#199BD6" width="300px">Expired Date</th>
		    <th bgcolor="#199BD6" width="150px">Action</th>
		  </thead>
		  <tbody>
		  </tbody>
		  
		  <tfoot align="center">
		  	<td colspan="5" bgcolor="#333" id="page"></td>
		  </tfoot>
	</table>
</body>
</html>