FROM adoptopenjdk/openjdk11:latest
RUN mkdir /app
COPY build/libs/chat-storage-0.0.1-SNAPSHOT.jar /app/chat-storage.jar
CMD ["java", "-jar", "/app/chat-storage.jar"]
