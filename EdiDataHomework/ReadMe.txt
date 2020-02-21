README: Edi Data processing homework
To run, run as Java application with main class ParseDriver
Application looks for csv in hard-coded relative path 'input/ediInput1.csv'
Application outputs file for each insurance company under directory with relative path of 
'output/<timestamp>'.
Timestamp allows for easy re-run of job if necessary.
Please note: When run under eclipse, the /output/ directory must be present. This is due
to the eclipse project folder becoming read-only during execution.