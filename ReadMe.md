
Executable jar Compare.jar is available in ./lib folder

=================================================================================================
Instructions to generate Compare.jar for comparing responses of requests from two different files:
=================================================================================================

Below command Generates executable jar in ./lib folder:

    $ mvn clean package

Run the program with existing files in target/test-classes/gojek

    $ java -jar lib/Compare.jar target/test-classes/gojek/file1.txt target/test-classes/gojek/file2.txt

If program is running in JDK9+ add the following option to the JVM to disable the warning from Spring's use of CGLIB

    $ java --add-opens java.base/java.lang=ALL-UNNAMED -jar lib/Compare.jar target/test-classes/gojek/file1.txt target/test-classes/gojek/file2.txt


