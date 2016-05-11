
# start the GlassFish Server
../glassfish/glassfish4/bin/asadmin start-domain

# create JDBC connection pool
../glassfish/glassfish4/bin/asadmin --echo --user admin --passwordfile passwords.txt create-jdbc-connection-pool --datasourceclassname org.apache.derby.jdbc.ClientDataSource40 --restype javax.sql.DataSource --property portNumber=1527:password=csoport1:user=csoport1:serverName=localhost:databaseName=moviesite moviePool


#create JDBC resource
../glassfish/glassfish4/bin/asadmin --echo --user admin --passwordfile passwords.txt create-jdbc-resource --connectionpoolid moviePool jdbc/movie

#################################################

#Creating a JMS Physical Destination: queue
#../glassfish/glassfish4/bin/asadmin --echo --user admin --passwordfile passwords.txt create-jmsdest --desttype queue MovieQueue

#Creating a JMS Physical Destination: topic
#../glassfish/glassfish4/bin/asadmin --echo --user admin --passwordfile passwords.txt create-jmsdest --desttype topic MovieTopic

#################################################

#Creating a JMS Connection Factory
../glassfish/glassfish4/bin/asadmin --echo --user admin --passwordfile passwords.txt create-jms-resource --restype javax.jms.ConnectionFactory  jms/MovieConnectionFactory

# Creating a JMS Destination Resource, whose JNDI name is jms/MovieQueue
 ../glassfish/glassfish4/bin/asadmin --echo --user admin --passwordfile passwords.txt create-jms-resource --restype javax.jms.Queue jms/MovieQueue

# Creating a JMS Destination Resource, whose JNDI name is jms/MovieTopic.
../glassfish/glassfish4/bin/asadmin --echo --user admin --passwordfile passwords.txt create-jms-resource --restype javax.jms.Topic jms/MovieTopic

#################################################
# stop the GlassFish Server
../glassfish/glassfish4/bin/asadmin stop-domain

