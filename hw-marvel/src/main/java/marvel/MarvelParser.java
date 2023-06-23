/*
 * Copyright (C) 2023 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Winter Quarter 2023 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package marvel;

import java.io.*;
import java.util.*;
//import java.util.List;
import java.util.stream.Collectors;

/**
 * Parser utility to load the Marvel Comics dataset.
 */
public class MarvelParser {

    /**
     * Reads the Marvel Universe dataset. Each line of the input file contains a character
     * name and a comic book the character appeared in, separated by a comma character
     *
     * @param filename The file that will be read.
     * @return a list containing a map from character to book and a map from book to character.
     * @spec.requires filename is a valid file in the resources/data folder.
     */
    // TODO: Replace 'void' with the type you want the parser to produce
    public static List<Map<String, List<String>>> parseData(String filename) {
        List<String> lines = readLines(filename);

        // TODO: Complete this method. You'll need to:
        //  - Split each line into its individual parts
        //  - Collect the data into some convenient data structure(s) to return to the graph building code
        List<Map<String, List<String>>> res = new ArrayList<>();
        Map<String, List<String>> charToBook = new HashMap<>();
        Map<String, List<String>> bookToChar = new HashMap<>();

        for (String line : lines) {
            String[] charBook = line.split(",");
            String character = charBook[0];
            String book = charBook[1];

            if (!charToBook.containsKey(character)) {
                charToBook.put(character, new ArrayList<>());
            }
            charToBook.get(character).add(book);
            if (!bookToChar.containsKey(book)) {
                bookToChar.put(book, new ArrayList<>());
            }
            bookToChar.get(book).add(character);
        }

        res.add(charToBook);
        res.add(bookToChar);
        return res;
    }

    /**
     * Reads all lines contained within the provided data file, which is located
     * relative to the data/ folder in this parser's classpath.
     *
     * @param filename The file to read.
     * @throws IllegalArgumentException if the file doesn't exist, has an invalid name,
                                        or can't be read
     * @return A new {@link List<String>} containing all lines in the file.
     */
    private static List<String> readLines(String filename) {
        // Note:
        // Most students won't re-write this code anywhere, this explanation is just for
        // completeness.
        //
        // You can use this code as an example for getting a file from the resources folder
        // in a project like this. If you access data files elsewhere in your code, you'll
        // need to use similar code. If you use this code elsewhere, don't forget:
        //   - Replace 'MarvelParser' in `MarvelParser.class' with the name of the class you
        //       write this in.
        //   - If the class is in src/main, it'll get resources from src/main/resources
        //   - If the class is in src/test, it'll get resources from src/test/resources
        //   - The "/" at the beginning of the path is important
        InputStream stream = MarvelParser.class.getResourceAsStream("/data/" + filename);
        if (stream == null) {
            // The file doesn't exist. We want to handle this case so we don't try to call
            // readLines and have a null pointer exception.
            throw new IllegalArgumentException("No such file: " + filename);
        }
        return new BufferedReader(new InputStreamReader(stream)).lines().collect(Collectors.toList());
    }
}
