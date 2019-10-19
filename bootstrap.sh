# Add java repository
add-apt-repository ppa:openjdk-r/ppa
#update the packaging
apt update
# Install open jdk 12
apt-get install -y openjdk-12-jdk
#install maven
apt-get install -y maven
# Install java packages
cd /pre_alation
mvn clean compile assembly:single
# run the program
# java -jar target/leaning-odbc-1.0-SNAPSHOT-jar-with-dependencies.jar