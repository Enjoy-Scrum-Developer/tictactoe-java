TicTacToe Game

This application runs over Java 1.8

```mermaid
graph TD;
    Game --> GameState;
    Game --> Ai;
    Game --> Board
    Game --> Messages
    Ai --> GameState
```

To run this application, build first through:
```
mvn clean package
```

And then, execute program:
```
java -jar target/tictactoe-java-1.0-SNAPSHOT.jar
```
