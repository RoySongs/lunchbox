const mysql = require('mysql');

var pool;

exports.connect = function(done) {
	console.log("Trying to connect DB...");
	pool = mysql.createPool({
		host: '13.209.68.137',
		user: 'root',
		password: 'goni0211',
		database: "lunchrun",
		connectionLimit: 100 // Why 5 ???
	});
	console.log("DB Connected.");

	exports.get = function () {
//	pool.dbHelper = new dbHelper();
		return pool;
	}
}