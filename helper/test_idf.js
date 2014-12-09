//test for idf 
// Accoridng to this example 
// http://en.wikipedia.org/wiki/Tf%E2%80%93idf#Example_of_tf.E2.80.93idf

var fs = require('fs');

var tfidf = require('./tfidf');

// var tweetlst1 = ["this is a a sample", "this is another another example example example"];

// var t1 = new tfidf(tweetlst1);

// console.log(t1.idf("example"))

//var tweetlst2 = ["this is a a sample", "this is another another example example example", "this is an example"];

//var t2 = new tfidf(tweetlst2);

var data = fs.readFileSync("../out1", 'utf8');

var tweets = JSON.parse(data);

var t = new tfidf(tweets);

var result = t.get_result(300);

var output = "";

for (var i = 0; i < result.length; i++) {
	output += (result[i].term + "\t" + result[i].tfidf + "\n");
}

console.log(output);
