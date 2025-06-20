# Two-Figure Board Puzzle (1.11. Task)

## Task description

You are given a game board consisting of 8 × 8 numbered cells. Two figures are initially placed in the top-left corner, and both must move simultaneously in each step according to the following rules:

- The figures can move vertically and horizontally.
- A figure must move exactly as many cells as the number in its current position indicates.
- If one figure moves vertically, the other must move horizontally.

The goal is to move both figures simultaneously to the target cell located in the bottom-right corner. If only one figure reaches the target cell in a given move, or if either figure steps onto a cell with a 0 (from which it cannot move further), then no further moves can be made from that state.

| 3 | 5 | 0 | 2 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|---|---|
| 1 | 2 | 2 | 1 | 4 | 5 | 2 | 0 |
| 2 | 0 | 1 | 3 | 4 | 3 | 2 | 1 |
| 4 | 4 | 0 | 2 | 3 | 0 | 5 | 2 |
| 4 | 1 | 0 | 3 | 3 | 2 | 4 | 3 |
| 1 | 0 | 2 | 2 | 3 | 0 | 1 | 0 |
| 4 | 0 | 2 | 2 | 1 | 4 | 0 | 1 |
| 2 | 2 | 0 | 4 | 3 | 5 | 4 | * |

## Building from Source

Building the project requires JDK 24 or later and access to [GitHub Packages](https://docs.github.com/en/packages).

GitHub Packages requires authentication using a personal access token (classic) that can be created [here](https://github.com/settings/tokens).

> [!IMPORTANT]
> You must create a personal access token (PAT) with the `read:packages` scope.

You need a `settings.xml` file with the following content to store your PAT:

```xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">
    <servers>
        <server>
            <id>github</id>
            <username><!-- Your GitHub username --></username>
            <password><!-- Your GitHub personal access token (classic) --></password>
        </server>
    </servers>
</settings>
```

The `settings.xml` file must be placed in the `.m2` directory in your home directory, i.e., in the same directory that stores your local Maven repository.

## Solution

- FIGURE1: RIGHT
- FIGURE2: DOWN
- FIGURE1: DOWN
- FIGURE2: RIGHT
- FIGURE1: LEFT
- FIGURE2: DOWN
- FIGURE1: DOWN
- FIGURE2: LEFT
- FIGURE1: RIGHT
- FIGURE2: UP
- FIGURE1: RIGHT
- FIGURE2: DOWN
- FIGURE1: DOWN
- FIGURE2: RIGHT
