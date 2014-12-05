#!/bin/bash
echo 'Words Filter for passed ouputs'

node extractEnWords.js

cat ./output.txt | tr -cs A-Za-z\' '\n' | tr A-Z a-z | sort | uniq -c | sort -k1,1nr -k2 | sed ${1:-300}q >./wordsList.txt
