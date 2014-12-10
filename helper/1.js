var fs = require('fs');

var infile = "../corpus";

if (process.argv.length > 2) {
	infile = process.argv[2];
}

var tweets = fs.readFileSync(infile, 'utf8').split('\n').filter(function(d, i) {
	return i % 2  === 1;
});

//current prev1 prev2 post1 post2

var result = [];

var dict ={};

var it;
for (var i = 0; i < tweets.length; i++) {
	var ws = tweets[i].split(' ').filter(function(d) { return d.length > 0;});

	if (/@url/.test(ws[0])) {
		ws.shift();
	}
	//console.log(ws);
	var w;

	for (var j = 0; j < ws.length; j++) {
		it = [];
		w = ws[j];

		if (dict.hasOwnProperty(w)) {
			continue;
		} else {
			dict[w] = true;
		}

		it.push(w);
		it.push((j-1 >= 0 ? ws[j-1] : "-"));
		it.push((j-2 >= 0 ? ws[j-2] : "-"));
		it.push((j+1 < ws.length ? ws[j+1] : "-"));
		it.push((j+2 < ws.length ? ws[j+2] : "-"));

		
		if (it.length > 0) result.push(it);
	}

	

}

result.forEach(function(v){
	console.log(v.join(' '));
});

