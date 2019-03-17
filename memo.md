

curl -i -XPOST -H 'Content-Type:application/json' -d '{"name": "user1", "password": "password" }' http://localhost:9000/login

SESSION=`curl -i --cookie cookie -d "_csrf=$TOKEN" -d name=user1 -d password=user1 -L http://localhost:8080/login | grep -E "SESSION\=[^ ]+" -o`


check_result() {
  echo $1
  SESSION=`echo $1 | grep -E "PLAY_SESSION\=[^ ]+" -o`
  echo $SESSION
}



# ログイン
RESULT=`curl -i -XPOST -H 'Content-Type:application/json' -d '{"name": "user1", "password": "password" }' http://localhost:9000/login`
check_result $RESULT

# 商品検索
curl -i -XGET -b "$SESSION" http://localhost:9000/product/search?name=product1

# 商品追加
RESULT=`curl -i -XPOST -b "$SESSION" -H 'Content-Type:application/json' -d '{"product_id": 1, "number": 3 }' http://localhost:9000/order/updateItem`
check_result $RESULT

# 購入確定
RESULT=`curl -i -XPOST -b "$SESSION" -H 'Content-Type:application/json' -d '{"bank_account": "123-4567" }' http://localhost:9000/order/bankConfirm`
check_result $RESULT

# ログアウト
RESULT=`curl -i -XPOST -b "$SESSION" http://localhost:9000/logout`
check_result $RESULT


