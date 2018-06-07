const express=require('express')
const app=express()
var fs = require("fs");

var bodyParser = require('body-parser');
app.use(bodyParser.json()); // support json encoded bodies
app.use(bodyParser.urlencoded({ extended: true }));

app.use(express.static('public'))
var buffer1;
var buffer2;
fs.readFile("file1.json","utf8",function(err,data){
    buffer1=JSON.parse(data)  
})
fs.readFile("file2.json","utf8",function(err,data){
	buffer2=JSON.parse(data)
})

app.get('/tab',function(req,res){
    word = req.query['word'];
    console.log(word);
    var titleArray=[];
    let a=0;
    for (var i in buffer2) {
    	titleArray[a]=i;
    	a++;
    }
    // console.log(titleArray)
    let title = buffer1[titleArray[word]];
    let content = buffer1[title];
    // console.log(word+content+":" +title)
    res.status(200).send(title+"\r"+content);
    
    
});
app.post('/title',function (req,res) {
	// console.log(buffer2);
	res.send(buffer2);
});



app.listen(3000,()=>console.log("Example app listening on port 3000"))
