### Prepare dev environment
```
1. Install JDK 21
2. Install gradle 8.5.x
3. Install nodejs 18
4. Install MongoDB 5+ and initialize replica set with 1 instance
```

### Upgrade gradle from 7.6.x to 8.5.x (run upgrade on older java version 17)
```
sdk use java 17.0.5-zulu
gradle wrapper --gradle-version 8.5
sdk use java 21.0.3-zulu
```

### Use multiple gradle versions on dev machine - https://sdkman.io/usage
```
sdk list gradle
sdk install gradle 8.6
sdk use gradle 8.6
```

### Use multiple java on dev machine - https://sdkman.io/usage
```
sdk list java
sdk install java 21.0.3-zulu
sdk use java 21.0.3-zulu
sdk default java 21.0.3-zulu

sdk install java 17.0.5-zulu
sdk use java 17.0.5-zulu
sdk default java 17.0.5-zulu

sdk install java 11.0.17-zulu
sdk use java 11.0.17-zulu
sdk default java 11.0.17-zulu
```

### Use multiple nodejs on dev machine - https://github.com/nvm-sh/nvm
```
nvm install 16
nvm install 18
nvm use 16
nvm use 18
```

### How to enable replica set on MongoDB (makes spring boot transactions to work)
```
1. Add following to mongod.conf and restart
replication:
  replSetName: "rs0"

2. Run rs.initiate() on mongo console
```

### Build
```
gradle build
```

### Clean & build
```
gradle clean build
```

### Build without test
```
gradle clean build -x test
```

### Dependencies
```
gradle dependencies
```

### Run tests
```
gradle test
```

### Run
```
gradle bootRun
```

### Ports
```
portal-frontend                -> 3000/
manage-frontend                -> 3001/
backoffice-frontend            -> 3002/
socket-server                  -> 3003/
portal-backend                 -> 8080/portal-api
manage-backend                 -> 8081/manage-api
manage-backend                 -> 8082/backoffice-api
notification-api               -> 8083/notification-api
sso api                        -> 8084/sso-api
analytics api                  -> 8085/analytics-api
wallet-api                     -> 8089/wallet-api
payment-api                    -> 8090/payment-api
payment-cron-khan              -> 8091/payment-cron-khan
payment-cron-golomt            -> 8092/payment-cron-golomt
payment-cron-tdb               -> 8093/payment-cron-tdb
cron-shared                    -> 8100/cron-shared
cron-email                     -> 8101/cron-email
cron-email-sqs                 -> 8102/cron-email-sqs
```

### Production URLs
```
Config API                     -> https://config.astvision.mn
Portal Frontend                ->
Back-Office Frontend           ->
Manage Frontend                ->
Socket Server                  ->
Portal Backend                 ->
Manage Backend                 ->
Back-Office Backend            ->
Payment API                    ->
Wallet API                     ->
```

### Staging URLs
```
Config API                     -> https://config-dev.astvision.mn
Portal Frontend                ->
Back-Office Frontend           ->
Manage Frontend                ->
Socket Server                  ->
Portal Backend                 ->
Manage Backend                 ->
Back-Office Backend            ->
Payment API                    ->
Wallet API                     ->
```
"# new" 
