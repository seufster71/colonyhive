# Colony Hive

Copyright (C) 2023 Lydia's Honey Pine Farm


create directory gitcolony and clone all project into this directory  script depend on it. 


run command to pull and attach submodules (make sure directories are populated) </br>

setup database need to run both files </br>
go to toasthub-db -> monolith -> colonycommand </br>
maindbload.sh </br>
memberdbload.sh </br>
memberdatadbload.sh </br>

run npm install </br>

start server </br>

run npm start </br>



#### This just to remember how to initially add a submodule. You do not need to execute these commands ####
place at src/main/js </br>
git submodule add -b feature/colony_1_from_develop https://github.com/seufster71/toasthub-react-core.git core </br>
git submodule add -b feature/colony_1_from_develop https://github.com/seufster71/toasthub-reactweb-core.git coreView </br>


#### Build react
cd to colonyhive then run npm run build </br>

#### Build and deploy
cd ~/gitcolony/toasthub-core/toasthub-core then run  mvn clean install </br>
cd ~/gitcolony/toasthub-security/toasthub-security then run  mvn clean install </br>
cd ~/gitcolony/toasthub-system/toasthub-system then run mvn clean install </br>
cd ~/gitcolony/toasthub-member/toasthub-member then run  mvn clean install </br>
cd ~/gitcolony/toasthub-admin/toasthub-admin then run  mvn clean install </br>
cd ~/gitcolony/toasthub-trade/toasthub-trade then run  mvn clean install </br>
cd ~/gitcolony/tradeanalyzer/tradeanalyzer then run  mvn clean package spring-boot:repackage </br>
java -jar target/tradeanalyzer-1.0-GA.jar </br>


#### Create db users
CREATE USER 'cyborg'@'localhost' IDENTIFIED WITH mysql_native_password BY 'c7b8rg#P'; </br>

CREATE USER 'toasthub'@'localhost' IDENTIFIED WITH mysql_native_password BY 'T0asth3b#'; </br>
GRANT SELECT, INSERT, UPDATE, DELETE on *.* TO 'toasthub'@'localhost'; </br>