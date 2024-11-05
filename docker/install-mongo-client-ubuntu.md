### add gpg key
curl -fsSL https://www.mongodb.org/static/pgp/server-5.0.asc | sudo gpg --dearmor -o /usr/share/keyrings/mongo-5.x.gpg

### add apt repository
echo "deb [ arch=amd64,arm64 signed-by=/usr/share/keyrings/mongo-5.x.gpg ] https://repo.mongodb.org/apt/ubuntu focal/mongodb-org/5.0 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-5.0.list

### install mongo client
sudo apt update
sudo apt install mongodb-org-shell mongodb-database-tools
