FROM java:8
ADD dingding-robot-notify-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT exec java -jar app.jar
