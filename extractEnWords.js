
console.log("Ready to extract En Words, Please replace with correct $PATH!");

fs = require('fs');
var file = "~/input.json";

var result = [];

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

	fs.writeFile('~/output.txt', words_pool, function (err) {
  		if (err) return console.log(err);
  		console.log('Extraction Finish!');
	});
});




