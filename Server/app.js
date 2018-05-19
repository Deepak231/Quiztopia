var express = require("express");
var mysql = require("mysql");
var app = express();
var fs = require("fs");

var bodyParser = require('body-parser')
app.use(bodyParser.json()); 
app.use(bodyParser.urlencoded({
    extended: true
}));

var connection = mysql.createConnection({
    host : "localhost",
    port: "3306",
    user : "root",
    password : "12345",
    database : "quiztopia",
    multipleStatements: true
});

var index = 0, index1 = 0, index2 = 0;

var users = []
var follow_topics = []
var rankings = []
var topics = require('./server_data/topic_package.json');
var gk = require('./server_data/General_Knowledge.json');
var andy = require('./server_data/Android.json');
var bmath = require('./server_data/Basic_Math.json');


 connection.query('SELECT * FROM user ', function (error, rows, fields) {
      for (var i in rows) {
        users.push({id: index +1,username: rows[i].username, password: rows[i].password,
        			lvl :rows[i].lvl , exp : rows[i].exp , ll : rows[i].ll });
        index++;
      }
});

 connection.query('SELECT * FROM followed_topics', function (error, rows, fields) {
      for (var i in rows) {
        follow_topics.push({ id : index1 +1, username : rows[i].username, topics : rows[i].topics });
        index1++;
      }
});

  connection.query('SELECT * FROM rankings', function (error, rows, fields) {
      for (var i in rows) {
        rankings.push({id : index2 +1, username : rows[i].username, rank1 : rows[i].rank1 , rank2 : rows[i].rank2 ,
        	rank3 : rows[i].rank3 });
        index2++;
      }
});


app.get('/users', function (req, res) {
    res.end(JSON.stringify(users));
});

app.get('/followed_topics', function (req, res) {
    res.end(JSON.stringify(follow_topics));
});

app.get('/rankings', function (req, res) {
    res.end(JSON.stringify(rankings));
});

app.put('/rankings',function( req , res ) {
  var newrankings;
  for (var i = 0; i < rankings.length; i++) {
    if (rankings[i].username == req.body.username) {
      rankings[i].rank1 = req.body.rank1;
      rankings[i].rank2 = req.body.rank2;
      rankings[i].rank3 = req.body.rank3;
      newrankings = rankings[i];
      break;
    }
  }
  var query = "UPDATE rankings SET rank1 = "+rankings[i].rank1+",rank2 = "+rankings[i].rank2+ ",rank3 = "+
  			rankings[i].rank3+" WHERE username = '"+rankings[i].username+"'";
    connection.query(query, function(queryError, queryResult){
      if(queryError){
          throw queryError;
      }
    });
  res.end(JSON.stringify(newrankings));
});

app.post('/followed_topics', function (req, res) {
    var newtopic = {
    	"id": index1 + 1,
        "username": req.body.username,
        "topics": req.body.topics
        
    }

    index1++;

    follow_topics.push(newtopic);
    var query = "INSERT INTO followed_topics(username,topics) VALUES ('" + newtopic.username + 
    "','"+ newtopic.topics +"')";
  	connection.query(query, function(queryError, queryResult){
    	if(queryError){
      		throw queryError;
    	}
  	});
    res.status(201).end(JSON.stringify(newtopic));
});



app.delete('/followed_topics/:id', function (req, res) {
    for (var i = 0; i < follow_topics.length; i++) {
        if(follow_topics[i].id == req.params.id){
            var query = "DELETE FROM followed_topics WHERE "+
            		"username =  '" + follow_topics[i].username + "' and "+
            		"topics = '"+ follow_topics[i].topics +"'";
  			connection.query(query, function(queryError, queryResult){
    		if(queryError){
      			throw queryError;
    		}
  		});
  			follow_topics.splice(i, 1);
        res.status(204).end(JSON.stringify(follow_topics[i]));
        }
    }
});
app.post('/users', function (req, res) {
    var newuser = {
    	"id": index + 1,
        "username": req.body.username,
        "password": req.body.password,
        "lvl": req.body.lvl,
        "exp": req.body.exp,
        "ll" : req.body.ll
    }
    index++;
    users.push(newuser);
    var query = "INSERT INTO user(username,password,lvl,exp,ll) VALUES ('" + newuser.username + "','"
    	+ newuser.password +"',"+newuser.lvl+","+newuser.exp+",'"+newuser.ll +"')";
  	connection.query(query, function(queryError, queryResult){
    	if(queryError){
      		throw queryError;
    	}
  	});
  	var newrank = {
  		"id": index + 1,
  		"username" : req.body.username,
  		"rank1" : "0",
  		"rank2" : "0",
  		"rank3" : "0"
  	}

  	index2++;

  	rankings.push(newrank);

    res.status(201).end(JSON.stringify(newuser));
});


app.get('/topics', function (req, res) {
	res.end(JSON.stringify(topics));
});

app.get('/General_Knowledge', function (req, res) {
	res.end(JSON.stringify(gk));
});

app.get('/Android', function (req, res) {
  res.end(JSON.stringify(andy));
});

app.get('/Basic_Math', function (req, res) {
  res.end(JSON.stringify(bmath));
});

app.put('/users',function( req , res ) {
  var newuser;
  for (var i = 0; i < users.length; i++) {
    if (users[i].username == req.body.username) {
      users[i].lvl = req.body.lvl;
      users[i].exp = req.body.exp;
      users[i].ll = req.body.ll;
      newuser = users[i];
      break;
    }
  }
  var query = "UPDATE user SET lvl = "+users[i].lvl+",exp = "+users[i].exp+",ll = '"+
                users[i].ll+"' WHERE username = '"+users[i].username+"'";
    connection.query(query, function(queryError, queryResult){
      if(queryError){
          throw queryError;
      }
    });
  res.end(JSON.stringify(newuser));
});


var server = app.listen(9000, function () {
    var host = server.address().address;
    var port = server.address().port;
    console.log("\nlistening at http://%s:%s",host,port);
});
