onoff-similarity
================

Closed-form similarity measurement for on/off time patterns.

Many signals are represented by on/off patterns that switch over time (e.g.  Morse code). The patterns can be represented by integer number sequences, where on and off times alternate. For instance, Morse "..." might be the sequence {200^on, 200^off, 200^on, 200^off, 200^on} (ms). Suppose we want to make a Morse recognizer, and are listening to signals to match with our template "...". If we receive {210,251,192,201,203}, how can we compare it to our template?

This small library can compute measures of similarity between two such time sequences, as a score between 0 (completely different) and 1 (equal).

