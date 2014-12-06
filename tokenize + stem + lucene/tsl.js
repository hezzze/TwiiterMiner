// Call the console.log function.
console.log("Hello World");

fs = require('fs');
var stem = require('stem-porter');
//var sets = require('./simplesets');

var file_Twitter = "/Users/wangyixun/Desktop/test.json";
var file_dic = "/Users/wangyixun/Desktop/wordsEn.txt";

var data = fs.readFileSync(file_Twitter, 'utf8');  //it's a string

var dictionary = fs.readFileSync(file_dic, 'utf8');  //it's a string

var single_Twitter = data.split('\n');
single_Twitter.pop();

function string2array(str){
	var arr = [];
	str = str.split("\n")
	for(var i=0; i<str.length; i++){
		str[i] = str[i].split('\r')[0];
		str[i] = str[i].split(',')[0];
		arr[str[i]] = 1;
	}
	delete arr['']
	return arr;
}

var dic_arr = string2array(dictionary);  // use for index

var result = [];
//console.log(single_Twitter)
for(var entry in single_Twitter){
	var temp = JSON.parse(single_Twitter[entry]);
	if(!temp["created_at"]) continue;
	result.push(temp)
}

var output = '', twitter, user, doc_output, words_vec, stemWord;
for (var i =0; i<result.length; i++){
	//initial doc_output first
	doc_output = ''
	twitter = result[i].text;
	//check, if any, url exists in this twitter
	if(new RegExp("([a-zA-Z0-9]+://)?([a-zA-Z0-9_]+:[a-zA-Z0-9_]+@)?([a-zA-Z0-9.-]+\\.[A-Za-z]{2,4})(:[0-9]+)?(/.*)?").test(twitter)) {
		// if multi-url within this twitter doc, only one counted
        doc_output += '@url ';
	}
	//replace non-letter with space
	twitter = twitter.replace(/[^a-zA-Z]/g, ' ');
	words_vec = twitter.split(' ');
	for(var j=0; j<words_vec.length; j++){
		//stemming before lucene
		stemWord = stem(words_vec[j]);
		if(dic_arr[stemWord] == 1){
			doc_output += stemWord + ' ';
		}
		else{
			// show no mercy to non-En!
			break;
		}
	}
	// if this twitter is written in En or has url, then store it with user info
	//USER_FIELDS = ["id", "name", "screen_name", "location"]
	if(doc_output){
		output += doc_output + '\n';
		user = {id:result[i]['user']['id'], name:result[i]['user']['name'], screen_name:result[i]['user']['screen_name'], location:result[i]['user']['location']};
		output += JSON.stringify(user) + '\n';
	}
}

fs.writeFile('/Users/wangyixun/Desktop/output.txt', output, function (err) {
	if (err) return console.log(err);
	console.log('twitter + user > output.txt');
});


/*
fs.readFile(file, 'utf8', function(err, data) {
	var d= data.split('\n');
	d.pop();
	for (var i =0; i< d.length; i++) {

		var entry = JSON.parse(d[i]);
		if (!entry["created_at"]) continue;

		result.push(entry);
	}
	//console.log(result[15]);
	var words_vec, charcode, isEn = true, words_pool = [];
	for (var j=0; j<result.length; j++){
		words_vec = result[j].text.split(" ");
		for (var m=0; m<words_vec.length; m++){
			 for(var n=0; n<words_vec[m].length; n++){
			 	charcode = words_vec[m].charCodeAt(n);
			 	if( (charcode > 64 && charcode < 91) || (charcode > 96 && charcode < 123)){
				//do nothing
			 	}else{
			 		isEn = false;
			 		break
			 	}
			}
			if (isEn){
			 	words_pool.push(words_vec[m]);
			 }
			 else{
			 	isEn = !isEn;
			 }
		}

	}

	fs.writeFile('/Users/wangyixun/Desktop/helloworld.txt', words_pool, function (err) {
  		if (err) return console.log(err);
  		console.log('Hello World > helloworld.txt');
	});
});
*/


