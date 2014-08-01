function testAuthentication()
{
    var httpRequest;
    if (window.XMLHttpRequest)
    {// code for IE7+, and browsers you should be using.
        httpRequest = new XMLHttpRequest();
    }
    else
    {// code for browsers you should not be using.
        httpRequest = new ActiveXObject("Microsoft.XMLHTTP");
    }
    httpRequest.onreadystatechange = function()
    {
        if (httpRequest.readyState === 4 && httpRequest.status === 200)
        {
            var json = JSON.parse(httpRequest.responseText);
            document.getElementById("authRes").innerHTML = json.name;
        }
        else if (httpRequest.readyState < 4) {
            document.getElementById("authRes").innerHTML = "Working on it ...";
        } else {
            document.getElementById("authRes").innerHTML = "Something went wrong!(" + httpRequest.status + ")";
        }
    };
    httpRequest.open("GET", "http://localhost:8080/webapp/api/authentication/authenticate?subject="+document.getElementById("email").value+"&password="+document.getElementById("passwd").value, true);
    httpRequest.send();
}

function testServlet()
{
    var httpRequest;
    if (window.XMLHttpRequest)
    {// code for IE7+, and browsers you should be using.
        httpRequest = new XMLHttpRequest();
    }
    else
    {// code for browsers you should not be using.
        httpRequest = new ActiveXObject("Microsoft.XMLHTTP");
    }
    httpRequest.onreadystatechange = function()
    {
        if (httpRequest.readyState === 4 && httpRequest.status === 200)
        {
            document.getElementById("servRes").innerHTML = httpRequest.responseText;
        }
        else if (httpRequest.readyState < 4) {
            document.getElementById("servRes").innerHTML = "Working on it ...";
        } else {
            document.getElementById("servRes").innerHTML = "Something went wrong!(" + httpRequest.status + ")";
        }
    };
    httpRequest.open("GET", "http://localhost:8080/webapp/testServlet", true);
    httpRequest.send();
}


function testJersey()
{
    var httpRequest;
    if (window.XMLHttpRequest)
    {// code for IE7+, and browsers you should be using.
        httpRequest = new XMLHttpRequest();
    }
    else
    {// code for browsers you should not be using.
        httpRequest = new ActiveXObject("Microsoft.XMLHTTP");
    }
    httpRequest.onreadystatechange = function()
    {
        if (httpRequest.readyState === 4 && httpRequest.status === 200)
        {
            document.getElementById("jersRes").innerHTML = httpRequest.responseText;
        }
        else if (httpRequest.readyState < 4) {
            document.getElementById("jersRes").innerHTML = "Working on it ...";
        } else {
            document.getElementById("jersRes").innerHTML = "Something went wrong!(" + httpRequest.status + ")";
        }
    };
    httpRequest.open("GET", "http://localhost:8080/webapp/api/authentication", true);
    httpRequest.send();
}

