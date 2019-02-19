FROM anapsix/alpine-java
COPY build/libs/gradle-calc-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
