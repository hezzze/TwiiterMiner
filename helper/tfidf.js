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

    var _doc_tfidf_lst = [];

    var _terms_tfidf = {};

    calc();

    // console.log(_dic);
    console.log(_terms);
    console.log(_termDocFrequency);
    console.log(_doc_tfidf_lst);
    console.log(_terms_tfidf);


    this.idf = function(term) {
    	return idx_idf(_dic[term]);
    }

    function idx_idf(idx) {
        return Math.log(tweetlst.length / _termDocFrequency[idx]) / Math.log(10);
    }

    function calc () {
        var doc_idx_freq_lst = [];

        for (var i = 0; i < tweetlst.length; i++) {
            var doc_idx_freq = processTweet(tweetlst[i]);

            doc_idx_freq_lst.push(doc_idx_freq);

            for (var idx in doc_idx_freq) {
            	_termDocFrequency[idx]++;
            }
        }

        for (i = 0; i < tweetlst.length; i++) {

            var doc_tfidf = {};
            for (idx in doc_idx_freq_lst[i]) {
                var tfidf = doc_idx_freq_lst[i][idx] * idx_idf(idx);
                var term = _terms[idx]
                doc_tfidf[term] = tfidf;

                if (_terms_tfidf.hasOwnProperty(term)) {
                    _terms_tfidf[term] += tfidf;
                } else {
                    _terms_tfidf[term] = tfidf;
                }
            }

            _doc_tfidf_lst.push(doc_tfidf);
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

        var doc_idx_freq = {};

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

            if (doc_idx_freq.hasOwnProperty(idx)) {
                doc_idx_freq[idx]++;
            } else {
                doc_idx_freq[idx] = 1;
            }

        }

        return doc_idx_freq;
    }

}
