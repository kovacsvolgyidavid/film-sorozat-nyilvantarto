# set glassfish home folder
GLASSFISH_BIN=../glassfish/glassfish4/bin

# start the GlassFish Server
$GLASSFISH_BIN/asadmin start-domain

# create JDBC connection pool
$GLASSFISH_BIN/asadmin --echo --user admin --passwordfile passwords.txt create-jdbc-connection-pool --datasourceclassname org.apache.derby.jdbc.ClientDataSource40 --restype javax.sql.XADataSource --property portNumber=1527:password=csoport1:user=csoport1:serverName=localhost:databaseName=moviesite moviePool11432


#create JDBC resource
$GLASSFISH_BIN/asadmin --echo --user admin --passwordfile passwords.txt create-jdbc-resource --connectionpoolid moviePool jdbc/Movie254542f

#################################################

#Creating a JMS Physical Destination: queue
$GLASSFISH_BIN/asadmin --echo --user admin --passwordfile passwords.txt create-jmsdest --desttype queue MovieQueue2352

#Creating a JMS Physical Destination: topic
$GLASSFISH_BIN/asadmin --echo --user admin --passwordfile passwords.txt create-jmsdest --desttype topic MovieTopic2352

#################################################

#Creating a JMS Connection Factory
$GLASSFISH_BIN/asadmin --echo --user admin --passwordfile passwords.txt create-jms-resource --restype javax.jms.ConnectionFactory  jms/MovieConnectionFactory2355

# Creating a JMS Destination Resource, whose JNDI name is jms/MovieQueue
 $GLASSFISH_BIN/asadmin --echo --user admin --passwordfile passwords.txt create-jms-resource --restype javax.jms.Queue jms/MovieQueue2355

# Creating a JMS Destination Resource, whose JNDI name is jms/MovieTopic.
$GLASSFISH_BIN/asadmin --echo --user admin --passwordfile passwords.txt create-jms-resource --restype javax.jms.Topic jms/MovieTopic2355

#################################################
# stop the GlassFish Server
$GLASSFISH_BIN/asadmin stop-domain

