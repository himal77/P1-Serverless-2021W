var express = require('express');
var app = express();
 
app.get('/', (req, res) => res.send('Hello world! I am from docker'));
 
app.listen(8080, function () {
   console.log("I am listening from docker at 8080")
})