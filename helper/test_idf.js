//test for idf 
// Accoridng to this example 
// http://en.wikipedia.org/wiki/Tf%E2%80%93idf#Example_of_tf.E2.80.93idf

var fs = require('fs');

var tfidf = require('./tfidf');

var infile = "../corpus";

if (process.argv.length > 2) {
	infile = process.argv[2];
}
// var tweetlst1 = ["this is a a sample", "this is another another example example example"];

// var t1 = new tfidf(tweetlst1);

// console.log(t1.idf("example"))

//var tweetlst2 = ["this is a a sample", "this is another another example example example", "this is an example"];

//var t2 = new tfidf(tweetlst2);

var tweets = fs.readFileSync(infile, 'utf8').split('\n').filter(function(d, i) {
	return i%2 === 1;
});

//console.log(tweets);

var t = new tfidf(tweets);

var result = t.get_result();

var output = "";

for (var i = 0; i < result.length; i++) {
	output += (result[i].term + "\t" + result[i].tfidf + "\n");
}

console.log(output);
