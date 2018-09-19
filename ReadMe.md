=================================================================================================
Instructions to generate Compare.jar for comparing responses of requests from two different files
=================================================================================================

Below command Generates executable jar in ./lib folder:

    $ mvn clean package


Unzip compare-response.zip to compare-response folder

=================================================================================================
Instructions to Execute compare.bat (WINDOWS)
=================================================================================================

    $ cd compare-response/execute
    $ compare.bat file1.txt file2.txt

=================================================================================================
Instructions to Execute compare.sh (LINUX)
=================================================================================================

    $ cd compare-response/execute
    $ chmod 777 compare.sh
    $ ./compare.sh file1.txt file2.txt
