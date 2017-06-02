(function() {

  console.log("Script loaded");
  var resultsDiv = document.getElementById("resultsContainer");

  document.getElementById("analyzeButton").addEventListener("click", submitForm);
  document.getElementById("urlForm").addEventListener("submit", function(event) {
    console.log("The form is submitted")
    event.preventDefault();
    submitForm();
  });

  function submitForm() {
    var req = new XMLHttpRequest();
    var urlInput = document.getElementById("urlInput");
    req.open('GET', "http://localhost:8080/api/analyze?url=" + encodeURIComponent(urlInput.value));

    req.onload = function() {
      if (req.status >= 200 && req.status < 400) {
        // var ourData = JSON.parse(req.responseText);
        var ourData = JSON.parse(req.responseText);
        //resultsDiv.innerHTML = "<p>" + ourData + "</p>";
        parseData(ourData);
      } else {
        console.log("We connected to the server, but it returned an error.");
        var ourData = JSON.parse(req.responseText);
        console.log(ourData);
      }

    };
    req.onerror = function() {
      console.log("Connection error");
    };

    req.send();
  }

  function parseData(data) {
    var table = new ResulTable();
    table.addDataRow("URL", decodeURIComponent(data.url), true)
      .addDataRow("HTML Version", data.htmlVersion, true)
      .addDataRow("Title", data.title || "<em>none</em>", true)
      .addSectionRow("Headers:");
    for (var i = 1; i <= 6; i++) {
      table.addDataRow("h" + i, data.headers["h" + i]);
    }
    table.addSectionRow("Number of Hypermedia Links:")
      .addDataRow("Internal", data.linkCount.internal)
      .addDataRow("External", data.linkCount.external);
    table.addDataRow("Login Form", data.hasLogin? "Yes" : "No", true);
    resultsDiv.innerHTML = table.toHtml();

  }

  function ResulTable() {
    this.rows = [];
  };


  ResulTable.prototype.addDataRow = function(key, value, emphasis) {
    this.rows.push(new DataRow(key, value, emphasis));
    return this;
  };

  ResulTable.prototype.addSectionRow = function(key) {
    this.rows.push(new SectionRow(key));
    return this;
  };

  ResulTable.prototype.toHtml = function() {
    var html = "<table width='500'>";
    this.rows.forEach(function(r) {
      html += r.toHtml()
    });
    html += "</table>";
    return html;
  }



  function DataRow(key, value, emphasis) {
    this.key = key;
    this.value = value;
    this.emphasis = emphasis || false;
  }
  DataRow.prototype.toHtml = function() {
    var html = "";
    if (this.emphasis) {
      html += "<tr><td width='190' class='title'>";
    } else {
      html += "<tr><td width='190'>";
    }
    html += this.key;
    html += "</td><td width='294'>" + this.value + "</td></tr>";
    return html;
  }

  function SectionRow(key) {
    this.key = key;
  }

  SectionRow.prototype.toHtml = function() {
    var html = "<tr><td colspan='2' class='title'>";
    html += this.key;
    html += "</td></tr>";
    return html;
  }

}())
