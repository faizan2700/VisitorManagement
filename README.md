Visitor Management Software uses : 

1. Java
2. JavaFX using scene builder
3. Apache Derby
4. Jfoenix 
5. Javax.mail package
6. Twilio API

Visitor Management Features : 

You will be asked for username and password in beginning for administrator authentication (password is shaHex
hashing for security puproses)

username : admin
password : admin

(MVC Design Pattern)

1. Email sending Using TLS authentication
2. Showing present visitors in company in table view 
3. Showing the details of present visitors in company like phone,email etc in table
4. Resizable column of table
5. During Check in no need to type the checkin time, software will add it
6. Storing the visitor information in Relational Database table (derby)
7. After checkin the host will get an email from email service of software 
8. Appropriate information window appears after checkin checkout operations (failed , success , all field required)
8. Check out adds the information of visitor automatically when given enough unique information	
	( i. for example if the user 'xyz' visitor is only visitor with name 'xyz' just type xyz in name field 
		and hit Enter all the informaiton will appear and visitor will check out.
	  ii. if visitor there are two visitor with name 'abc' in company right now, but only one is meeting 
	  Mr. Karan then type abc , and Mr. Karan in name and host field and hit Enter. All the information will appear 
	  and user will be checked out
	  )
9. After check out the visitor is present in database for permanent record but not the visitor table as only the
	present or current visitors are show in present visitor table
10. Only one database object is used throughtout the whole application to avoid errors
11. database implements methods for executing queries, actions to provide database access to other modules

12. Functionality to add and remove host
13. Storing the record book for previously came visitors and not currently present
14. with few changes MySQL or PostgreSQL database can be implemented