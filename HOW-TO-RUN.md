### REQUIREMENTS
1. Maven (>= 3.5)
2. Java 8
3. MySQL / MariaDB 
4. Docker


### RUNNING THE APPLICATION
1. `git clone https://github.com/manuyaji/software-developer-coding-challenge.git`
2. ```cd software-developer-coding-challenge```
3. ```docker build -t carauction .```
4. ```docker network create carauction-net```
5. ```docker run --name carauctiondb --network carauction-net -e MYSQL_USER=manu -e MYSQL_PASSWORD=manu -e MYSQL_DATABASE=traderev -p 3306:3306 -d mysql/mysql-server:5.7```
6. ```mysql --protocol=TCP -u manu -pmanu < traderev.sql```
7. ```docker run --network carauction-net --name carauction-app -p 8080:8080 -d carauction```

### PRE-CONFIGURATIONS
1. Change the database configurations in `application.properties` according to the MySQL/MariaDB setup that you are going to use. In particular, you will have to check `spring.datasource.url` , `spring.datasource.url` , `spring.datasource.password` , `spring.datasource.driver-class-name` , `spring.jpa.properties.hibernate.dialect`. 
2. Check the script `traderev.sql` - note that it drops the database `traderev` and recreates it. If you want to change the database name, please change it. 
3. Run the command `mysql -u <user> -p < traderev.sql` . Make sure that you use a `<user>` who has database creation access. This script creates the required database and inserts two `car_info` entries and four `user` entries. 
 
### HOW TO RUN
1. `git clone https://github.com/manuyaji/software-developer-coding-challenge.git`
2. Choose the mariadb/mysql connector dependency (comment out the one not required, and uncomment the one which you require) in `pom.xml`.
3. Run the application using this command `mvn spring-boot:run` after navigating to the git-cloned folder (should be named `software-developer-coding-challenge`) on your terminal. 

### APIs 
1. Create an auction : 
```
curl -X POST \
  http://localhost:8080/auctions \
  -H 'Content-Type: application/json' \
  -d '{
	"shouldOverwriteAllFields":false,
	"info":{
		"status":"{status}",
		"openingBid":{openingBidAmount},
		"carInfoId":{carInfoId},
		"sellerId":{userId-of-seller},
		"auctionDate":{auctionDate}
	}
}'
```
2. Get an auction : 
```
curl -X GET http://localhost:8080/auctions/{auctionId}
```
3. Get all auctions : 
```
curl -X GET http://localhost:8080/auctions
```
4. Modify an auction : 
```
curl -X PUT \
  http://localhost:8080/auctions/{auctionId} \
  -H 'Content-Type: application/json' \
  -d '{
	"shouldOverwriteAllFields":false,
	"info":{
		"status":"{status}"
	}
}'
```
5. Submit a bid : 
```
curl -X POST \
  http://localhost:8080/auctions/{auctionId}/bids \
  -H 'Content-Type: application/json' \
  -d '{
	"shouldOverwriteAllFields":false,
	"info":{
		"carAuctionId":{auctionId},
		"bidAmount":{bidAmount},
		"bidBy":{userId-of-bidder}
	}
}'
```
6. Get a bid for an auction : 
```
curl -X GET http://localhost:8080/auctions/{auctionId}/bids/{bidId} 
```
7. Get all bids for an auction : 
```
curl -X GET http://localhost:8080/auctions/{auctionId}/bids 
```
8. Get winning bid for an auction : 
```
curl -X GET http://localhost:8080/auctions/{auctionId}/bids/winningBid
```
9. Close/End an auction :  
```
curl -X POST http://localhost:8080/auctions/{auctionId}/closeAuction 
```
10. Get History of an auction (ordered according to time of bid)
```
curl -X GET http://localhost:8080/auctions/{auctionId}/bids/history 
```

### TESTING
#### HAPPY FLOW
1. Create an auction
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
2. Check if it is created
```
curl -X GET http://localhost:8080/auctions/1
```
3. Start the auction (use Modify-Auction API for this)
```
curl -X PUT \
  http://localhost:8080/auctions/{auctionId} \
  -H 'Content-Type: application/json' \
  -d '{
	"shouldOverwriteAllFields":false,
	"info":{
		"status":"AUCTION_IN_PROGRESS"
	}
}'
```
4. Submit a bid
```
curl -X POST \
  http://localhost:8080/auctions/1/bids \
  -H 'Content-Type: application/json' \
  -d '{
	"shouldOverwriteAllFields":false,
	"info":{
		"carAuctionId":1,
		"bidAmount":16000,
		"bidBy":1
	}
}'
```
5. Get the winning bid
```
curl -X GET http://localhost:8080/auctions/1/bids/winningBid
```
6. Submit another bid
```
curl -X POST \
  http://localhost:8080/auctions/1/bids \
  -H 'Content-Type: application/json' \
  -d '{
	"shouldOverwriteAllFields":false,
	"info":{
		"carAuctionId":1,
		"bidAmount":17000,
		"bidBy":4
	}
}'
```
7. Get the winning bid
```
curl -X GET http://localhost:8080/auctions/1/bids/winningBid
```
8. Get all bids for the auction
```
curl -X GET http://localhost:8080/auctions/1/bids 
```
9. Close the auction
```
curl -X POST http://localhost:8080/auctions/{auctionId}/closeAuction 
```
10. Get the winning bid
```
curl -X GET http://localhost:8080/auctions/1/bids/winningBid
```
11. Get all bids for the auction
```
curl -X GET http://localhost:8080/auctions/1/bids 
```
12. Get details of the auction just ended/closed
```
curl -X GET http://localhost:8080/auctions/1
```
13. Get history of the auction
```
curl -X GET http://localhost:8080/auctions/{auctionId}/bids/history 
```

#### NEGATIVE FLOWS
1. You may try APIs in a different order from the one given in the above section. Though I can't say all are neatly implemented, I have tried to take care of common flows. As mentioned below, scenarios with invalid users have not been handled. 


### NOT IMPLEMENTED
1. Unit tests are not written.
2. UI/CLI has not been implemented.
3. Integration tests (end to end flow) have not been written. 
4. `User` and `UserInfo` was created in order to implement login mechanism - which I could not implement. I was hoping to use `spring-security` for this. 
5. Operations on `User` and `UserInfo` has not been implemented via REST. I have written Java "Services" for them with the common CRUD operations. 
6. Error/Exception handling with respect to `User` and `UserInfo` has not been handled. 
7. Dockerizing the DB and Application has not been done.
