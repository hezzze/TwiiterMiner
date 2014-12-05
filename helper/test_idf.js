//test for idf 
// Accoridng to this example 
// http://en.wikipedia.org/wiki/Tf%E2%80%93idf#Example_of_tf.E2.80.93idf

var tfidf = require('./tfidf');

var tweetlst = ["this is a a sample", "this is another another example example example"];

var t = new tfidf(tweetlst);

console.log(t.idf("example"))