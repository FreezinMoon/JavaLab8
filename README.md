# JavaLab8
My eighth and final laboratory work on Java programming in my first year at ITMO

_________________________________________________________________________________
**Divide the program from lab 5 into a client and a server module. The server module must execute collection management commands. The client module must interactively read the commands, pass them to the server for execution, and display the results of execution.**

The following requirements must be met:

Collection object processing operations must be implemented using the Stream API using lambda expressions.
Objects must be passed between client and server in serialized form.
Objects in the collection passed to the client must be sorted by name
The client must correctly handle temporary server unavailability.
The data exchange between the client and the server must be done using the UDP protocol
It is necessary to use a network channel for data exchange on the server
Datagrams should be used for data exchange on the client
Network channels must be used in non-blocking mode.
The responsibilities of the server application:

Handling the file storing the collection.
Managing the collection of objects.
Assigning automatically generated fields to objects in the collection.
Expecting connections and requests from the client.
Processing received requests (commands).
Saving collection to a file at application shutdown.
Saving the collection to a file when executing a special command that only the server can send (the client cannot send such a command).
The server application must consist of the following modules (implemented as one or more classes):
The connection-receiving module.
The request reading module.
The module of processing of received commands.
A module for sending responses to a client.

Duties of the client application:

Reading commands from the console.
Validation of input data.
Serialization of the entered command and its arguments.
Sending the command and its arguments to the server.
Processing the response from the server (outputting the command execution result to the console).
The save command must be removed from the client application.
The exit command terminates the client application.
Important: Commands and their arguments must be class objects. It is unacceptable to exchange "simple" strings. For example, for an add command or its analog, you must form an object containing the command type and the object to be stored in your collection.
Additional task:
Implement logging of different stages of server operation (starting work, receiving a new connection, receiving a new request, sending a response, etc.) using Logback
_________________________________________________________________________________

**Revise the program from the laboratory work №6 as follows:**

Organize storage of the collection in a relational DBMS (PostgresQL). Eliminate storing the collection in a file.
Use database tools (sequence) to generate the id field.
Update the state of the collection in memory only when the object is successfully added to the database
All commands of data retrieval should work with the collection in memory, not in the database.
Arrange the possibility of registration and authorization of users. The user has the ability to specify a password.
Passwords should be hashed using the SHA-224 algorithm when storing them
Prohibit the execution of commands by unauthorized users.
When storing objects, save the information about the user who created the object.
Users should be able to view all the objects in the collection, but they can only modify objects that belong to them.
To identify the user, send a username and password with each request.
It is necessary to implement multi-threaded processing of requests.

For multithreading of requests use Cached thread pool
To process a received request in multiple threads use Fixed thread pool.
To send reply with multiple threads use Fixed thread pool
To synchronize collection access, use read and write synchronization with java.util.concurrent.locks.ReentrantLock
_________________________________________________________________________________
The interface must be implemented using the JavaFX library
The GUI of the client side must support Russian, Macedonian, Croatian and English (India) languages/locales. Should provide correct display numbers, date and time in accordance with locale. Language switching should be done without restarting the application. The localized resources must be stored in a properties file.
**Revise the program from the laboratory work #7 as follows:**

Replace the console client with a client with a graphical user interface (GUI). 
The client functionality must include:

Window with authorization/registration.
Display the current user.
A table showing all the objects in the collection
Each field of the object is a separate table column.
The table rows can be filtered/sorted by the values of any of the columns. Implement sorting and filtering of column values using Streams API.
Support for all commands from previous labs.
An area visualizing the objects of the collection
Objects should be drawn using Graphics primitives using Graphics, Canvas, or similar graphics library tools.
When visualizing, use the object's coordinate and size data.
Objects from different users must be drawn in different colors.
When you click on an object, information about that object should be displayed.
When adding/removing/modifying an object, it should automatically appear/disappear/change in the area of both the owner and all other clients. 
When rendering an object, the animation agreed upon with the instructor must be played.
It is possible to edit individual fields of any of the objects (belonging to the user). You can switch to editing the object from the table with the general list of objects and from the area with the visualization of the object.
It is possible to delete the selected object (even if there was no delete command before).
