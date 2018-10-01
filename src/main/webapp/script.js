function getPage(next) {
		$('#body').load(next + ".html");
	}
	getPage("login");
	getUsername();
	
	function getLogout() {
		let xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function () {
			if (this.readyState == 4 && this.status == 200) {
					getPage("login");
			}
		};
		xhttp.open("get", "/Project1/logut.do", true);
		xhttp.send();
	}
	function submitForm() {
        const username = document.getElementById("username").value;
        const password = document.getElementById("password").value;
        document.getElementById("username").value = "";
        document.getElementById("password").value = "";
        console.log(username,password);
        let xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 201) {
            	getPage("manager");
                getUsername();
            }
            if (this.readyState == 4 && this.status == 202) {
            	getPage("employee");
            	getUsername();
            }
        };
        xhttp.open("post", "/Project1/login/submit.do", true);
        xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded")
        xhttp.send("username=" + username + "&password=" + password);
    }

	function getUsername() {
	    $.get("/Project1/username.do",function(data){
	    	$("#hello").html("Hello "+data)
	    })
	}
	function createRequest() {
		getPage("create");
	}
	function submitRequest() {
		console.log( document.getElementById("value").value)
	    const value = document.getElementById("value").value;
	    document.getElementById("value").value = "";
	    let xhttp = new XMLHttpRequest();
	    xhttp.onreadystatechange = function () {
	        if (this.readyState == 4 && this.status == 201) {
	            getPage("employee");
	            getUsername();
	        }
	    };
	    xhttp.open("post", "/Project1/employee/reimburse/submit.do", true);
	    xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded")
	    xhttp.send("value=" + value);
	}
	function getDashboard() {
	    //for return to dashboard
		 let xhttp = new XMLHttpRequest();
	     xhttp.onreadystatechange = function () {
	         if (this.readyState == 4 && this.status == 201) {
	             getPage("manager");

	         }else if (this.readyState == 4 && this.status == 202) {
	             getPage("employee");

	         }
	         getUsername();
	     };
	     xhttp.open("get", "/Project1/dashboard.do", true);
	     xhttp.send();
	}
	function viewListPending(){
		getPage("lists");
		viewPending();
		getUsername();
	}
	
	function viewPending() {
	    /* let xhttp = new XMLHttpRequest();
	    xhttp.onreadystatechange = function () {
	        if (this.readyState == 4 && this.status == 200) {
	        	document.getElementById('body').append("<ul>");
	            for (let i = 0; i<this.responseText.length; i++){
	            	document.getElementById('body').append("<li>" + this.responseText[i] +"</li>");
	            }
	        	document.getElementById('body').append("</ul>");
	        }
	    };
	    xhttp.open("get", "/Project1/employee/pending.do", true);
	    xhttp.send(); */
	    $.get("/Project1/employee/pending.do",function(data){
	    	obj = JSON.parse(data)
	    	console.log(obj);
	    	for (let i = 0; i<obj.length;i++){
	    		$('#list').append("<tr><td> Request ID: " + obj[i].id+ "</td><td> Value: " + obj[i].value + "</td></tr>");
	    	}
	    })
	}
	function viewListResolved(){
		getPage("lists");
		viewResolved();
		getUsername();
	}
	function viewResolved(){
		$.get("/Project1/employee/resolved.do",function(data){
			console.log(data);
			obj = JSON.parse(data)
	    	console.log(obj);
	    	for (let i = 0; i<obj.length;i++){
	    		$('#list').append("<tr> <td> Request ID: " + obj[i].id+ "</td><td> Value: " + obj[i].value + "</td><td> Status: "+obj[i].status + "</td><td> Decided By: "+obj[i].decided_by+"</td></tr>");
	    	}
		})
	}
	
	function viewInfo() {
		getPage("info");
		getInfo();
	}
	function getInfo() {
		$('form').ready(function () {
			$.get('/Project1/employee/info/get.do', function (data) {
				data1 = JSON.parse(data);
				console.log(data1);
				$("#first_name").val(data1.first_name);
				$('#last_name').val(data1.last_name);
			})
		})

	}
	function updateInfo(){
		console.log( document.getElementById("first_name").value);
		console.log( document.getElementById("last_name").value);
	  	let xhttp = new XMLHttpRequest();
	    xhttp.onreadystatechange = function () {
	        if (this.readyState == 4 && this.status == 201) {
	        	getPage("employee")
	        	getUsername();
	        }
	    };
	    xhttp.open("post", "/Project1/employee/info/submit.do", true);
	    xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded")
	    xhttp.send("first_name=" + document.getElementById("first_name").value + "&last_name=" + document.getElementById("last_name").value);
	    getPage("employee");
	}
	function viewAllPending(){
		getPage("lists");
		getUsername();
		$.get("/Project1/manager/pending.do", function(data){
		obj = JSON.parse(data);
		console.log(obj);
		for (let i = 0; i<obj.length;i++){
    		$('#list').append("<tr><td> Request ID: " + obj[i].id+ "</td><td> Value: " + obj[i].value + "</td><td> Status: "+obj[i].status + "</td><td> Submitted By: "+obj[i].submitted_by+"</td></tr>");
    	}
		})
		
	}
	function viewAllResolved(){
		getPage("lists");
		getUsername();
		$.get("/Project1/manager/resolved.do", function(data){
		obj = JSON.parse(data);
		console.log(obj);
		for (let i = 0; i<obj.length;i++){
    		$('#list').append("<tr><td> Request ID: " + obj[i].id+ " </td><td>Value: " + obj[i].value + "</td><td> Status: "+obj[i].status + "</td> <td>Submitted By: "+obj[i].submitted_by+ "</td><td>Decided By: "+obj[i].decided_by+"</td></tr>");
    	}
		})
	}
	function getByEmployee(){
		username = document.getElementById("view_username").value;
		getPage("lists");
		viewAllByEmployee(username);
		getUsername();
		
	}
	function viewAllByEmployee(username){
		console.log(username);
		let xhttp = new XMLHttpRequest();
	    xhttp.onreadystatechange = function () {
	        if (this.readyState == 4 && this.status == 200) {
	        	obj = JSON.parse(this.responseText);
	        	console.log(this.responseText);
	    		for (let i = 0; i<obj.length;i++){
	        		$('#list').append("<tr><td> Request ID: " + obj[i].id+ "</td><td> Value: " + obj[i].value + "</td><td> Status: "+obj[i].status + "</td><td> Submitted By: "+obj[i].submitted_by+ "</td><td> Decided By: "+obj[i].decided_by+"</td></tr>");
	        	}
	        	
	        }
	    };
	    xhttp.open("post", "/Project1/manager/employee/request.do", true);
	    xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded")
	    xhttp.send("username=" + username);
	}
	function updateRequest() {
		
		requestID = document.getElementById("request_id").value;
		requestStatus = document.getElementById("request_status").value;
		console.log(requestID);
		console.log(requestStatus);
		let xhttp = new XMLHttpRequest();
	    xhttp.onreadystatechange = function () {
	        if (this.readyState == 4 && this.status == 200) {
	        	getPage("manager");
	        }
	    };
	    xhttp.open("post","/Project1/manager/pending/update.do", true);
	    xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded")
	    xhttp.send("id=" + requestID + "&status="+ requestStatus);
	}