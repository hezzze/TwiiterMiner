fs = require('fs');

var file = "data/part.json";

if (process.argv.length > 2) {
	file = process.argv[2];
}

FIELDS = ["created_at", "id", "text","in_reply_to_status_id", "in_reply_to_user_id", "geo", "place", "retweet_count", "retweeted"];

USER_FIELDS = ["id", "name", "screen_name", "location"];

var result = [];

fs.readFile(file, 'utf8', function(err, data) {
	var d= data.split('\n');
	d.pop();

	for (var i =0; i< d.length; i++) {
		var entry = JSON.parse(d[i]);
		if (!entry["created_at"]) continue;

		//console.log("!");

		var lst = [];
		for (var j = 0; j<FIELDS.length; j++) {
			lst.push(entry[FIELDS[j]]);
		}

		//Add user field
		for (var j = 0; j<USER_FIELDS.length; j++) {
			lst.push(entry["user"][USER_FIELDS[j]]);
		}

		result.push(lst);
	}

	console.log(JSON.stringify(result));

});

