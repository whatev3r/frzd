import os
from datetime import datetime

def main():
	f = open("access.log","r")
	lines = f.readlines()

	uuids = {};
	ips = {};
	dates = {};

	for line in lines:
		line = line.replace("\n","")
		char = ""
		ip = ""
		date = ""
		mode = "IP"
		if line == '':
			continue
		if not " - - [" in line:
			continue
		if not (('GET /favicon.ico HTTP/1.1" 200' in line) or ('GET /favicon.ico HTTP/1.1" 304' in line)):
			continue

		continueIterator = 0
		for i in range(len(line)):
			char = line[i]
			if continueIterator > 0:
				continueIterator = continueIterator - 1
				continue
			if (char == ' '):
				continueIterator = 5
				mode = "DATE"
				continue
			if char == ':':
				break
			if mode == "IP":
				ip = ip + char
			if mode == "DATE": 
				date = date + char
		uuid = line.split('"')[5]
		date = datetime.strptime(date, '%d/%b/%Y').strftime('%Y-%m-%d')

		addToList(dates,date)
		addToList(uuids,(date, uuid))
		addToList(ips,(date, ip))

	for date in dates:
		counterIp = 0
		counterUuid = 0
		for key in ips:
			if not key[0] == date:
				continue
			counterIp = counterIp + 1

		for key in uuids:
			if not key[0] == date:
				continue
			counterUuid = counterUuid + 1
		print(date + " : от " + str(counterUuid) + " до " + str(counterIp))

def addToList(list, key):
	if not key in list:
		list[key] = 0
	list[key] = list[key] + 1

main()
