=================================================================================================
Instructions to execute Compare.jar for comparing responses of requests from two different files:
=================================================================================================

Download compare-response.zip

Unzip the project to the folder : compare-response

Change to compare-response directory

    $ cd compare-response

Below command Generates executable jar in ./lib folder:

    $ mvn clean package

Run the program with existing files in target/test-classes/gojek

    $ java -jar lib/Compare.jar target/test-classes/gojek/file1.txt target/test-classes/gojek/file2.txt