package com.jpmorgan.onlinetest.others;
/*
[Question #7 – Algorithms]
Our movie streaming service logs the usage of the service to a text file for each subscribed user.

The logs contain the date that a movie was viewed, the name of the movie, the length of the movie and the number of minutes watched by the user and the genre of the movie. An example of a log file is shown below:

9/24/2016 The Magnificent Seven 133min 126min Action
9/30/2016 Miss Peregrine's Home for Peculiar Children 127min 100min Fantasy
11/5/2015 Trolls 92min 40min Fantasy
11/5/2015 Doctor Strange 115min 110min Fantasy
11/19/2016 Fantastic Beasts and Where to Find Them 133min 120min Fantasy
11/12/2016 Arrival 116min 20min SciFi
Write a program that will read this log file and produce the following information:

An ordered list of movies watched by length
The average percentage of the movie that this user watches. That is, if a movie is 60 minutes long and the user watched 30 minutes of the movie, the percentage watched is 50%. What is this average over all movies watched.
The user’s favourite genre of movies. This is determined by first removing all movies where the user watched less than 50% of the movie then counting the movies in each genre that remains.
Why this Java interview question?
This is a test for the candidate’s understanding of streams in Java 8, which are great for performing such data processing with compact yet readable code.

When asking the question, emphasis should be put upon the data processing part, so the candidate doesn’t get bogged down in the details of parsing the text format.

Possible answers
To parse the log lines, a regular expression should do fine, but as noted, this is not the core part of the question. If you are short on time during the interview, tell the candidate to assume they already have a LogEntry class or similar, with suitable getters.

This regular expression string could be used to parse the input:

"^(\\d+)/(\\d+)/(\\d+) (.*) (\\d+)min (\\d+)min (\\S+)$"
Stream implementations of the requested output could look like this. To sort watched movies by length, assuming that logEntries is a List<LogEntry>:

logEntries
    .stream()
    .sorted((a, b) -> a.getLength() - b.getLength())
    .map(logEntry -> logEntry.toString())
    .collect(Collectors.joining("\n"))
To determine the average percentage watched:

logEntries
    .stream()
    .mapToDouble(logEntry -> (double) logEntry.getWatched() / logEntry.getLength())
    .average()
    .getAsDouble()
To determine their favorite genre:

logEntries
    .stream()
    .filter(logEntry -> logEntry.getWatched() * 2 >= logEntry.getLength())
    .collect(Collectors.groupingBy(LogEntry::getGenre, Collectors.counting()))
    .entrySet()
    .stream()
    .collect(Collectors.maxBy((a, b) -> (int) (a.getValue() - b.getValue())))
    .get()
    .getKey()
Of course, it’s unlikely that any interview candidate will write this out correctly without referring to documentation or IDE autocompletion.

You’re not looking for encyclopedic knowledge of the streams API, but rather for a general understanding of how it works and how it can be used to solve such problems.

Follow-up questions
You could drill into error handling. What happens if the input does not conform to the expected format? What if we try to compute the average of an empty list of numbers?
 */
public class Movies {
}
