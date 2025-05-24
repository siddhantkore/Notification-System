#!/bin/bash
echo "Waiting for MongoDB to start..."
sleep 20

echo "Initializing MongoDB replica set..."
mongosh --host mongo-primary:27017 <<EOF
config = {
  "_id": "rs0",
  "members": [
    { "_id": 0, "host": "mongo-primary:27017", "priority": 2 },
    { "_id": 1, "host": "mongo-secondary:27017", "priority": 1 }
  ]
}

rs.initiate(config)
EOF

echo "Waiting for replica set to stabilize..."
sleep 10

echo "Checking replica set status..."
mongosh --host mongo-primary:27017 --eval "rs.status()"

echo "MongoDB replica set initialized"
