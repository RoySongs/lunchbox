var crypto = require('crypto');
var uuid = require('uuid');
var express = require('express');
var mysql = require('mysql');
var bodyParser = require('body-parser');

//Connect to mariaDB
var con = mysql.createConnection({
    host:'13.209.68.137',
    user:'root',
    password:'goni0211',
    database:'lunchrun'
});

//Password
var genRandomString = function(length){
    return crypto.randomBytes(Math.ceil(length/2))
        .toString('hex')   //convert to hexa format
        .slice(0, length); //return required number of characters
};

var sha512 = function(password, salt){
    var hash = crypto.createHmac('sha512', salt); //Use Sha512
    hash.update(password);
    var value = hash.digest('hex');
    return{
        salt:salt,
        passwordHash:value
    };
};

function saltHashPassword(userPassword){
    var salt = genRandomString(16);  //Gen random string with 16 character to salt
    var passwordData = sha512(userPassword, salt);
    return passwordData;
};

var app = express();
app.use(bodyParser.json());  //Accept JSON Params
app.use(bodyParser.urlencoded({extended: true}));  //Accept URL Encoded Params


app.post('/register/', (req, res, next)=>{
    var post_data = req.body; //Get post params
    var uid = uuid.v4(); //Get uuid v4 like '110abcsasas-af0x-90333-casasjkajksk'
    var plaint_password = post_data.password; //Get password from post params
    var hash_data = saltHashPassword(plaint_password);
    var password = hash_data.passwordHash; //Get hash value
    var salt = hash_data/salt; //Get salt

    var name = post_data.name;
    var email = post_data.email;

    con.query('SELECT * FROM user where email=?', [email], function (err, result, fields){
        con.on('error', function (err) {
           console.log('SQL Error: ', err);
        });

        if(result && result.length)
            res.json('User already exists!!');
        else
        {
            con.query('insert into user(unique_id, name, email, password, salt, created_at, update_at) ' +
                'VALUES(?,?,?,?,?,NOW(),NOW()', [uid, name, email, password, salt], function (err, result, fields) {
                con.on('error', function (err) {
                    console.log('SQL Error: ', err);
                    res.json('Register error: ', err);
                });
                    res.json('Resister successful');
            });
        };
    });


});


//Start Server
app.listen(3000, ()=>{
    console.log('EDMTDev Restful running on port 3000!!');
});