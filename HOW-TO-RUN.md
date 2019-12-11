### REQUIREMENTS
1. Maven 
2. Java 8
3. MySQL / MariaDB 


### PRE-CONFIGURATIONS
1. Change the database configurations in `application.properties` according to your MySQL.
2. Check the script `traderev.sql` - note that it drops the database `traderev` and recreates it. If you want to change the database name, please change it. 
3. Run the command `mysql -u <user> -p < traderev.sql` . Make sure that you use a `<user>` who has database creation access. This script creates the required database and inserts two `car_info` entries and four `user` entries. 
 
### HOW TO RUN
1. `git clone https://github.com/manuyaji/software-developer-coding-challenge.git`
2. Run the application using this command `mvn spring-boot:run` after navigating to the git-cloned folder (should be named `software-developer-coding-challenge`) on your terminal. 

### TESTING 
1. Create an auction : 
```
curl -X POST \
  http://localhost:8080/auctions \
  -H 'Content-Type: application/json' \
  -d '{
	"shouldOverwriteAllFields":false,
	"info":{
		"status":"UNVERIFIED",
		"openingBid":15000,
		"sellingPrice":null,
		"carInfoId":2,
		"sellerId":2,
		"buyerId":null,
		"auctionDate":null,
		"sellingBidId":null
	}
}'
```
2. Get an auction : 
```
curl -X GET http://localhost:8080/auctions/4
```
3. Get all auctions : 
```
curl -X GET http://localhost:8080/auctions
```
4. Modify an auction : 
```
curl -X PUT \
  http://localhost:8080/auctions/3 \
  -H 'Content-Type: application/json' \
  -d '{
	"shouldOverwriteAllFields":false,
	"info":{
		"status":"AUCTION_IN_PROGRESS"
	}
}'
```
5. Submit a bid : 
```
curl -X POST \
  http://localhost:8080/auctions/3/bids \
  -H 'Content-Type: application/json' \
  -d '{
	"shouldOverwriteAllFields":false,
	"info":{
		"carAuctionId":3,
		"bidAmount":16000,
		"bidBy":4
	}
}'
```
6. Get a bid for an auction : 
```
curl -X GET http://localhost:8080/auctions/3/bids/5 
```
7. Get all bids for an auction : 
```
curl -X GET http://localhost:8080/auctions/3/bids 
```
8. Get winning bid for an auction : 
```
curl -X GET http://localhost:8080/auctions/3/bids/winningBid
```
9. Close/End an auction :  
```
curl -X POST http://localhost:8080/auctions/4/closeAuction 
```
