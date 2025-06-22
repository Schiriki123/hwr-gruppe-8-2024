# HWR OOP Student Project Group 8: Chess

This repository contains a student project created for an ongoing lecture on object-oriented
programming with Kotlin at HWR Berlin (summer term 2025).

> :warning: This code is for educational purposes only. Do not rely on it!

## Prerequisites

Installed:

1. IDE: IntelliJ IDEA 2025.1.1.1 (Ultimate Edition)
2. SDK: Eclipse temurin-23.0.2
3. Maven installed
4. Git installed

## Local Development

This project uses [Apache Maven][maven] as build tool.

To build from your shell (without an additional local installation of Maven), ensure that `./mvnw`
is executable:

```
chmod +x ./mvnw
```

I recommend not to dive into details about Maven at the beginning.
Instead, you can use [just][just] to build the project.
It reads the repositories `justfile` which maps simplified commands to corresponding sensible Maven
calls.

With _just_ installed, you can simply run this command to perform a build of this project and run
all of its tests:

```
just build
```

## Abstract

Our project is a program that simulates the popular board game known as chess. Our intended goal is, that  
two players can play against each other only using the command line interface (cli).

The game features all possible rules found in chess, like en passant, castling and pawn promotion. You can play
together with another player and every move you make the current board is saved, so you can resume the game
at any point.

One of our biggest difficulties was the game logic which at some points was misleading. Especially the implementation of
the movement for every piece.
To bring us right on track, our teacher hinted towards a better solution using the implementation of 'direction'. This
helped us understand the misconception, thus facilitating the process
of "move"-ing the pieces.

## Feature List

[TODO]: # (For each feature implemented, add a row to the table!)

| Number | Feature                                  | Tests |
|--------|------------------------------------------|-------|
| 1      | create new game                          |       |
| 2      | make moves based on piece logic          |       |
| 3      | Detect checkmate                         |       |
| 4      | save & load multiple games as fen string |       |
| 5      | cli for user input                       |       |
| 6      | move clocks                              |       |
| 7      | detection of all draw possibilities      |       |
| 8      | pawn promotion                           |       |
| 9      | en passant                               |       |
| 10     | castling                                 |       |

## Additional Dependencies

[TODO]: # (For each additional dependency your project requires- Add an additional row to the table!)

| Number | Dependency Name | Dependency Description                           | Why is it necessary?                                |
|--------|-----------------|--------------------------------------------------|-----------------------------------------------------|
| 1      | clikt           | a library that simplifies writing cli interfaces | /                                                   |
| 2      | arcmutate       | the kotlin extension for pitest                  | pitest started to generate a lot of false positives | 

### Formatting

The repository contains an IntelliJ IDEA formatter configuration file.
It is located in the `.intellij` folder (not in `.idea`, which is the folder created by IntelliJ IDEA).
To use the formatter, you need to import the configuration into your IntelliJ IDEA settings.

Under **Settings**, go to **Editor**, then **Code Style**, click the **Gear Symbol** next to the Dropdown, click *
*Import Scheme**, then **IntelliJ IDEA code style XML**. Finally, select the `.intellij/formatter.xml` file.

Make sure to always use the imported `HWR OOP` code style when formatting your code.
Be aware that it might differ from the code style configured in your *Project*, or IntelliJ's *Default* code style.

[maven]: https://maven.apache.org/

[just]: https://github.com/casey/just
