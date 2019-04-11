var express = require('express');
var router = express.Router();
var db = require('../db');
var nodemailer = require("nodemailer");
var smtpTransport = nodemailer.createTransport({
  service: 'Gmail',
  auth: {
    user: 'study.simpleSNS@gmail.com',
    pass: 'simple123SNS'
  }
});

// From client, if the user click send button. calls... and send email to the email address that passed.
router.get('/send', async function(req, res, next) {
  var email = decodeURI(req.query.to);
  var rand = Math.floor((Math.random() * 9000 + 1000));
// get method
//  var link="http://"+req.get("host")+"/email/verify?email="+email+"&num="+rand;
  var mailOptions={
		to : email,
    subject : "Please verify that it's your email address.",
    html : "<div style='font-size: 15px;'>"
					+"Please verify that it's you."
					+"<br>This is an email from"
					+"<a style='font-weight: bold;' href='https://github.com/chanjungkim/simpleSNS'>SimpleSNS</a> to verify your email address."
					+"<br>If you are attempting to sign-up, please use the following code to verify your identity."
					+"<br> Your verification code :"
					+"<div style='background-color:#555; color:white; text-align: center; font-weight: bold;"
					+"line-height: 100px; width: 150px; height: 100px;'>"+rand
					+"</div>"
					+"</div>"
					+"<div style='color:red;'>Please ignore or block this email if you are not signing up for SimpleSNS.</div>" 
					+"<br>Yours securely,"
					+"<br>Team <a href='https://github.com/chanjungkim/simpleSNS'>SimpleSNS</a>"
  }

	try{
  	smtpTransport.sendMail(mailOptions, function(error, response){
			console.log("smtpTransport.sendMail");
    	if(error){
      	console.log(error + '오류발생');
      	res.json({result:false,message:"Incorrect email format", code:404});
    	}else{
     		console.log("Message sent:\n"+JSON.stringify(response, null, 2));
			console.log(JSON.stringify(db, null, 2));

				var num = rand;
				var sql = "INSERT INTO email_verification(email, code) VALUES(?,?)";
				var input = [email,num];
				db.get().query(sql, input, function(err, result){
					if(err){
						console.log(err + '오류발생');
						res.json({result:false, message:"Please check the program code", code:100});
					}else{
						res.json({result:true, message:"Please check the code in your mail box.", code:100});
					}

				});
    	}
  	});
	}catch(e){
		console.log(e);
	}
});





// From email, if user clicks the link in the verification email. calls... => don't need link, but code in the email.
router.post('/verify',async function(req,res){
	console.log('/verify');
	console.log(req.body.code);

	var email = req.body.email;
	var code = req.body.code;



	var sql = "SELECT * FROM email_verification WHERE email=? AND code=? AND req_time >= NOW() - INTERVAL 3 MINUTE";
	var input = [email, code];
	db.get().query(sql, input, function(err, result){
		if(err){
			console.log("err: "+JSON.stringify(err, null, 2));
      console.log("email is not verified");
      res.json({result:false, code:403, message:"Verify SQL Error."});
		}
		console.log("result: "+JSON.stringify(result,null,2));
		if(result.length > 0){
    	console.log("Ready to verify - There is a record to be verified.");
    	var sql = "UPDATE email_verification SET status = 1 WHERE email=? AND code=? AND req_time >= NOW() - INTERVAL 3 MINUTE";
    	var input = [email, code];

    	db.get().query(sql, input, function(err, result){
				// this shows when the user clicks the link in the email.
				console.log(email+" M= verified successfully");
      	res.json({result:true, code:100, message:"Verified successfully."});
    	});
  	} else  {
      console.log("email is not verified");
      res.json({result:false, code:401, message:"Your email hasn't been verified yet."});
  	}
	});
});

router.post('/register',async function(req,res){
	console.log('/register');
	var email = req.body.email;
	var password = req.body.password;
	var username = req.body.username;

    if(email==undefined||password==undefined||username==undefined){
        res.json({result:false,code:401, message:"undefined parameter"});
    } else {
//        var result = await dbHelper.emailRegister(req.body.email,req.body.pass,req.body.nick,req.body.devid);
				var sql =  "SELECT * FROM email_verification WHERE email=? AND status = 1";
				var input = [email];

				db.get().query(sql, input, function(err, result){
					if(result.length >0){
						var sql_select = "SELECT * FROM member WHERE email=?";
						var input = [email];
						db.get().query(sql_select, input, function(err, result){
							if(result.length == 0){
								var token = makeToken();
								var sql_insert = "INSERT INTO member (email,password,username,device_id,login_method,token) VALUES(?,?,?,?,0,?)";
								var input = [email,pass,nick,devid,token];
								db.get().query(sql_insert, input, function(err, result){
									if(result.affectedRows > 0){
										result = {result:true, code:200, message: "Sign Up Success",data:{email:email,token:token}};
									} else{
										result = {result:false,code:401, message:"DB error"};
									}
								});
							}else{
								result = {result:false,code:402, message:"already registered id"};
							}
						});
					} else {
			        result = {result:false,code:403, meesage:"Email is not verified"};
      		}
					res.json(result);
				});
		}
});

router.post('/login',async function(req,res){
	console.log('/login');
	var email = req.body.email;
	var password = req.body.password;
    if(email==undefined||password==undefined){
        res.json({result:false,code:401, message:"undefined parameter"});
    } else {
				var sql = "SELECT * FROM member WHERE email=?";
				var input = [email];
				db.get().query(sql, input, function(err, result){
					res.json(result);
				});
//        var result = await dbHelper.emailLogin(req.body.email,req.body.pass,req.body.devid);
//        res.json(result);
    }
});

function makeToken(){
  console.log("makeToken()");
  var text = "";
  var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	 for(var i=0;i<32;i++){
    text+=possible.charAt(Math.floor(Math.random()*possible.length));
  }
  return text;
}

module.exports = router;
