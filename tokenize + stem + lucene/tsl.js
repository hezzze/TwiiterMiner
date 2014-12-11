
//console.log("Impliments tokenize, stemming and lucene");

fs = require('fs');
var stem = require('stem-porter');
//var sets = require('./simplesets');

var file_Twitter = "~/test.json";  // replace this test.json with your file
var file_dic = "~/wordsEn.txt";

if (process.argv.length  == 5) {
	file_Twitter = process.argv[2];
	file_dic = process.argv[3];
	outfile = process.argv[4];
}

//console.log(file_Twitter);
//console.log(file_dic);
//console.log(outfile);

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

var regx4url = new RegExp("([a-zA-Z0-9]+://)?([a-zA-Z0-9_]+:[a-zA-Z0-9_]+@)?([a-zA-Z0-9.-]+\\.[A-Za-z]{2,4})(:[0-9]+)?(/.*)?");

var output = [], twitter, user, doc_output, words_vec, stemWord, counter = 1, isEn = true;
for (var i =0; i<result.length; i++){
	//initial doc_output first
	doc_output = ''
	twitter = result[i].text;

	//if (i === Math.floor(result.length /2)) console.log(twitter);

	//check, if any, url exists in this twitter
	if(regx4url.test(twitter)) {
		// if multi-url within this twitter doc, only one counted
        doc_output += '@url ';
        twitter = twitter.replace(regx4url, ' ');
	}
	//replace non-letter with space
	twitter = twitter.replace(/[^a-zA-Z]/g, ' ');
	words_vec = twitter.toLowerCase().split(' ').filter(function(entry) {
		return entry.length > 0;
	});
	for(var j=0; j<words_vec.length; j++){

		//console.log(stemWord);

		if(dic_arr[words_vec[j]] == 1){
			//stemming before lucene
			stemWord = stem(words_vec[j]);
			doc_output += stemWord + ' ';
		}
		else if (counter < words_vec.length/2){
			// show a little mercy to non-En!
			counter ++;
			continue;
		}
		else{
			// more than half of words are non-En, so abandon
			isEn = false;
			break;
		}
	}

	counter = 0;
	if(!isEn){
		isEn = true;
		continue;
	}

	//console.log(doc_output);
	// if this twitter is written in En or has url, then store it with user info
	//USER_FIELDS = ["id", "name", "screen_name", "location"]
	if(doc_output.length > 0) {
		user = {id:result[i]['text'], user_id:result[i]['user']['id'], name:result[i]['user']['name'], screen_name:result[i]['user']['screen_name'], location:result[i]['user']['location']};
		output += JSON.stringify(user) + '\n';
		output += doc_output + '\n';
	}
}

fs.writeFile(outfile, output, function (err) {
	if (err) return console.log(err);
	//console.log('twitter + user > output.txt');
});

