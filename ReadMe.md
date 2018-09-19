Download the project compare-response.zip

Unzip the project to the folder : compare-response

Change to compare-response directory

$ cd compare-response

Below command Generates executable jar files with dependencies:

$ mvn clean package

Run the Compare API responses program with:

$ java -jar lib/Compare.jar target/test-classes/gojek/file1.txt target/test-classes/gojek/file2.txt

