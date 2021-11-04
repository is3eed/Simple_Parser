# Simple_Parser

small language parser using java PL.
In this program algorithm used for parsing is top down parsing (non-recursive Predictive Parsing) 
where we need to use a table to take decisions, to constructer this table first we need to constructer First and Follow sets
in this project constructing first and follow is not necessary, since we have the table already.
now in order to accomplish parsing we need :-

1. Input buffer (the expression which we read from file) 
2. Stack (contains the grammar symbols, and $ at the end)
3. Parsing table (which is given  in our case)

the algorithm is simple for tracing and code is commented to explain some points
