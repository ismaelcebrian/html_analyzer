(function() {

  console.log("Script loaded");
  var resultsDiv = document.getElementById("resultsContainer");

  document.getElementById("analyzeButton").addEventListener("click", function() {
    console.log("button clicked");
    var req = new XMLHttpRequest();
    var urlInput = document.getElementById("urlInput");
    console.log(urlInput.value);
    console.log(encodeURIComponent(urlInput.value));
    req.open('GET', "http://localhost:8080/api/analyze?url=" + encodeURIComponent(urlInput.value));

    req.onload = function() {
      if (req.status >= 200 && req.status < 400) {
        // var ourData = JSON.parse(req.responseText);
        var ourData = JSON.parse(req.responseText);
        //resultsDiv.innerHTML = "<p>" + ourData + "</p>";
        console.log(ourData);
        parseData(ourData);
      } else {
        console.log("We connected to the server, but it returned an error.");
      }

    };
    req.onerror = function() {
      console.log("Connection error");
    };

    req.send();
  });

  function parseData(data) {
    var table = new ResulTable();
    table.addDataRow("URL", decodeURIComponent(data.url), true)
      .addDataRow("Title", data.title || "<em>none</em>", true)
      .addSectionRow("Headers");
    for (var i = 1; i <= 6; i++) {
      table.addDataRow("h" + i, data.headers["h" + i]);
    }
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
    var html = "<table>";
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
    var html = "<tr><td>";
    if (this.emphasis) html += "<strong>";
    html += this.key;
    if (this.emphasis) html += "</strong>";
    html += "</td><td>" + this.value + "</td></tr>";
    return html;
  }

  function SectionRow(key) {
    this.key = key;
  }

  SectionRow.prototype.toHtml = function() {
    var html = "<tr><td colspan='2'><strong>";
    html += this.key;
    html += "</strong></td></tr>";
    return html;
  }

}())
