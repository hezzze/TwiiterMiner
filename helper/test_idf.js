//test for idf 
// Accoridng to this example 
// http://en.wikipedia.org/wiki/Tf%E2%80%93idf#Example_of_tf.E2.80.93idf

var tfidf = require('./tfidf');

// var tweetlst1 = ["this is a a sample", "this is another another example example example"];

// var t1 = new tfidf(tweetlst1);

// console.log(t1.idf("example"))

var tweetlst2 = ["this is a a sample", "this is another another example example example", "this is an example"];

var t2 = new tfidf(tweetlst2);