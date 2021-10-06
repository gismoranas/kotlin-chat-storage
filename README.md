# Chat Storage Application

This is a demo CRUD application that uses kotlin and spring boot. The data saved in the H2 database are 
messages belonging to chat rooms. A full chat application would use a socket in order to
enable real-time communication, and a backend like this can be used just to store data. 
The API's need to be secured in order to prevent unauthorized use.

### Build and Run the Application
    ./gradlew build
    docker build . -t chat-storage
    docker run -p 8080:8080 chat-storage
java 11, kotlin 1.5 and gradle 7.2 let you run the build, for example. 
I used docker 20 to run the application.
       
### Use the Application
You need an http client like curl or postman and can send requests to the main API, which is 
[RoomController](src/main/kotlin/de/cmanigrasso/chatstorage/api/RoomController.kt). 
The life cycle for a room is:
- create a new room,
- send messages to the room,
- archive the room. 

Once the room is archived no more messages can be published there.

There is also an admin/debugging API under 
[MessageController](src/main/kotlin/de/cmanigrasso/chatstorage/api/MessageController.kt).

The data is stored in files in ./data and you can just delete the folder to clean up:
   
    rm -rf ./data
       
