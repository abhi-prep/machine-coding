# Cron Parser

A Java command-line application that parses and pretty-prints standard cron expressions in a structured tabular format.

---

## Features

- Parses standard cron expressions:

  - Minute, Hour, Day of Month, Month, Day of Week, and Command

- Displays parsed output in a clean, human-readable table
- Includes comprehensive unit and integration tests

---

## Prerequisites

- **Java JDK 20** or higher
  👉 [Download OpenJDK 20](https://jdk.java.net/20/)

To verify your installation:

```sh
java -version
```

---

## Setup & Build

### In Terminal

```sh
# Step 1: Define the runtime classpath
CP="out:lib/junit-platform-console-standalone-1.13.1.jar:lib/byte-buddy-1.17.5.jar:lib/byte-buddy-agent-1.17.5.jar:lib/mockito-core-5.18.0.jar:lib/objenesis-3.4.jar"

# Step 2: Compile source and test files
javac -cp "$CP" -d out $(find src test -name "*.java")
```

---

## Running the Application

```sh
java -cp "$CP" CronParserApplication "*/15 0 1,15 * 1-5 /usr/bin/find"
```

This will output a tabular breakdown of the cron fields.

---

## Running Tests

```sh
java -jar lib/junit-platform-console-standalone-1.13.1.jar \
  --class-path "$CP" \
  --scan-class-path \
  --details tree
```

This will scan and execute all available unit and integration tests.


#####
1. Reverse Range
2. Next n occurrence
3. year optional field
4. Command params
5. Named Weeks and Months