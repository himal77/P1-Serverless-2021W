var express = require('express');
var app = express();

// parameter will be arrived in the res.
app.post('/run', (req, res) => res.send({"msg": "Hello world! I am from docker"}));
app.post('/init', (req, res) => res.status(200).send());
 
app.listen(8080, function () {
   console.log("I am listening from docker at 8080")
})