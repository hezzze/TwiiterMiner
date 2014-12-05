//Credit: Yuanyi Yang
//https://github.com/YuanyiYang/WebSearchEngineThree
// IndexerFullScan.java

//input: a list of tweets
//output: the tf-idf

module.exports = function(tweetlst) {

	//term -> index
    var _dic = {};

    //index -> term
    var _terms = [];

    //index -> how many docs term appears in
    var _termDocFrequency = {};

    calc();

    // console.log(_dic);
    // console.log(_terms);
    // console.log(_termDocFrequency);

    this.idf = function(term) {
    	return Math.log(tweetlst.length / _termDocFrequency[_dic[term]]) / Math.log(10);
    }

    function calc () {
        for (var i = 0; i < tweetlst.length; i++) {
            var uniq_term_idxs = processTweet(tweetlst[i]);

            for (var j = 0; j < uniq_term_idxs.length; j++) {
            	_termDocFrequency[uniq_term_idxs[j]]++;
            }
        }
    }

    //reference: http://stackoverflow.com/questions/9229645/remove-duplicates-from-javascript-array
    function uniq(a) {
        var seen = {};
        return a.filter(function(item) {
            return seen.hasOwnProperty(item) ? false : (seen[item] = true);
        });
    }

    //process a tweet and return uniq terms
    function processTweet(content) {
        var tokens = content.split(/[^a-zA-Z0-9]/).filter(function(w) {
            return w.length > 0
        });


        // console.log(tokens);

        var tok_idxs = [];

        var idx = -1;
        var tok = "";

        for (var i = 0; i < tokens.length; i++) {

            tok = tokens[i];
            if (_dic.hasOwnProperty(tok)) {
            	//already seen
            	//get the interger presentation
            	idx = _dic[tok];
            } else {
                idx = _terms.length;
                _terms.push(tok);
                _dic[tok] = idx;
                _termDocFrequency[idx] = 0;
            }

            tok_idxs.push(idx);
        }

        return uniq(tok_idxs);
    }

}
