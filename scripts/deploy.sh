#!/usr/bin/env bash

REPOSITORY=/home/ec2-user/my-app
cd $REPOSITORY

APP_NAME=my-project
JAR_NAME=$(ls $REPOSITORY/build/libs/ | grep 'SNAPSHOT.jar' | tail -n 1)
JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME

CURRENT_PID=$(pgrep -f $APP_NAME)

if [ -z "$CURRENT_PID" ]; then
  echo "> 종료할것 없음."
else
  echo "> kill -9 $CURRENT_PID"
  kill -9 $CURRENT_PID
  sleep 5
fi

PROPERTIES_FILE="$REPOSITORY/application-server.properties"
echo "> $JAR_PATH 배포"
nohup java -jar $JAR_PATH -Dapp.name=$APP_NAME --spring.config.location=file:$PROPERTIES_FILE > app.log 2>&1 &




 