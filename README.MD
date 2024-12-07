# Bingo Strip Generator

## Introduction
This project solves the problem of generating bingo strips in a systematic way. It is inspired by and based on the repository found here: [Ticket Generator Challenge](https://github.com/lindar-open/ticket-generator-challenge).

The program generates bingo tickets with unique numbers and arranges them into a predefined format. Each ticket adheres to the rules of having numbers distributed across rows and columns, ensuring randomness and uniqueness within the constraints.

## How It Works

1. **Create Groups of Elements**:
    - The program starts by dividing 90 elements into 9 groups, where each group contains a range of numbers (e.g., 1-9, 10-19, etc.).

2. **Divide into Tickets**:
    - These groups are used to create 6 tickets with unique numbers.
    - Each ticket contains numbers such that there is a minimum of 1 and a maximum of 3 elements from each group from the beginning.

3. **Insert Blanks (Zeros)**:
    - To ensure the bingo ticket format, blanks (represented as `0`) are inserted in a randomized manner.
    - The numbers are then arranged into rows and columns to complete the bingo ticket format.

## Prerequisites

1. Java Development Kit (JDK) 17 installed.
2. Maven 3.6.0 or higher
3. A terminal or IDE for running the application.

## Installation

1. Clone or download the repository to your local machine:
   ```bash
   git clone https://github.com/TatarDaniel/bingo-strip-generator.git
   cd ticket-generator-challenge
   ```

2. Verify the installation:
   ```bash
   mvn clean install
   ```

## How to Run

### Using Maven

You can run the application using Maven's `exec:java` plugin.

#### Without Arguments
To run the application with default parameters (1 group, no strips shown):
```bash
mvn exec:java
```

#### With Arguments
You can customize the behavior using the following arguments:

- `count=<number>`: Specifies how many groups to generate (default is `1`).
- `showStrips=true`: Displays the generated strips in a grid format (default is `false`).

##### Example Commands:

1. Generate 5 groups without showing strips:
   ```bash
   mvn exec:java -Dexec.args="count=5"
   ```

2. Generate 3 groups and display the strips:
   ```bash
   mvn exec:java -Dexec.args="count=3 showStrips=true"
   ```

### Using Command Line (Without Maven)

1. Compile the project:
   ```bash
   javac -d target/classes src/main/java/mrq/*.java
   ```

2. Run the project:
   ```bash
   java -cp target/classes mrq.BingoStripGenerator count=3 showStrips=true
   ```

## Project Structure

- **`src/main/java/mrq`**: Contains the main classes for the application.
    - `Main`: Main class for running the application.
    - `GroupGenerator`: Handles the creation of groups for tickets.
    - `TicketGenerator`: Manages the generation of bingo tickets.
    - `DistributionGenerator`: Allocates numbers across groups.
    - `TicketPrinter`: Handles the formatting and display of generated tickets.
- **`src/test/java/mrq`**: Contains test classes for the application.
    - `TicketGeneratorTest`: Tests for the ticket generation logic.

## Example Output

### Default Execution
```text
Running with count: 1
Show strips: false
GROUP 1

Elapsed time in seconds: 0.0151368
Memory used in MB: 0.2906646728515625
```

### With `showStrips=true`
```text
Running with count: 2
Show strips: true
GROUP 1

┌─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┐
│     │     │21   │     │     │52   │65   │75   │82   │
├─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤
│ 5   │     │23   │32   │     │53   │     │     │83   │
├─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤
│     │14   │25   │     │45   │55   │     │     │89   │
└─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┘

... 

┌─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┐
│     │13   │     │     │41   │57   │60   │73   │     │
├─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤
│     │15   │28   │33   │     │     │68   │     │80   │
├─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤
│ 8   │17   │29   │     │     │     │     │79   │87   │
└─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┘

GROUP 2
... (additional tickets printed here) ...

Elapsed time in seconds: 0.45678
```