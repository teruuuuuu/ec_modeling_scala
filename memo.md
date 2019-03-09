

curl -i -XPOST -H 'Content-Type:application/json' -d '{"name": "user1", "password": "password" }' http://localhost:9000/login

SESSION=`curl -i --cookie cookie -d "_csrf=$TOKEN" -d name=user1 -d password=user1 -L http://localhost:8080/login | grep -E "SESSION\=[^ ]+" -o`



SESSION=`curl -i -XPOST -H 'Content-Type:application/json' -d '{"name": "user1", "password": "password" }' http://localhost:9000/login | grep -E "PLAY_SESSION\=[^ ]+" -o`
curl -i -XGET -b "$SESSION" http://localhost:9000/product/search
