// index.js

var fs = require('fs')
var AWS = require('aws-sdk');
var lambda = new AWS.Lambda({
   'region': 'us-east-1'
});

var prompt = require('prompt');
prompt.message = '';
prompt.delimiter = '';
var schema = {
   properties: {
      text: {
         description: 'Enter input text:',
         required: true
      },
      font: {
         description: 'Enter font:',
         default: 'Arial',
         required: false
      },
      size: {
         description: 'Enter font size:',
         default: '20',
         required: false
      }
   }
};


// the request object to build and sent to lambda
var request = {};
var outputfile = '' + (new Date().getTime()) + '.png';

prompt.start();
prompt.get(schema, function (err, result) {
    request.text = result.text;
    request.font = result.font;
    request.size = result.size;
    var payload = JSON.stringify(request);
   
    console.log('User inputs:');
    console.log('  text: ' + result.text);
    console.log('  font: ' + result.font);
    console.log('  size: ' + result.size);
    console.log('Payload: ' + payload);

   var params = {
     FunctionName: 'textToImage',
     InvocationType: "RequestResponse",
     Payload: payload
   };

   lambda.invoke(params, function(err, data) {
     if (err) {
        console.log(err, err.stack);
     } else {
        console.log(data); // raw response from lambda
        
        var options = { encoding: 'base64' };
        var base64Image = data.Payload.toString('base64');
        console.log('base64Image: ' + base64Image);
        var decodedImage = new Buffer(base64Image, 'base64');
        
        fs.writeFile(outputfile, decodedImage, options, function(err){
            if (err) throw err
            console.log('File saved ' + outputfile)
        });
     }
   });
});
