var express = require('express');
var db = require('../db');
var router = express.Router();

//profile
router.put('/:username', function(req, res, next){
	console.log("/profile/:username");
	var username = req.params.username;
	var newUsername = req.body.username;
	var introduction = req.body.introduction;
	console.log("introduction : " +  introduction);



});

//profile/:username
router.get('/:username', function(req, res, next){
	console.log("/profile/:username");
	var username = req.params.username;

	console.log("username: "+username);

	var sql = "SELECT * FROM member WHERE username=?";

	var input = [username];
	try{
		db.get().query(sql, input, function(err,result){
			if(err) {
				console.log("err: "+JSON.stringify(err, null, 2));
				res.json({code:401, message:"DB error", result:"Failure"});
			}
			if(result.length>0){
				res.json({data: result[0], code:200, message:"Success", result:"OK"});
			}
		});
	}catch(e){
		console.log(e);
	}
});

//profile/:newUsername
router.get('/newuser/:newUsername', function(req, res, next){
	console.log("/profile/newuser/:newUsername");
	var newUsername = req.params.newUsername;
	console.log("newUsername: "+newUsername);

	var sql = "SELECT * FROM member WHERE username=?";

	var input = [newUsername];
	try{
		db.get().query(sql, input, function(err,result){
			if(err) {
				console.log("err: "+JSON.stringify(err, null, 2));
				res.json({code:401, message:"DB error", result:"Failure"});
			}
			console.log("data : "+result[0]);
			console.log("length : "+result.length);
			if(result.length>0){
				res.json({code:200, message:"Success", result:true});
			} else {
				res.json({code:200, message:"Success", result:false});
			}
		});
	} catch(e){
		console.log(e);
	}
});

module.exports = router;
