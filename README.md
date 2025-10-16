# Overview

As a software engineer, I'm working to master Java fundamentals and dynamic programming algorithms to build more complex applications in the future. This DNA Sequence Aligner demonstrates core Java concepts like classes, methods, loops, conditionals, and the ArrayList from the Collections Framework.

I wrote a program that takes two DNA sequences and aligns them using the Needleman-Wunsch algorithm - a classic bioinformatics method. It calculates a similarity score (matches = +1, mismatches = -1, gaps = -2) and shows the actual aligned sequences with visual symbols. The purpose is to practice Java's object-oriented structure while solving a real-world computational biology problem.

[Software Demo Video](https://www.youtube.com/watch?v=8ALbKJixP1w)

# Development Environment

I developed this using **Visual Studio Code** with the **"Extension Pack for Java"** extension, which handles compilation and running automatically with just F5.

**Java 1.8.0_461** with standard libraries:

- **ArrayList** (Collections Framework)
- **Scanner**, **File**, **PrintWriter** for file I/O

# Useful Websites

These resources were super helpful for learning and debugging:

- [Java Tutorial - W3Schools](https://www.w3schools.com/java/) - Clear examples for loops, methods, classes
- [Needleman-Wunsch Algorithm - Wikipedia](https://en.wikipedia.org/wiki/Needleman–Wunsch_algorithm) - Algorithm explanation
- [Java ArrayList Tutorial - GeeksforGeeks](https://www.geeksforgeeks.org/arraylist-in-java/) - How to use ArrayList properly
- [Oracle Java File I/O Tutorial](https://docs.oracle.com/javase/tutorial/essential/io/) - File reading/writing basics
- [Grok - AI Assistant for Syntax and Concepts](https://grok.x.ai/) - Checked my syntax and explained traceback logic
- [Java Tutorial for Beginners - Programming with Mosh (YouTube)](https://www.youtube.com/watch?v=eIrMbAQSU34) - 1-hour Java crash course
- [Needleman-Wunsch Algorithm Explanation (YouTube)](https://www.youtube.com/watch?v=of3B02hZGS0) - Visual breakdown of the matrix

# Future Work

- Add support for protein sequences (20 amino acids instead of 4 DNA bases)
- Create a simple Swing GUI for easier input/display
- Optimize memory usage for very long sequences (>1000 bases)
- Add multiple alignment for 3+ sequences
