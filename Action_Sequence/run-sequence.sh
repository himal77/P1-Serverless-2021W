wsk action create seq1 seq1.js
wsk action create seq2 seq2.js
wsk action invoke seq2 --param-file seq.json --result