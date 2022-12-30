FROM amazoncorretto:18
RUN mkdir /app

WORKDIR /app

ADD ./scrapper-api/target/scrapper-api-1.0-SNAPSHOT.jar /app

EXPOSE 8080

CMD ["java", "-jar", "scrapper-api-1.0-SNAPSHOT.jar"]
#ENTRYPOINT ["java", "-jar", "scrapper-api-1.0-SNAPSHOT.jar"]
#CMD java -jar scrapper-api-1.0-SNAPSHOT.jar
