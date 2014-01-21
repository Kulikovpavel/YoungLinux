import os
import re
import codecs
import json

res = ""
path = 'book/export/html/';
for filename in sorted(os.listdir('book/export/html/'), key=lambda name: int(name[:-5])):
    with codecs.open(path + filename, 'r', 'utf-8') as ifile:
        pattern = re.compile(".*<head>.*<title>(.*)</title>.*</head>.*", re.MULTILINE|re.DOTALL)
        # for line in ifile:
        content = ifile.read()
        # print(content.encode('utf-8'))
        
        match = re.match(pattern,content)
        if match:
            res += "<item>" + filename[0:-5] +". " + match.group(1) + "</item>\n"
        else:
            print(filename)



with open('data.txt', 'w') as outfile:
    outfile.write(res)