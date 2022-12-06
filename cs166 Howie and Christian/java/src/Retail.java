/*
 * Template JAVA User Interface
 * =============================
 *
 * Database Management Systems
 * Department of Computer Science &amp; Engineering
 * University of California - Riverside
 *
 * Target DBMS: 'Postgres'
 *
 */


import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;
import java.lang.Math;

/**
 * This class defines a simple embedded SQL utility class that is designed to
 * work with PostgreSQL JDBC drivers.
 *
 */
public class Retail {

   public static String userType = "";
   public static int userID = -1;
   public static double currUserLat = -1.0;
   public static double currUserLong = -1.0;	  
   private String dbname, dbport, user, passwd;
   private String currUserIDString = Integer.toString(userID);

   // reference to physical database connection.
   private Connection _connection = null;

   // handling the keyboard inputs through a BufferedReader
   // This variable can be global for convenience.
   static BufferedReader in = new BufferedReader(
                                new InputStreamReader(System.in));

   /**
    * Creates a new instance of Retail shop
    *
    * @param hostname the MySQL or PostgreSQL server hostname
    * @param database the name of the database
    * @param username the user name used to login to the database
    * @param password the user login password
    * @throws java.sql.SQLException when failed to make a connection.
    */
   public Retail(String dbname, String dbport, String user, String passwd) throws SQLException {
      System.out.print("Connecting to database...");
      try{
         // constructs the connection URL
         String url = "jdbc:postgresql://localhost:" + dbport + "/" + dbname;
         System.out.println ("Connection URL: " + url + "\n");

         // obtain a physical connection
         this._connection = DriverManager.getConnection(url, user, passwd);
         System.out.println("Done");
      }catch (Exception e){
         System.err.println("Error - Unable to Connect to Database: " + e.getMessage() );
         System.out.println("Make sure you started postgres on this machine");
         System.exit(-1);
      }//end catch
   }//end Retail

   // Method to calculate euclidean distance between two latitude, longitude pairs. 
   public static double calculateDistance (double lat1, double long1, double lat2, double long2){
      double t1 = (lat1 - lat2) * (lat1 - lat2);
      double t2 = (long1 - long2) * (long1 - long2);
      return Math.sqrt(t1 + t2); 
   }
   /**
    * Method to execute an update SQL statement.  Update SQL instructions
    * includes CREATE, INSERT, UPDATE, DELETE, and DROP.
    *
    * @param sql the input SQL string
    * @throws java.sql.SQLException when update failed
    */
   public void executeUpdate (String sql) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the update instruction
      stmt.executeUpdate (sql);

      // close the instruction
      stmt.close ();
   }//end executeUpdate

   /**
    * Method to execute an input query SQL instruction (i.e. SELECT).  This
    * method issues the query to the DBMS and outputs the results to
    * standard out.
    *
    * @param query the input query string
    * @return the number of rows returned
    * @throws java.sql.SQLException when failed to execute the query
    */
   public int executeQueryAndPrintResult (String query) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the query instruction
      ResultSet rs = stmt.executeQuery (query);

      /*
       ** obtains the metadata object for the returned result set.  The metadata
       ** contains row and column info.
       */
      ResultSetMetaData rsmd = rs.getMetaData ();
      int numCol = rsmd.getColumnCount ();
      int rowCount = 0;

      // iterates through the result set and output them to standard out.
      boolean outputHeader = true;
      while (rs.next()){
		 if(outputHeader){
			for(int i = 1; i <= numCol; i++){
			System.out.print(rsmd.getColumnName(i) + "\t");
			}
			System.out.println();
			outputHeader = false;
		 }
         for (int i=1; i<=numCol; ++i)
            System.out.print (rs.getString (i) + "\t");
         System.out.println ();
         ++rowCount;
      }//end while
      stmt.close ();
      return rowCount;
   }//end executeQuery

   /**
    * Method to execute an input query SQL instruction (i.e. SELECT).  This
    * method issues the query to the DBMS and returns the results as
    * a list of records. Each record in turn is a list of attribute values
    *
    * @param query the input query string
    * @return the query result as a list of records
    * @throws java.sql.SQLException when failed to execute the query
    */
   public List<List<String>> executeQueryAndReturnResult (String query) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the query instruction
      ResultSet rs = stmt.executeQuery (query);

      /*
       ** obtains the metadata object for the returned result set.  The metadata
       ** contains row and column info.
       */
      ResultSetMetaData rsmd = rs.getMetaData ();
      int numCol = rsmd.getColumnCount ();
      int rowCount = 0;

      // iterates through the result set and saves the data returned by the query.
      boolean outputHeader = false;
      List<List<String>> result  = new ArrayList<List<String>>();
      while (rs.next()){
        List<String> record = new ArrayList<String>();
		for (int i=1; i<=numCol; ++i)
			record.add(rs.getString (i));
        result.add(record);
      }//end while
      stmt.close ();
      return result;
   }//end executeQueryAndReturnResult

   /**
    * Method to execute an input query SQL instruction (i.e. SELECT).  This
    * method issues the query to the DBMS and returns the number of results
    *
    * @param query the input query string
    * @return the number of rows returned
    * @throws java.sql.SQLException when failed to execute the query
    */
   public int executeQuery (String query) throws SQLException {
       // creates a statement object
       Statement stmt = this._connection.createStatement ();

       // issues the query instruction
       ResultSet rs = stmt.executeQuery (query);

       int rowCount = 0;

       // iterates through the result set and count nuber of results.
       while (rs.next()){
          rowCount++;
       }//end while
       stmt.close ();
       return rowCount;
   }

   /**
    * Method to fetch the last value from sequence. This
    * method issues the query to the DBMS and returns the current
    * value of sequence used for autogenerated keys
    *
    * @param sequence name of the DB sequence
    * @return current value of a sequence
    * @throws java.sql.SQLException when failed to execute the query
    */
   public int getCurrSeqVal(String sequence) throws SQLException {
	Statement stmt = this._connection.createStatement ();

	ResultSet rs = stmt.executeQuery (String.format("Select currval('%s')", sequence));
	if (rs.next())
		return rs.getInt(1);
	return -1;
   }

   /**
    * Method to close the physical connection if it is open.
    */
   public void cleanup(){
      try{
         if (this._connection != null){
            this._connection.close ();
         }//end if
      }catch (SQLException e){
         // ignored.
      }//end try
   }//end cleanup

   /**
    * The main execution method
    *
    * @param args the command line arguments this inclues the <mysql|pgsql> <login file>
    */
   public static void main (String[] args) {
      if (args.length != 3) {
         System.err.println (
            "Usage: " +
            "java [-classpath <classpath>] " +
            Retail.class.getName () +
            " <dbname> <port> <user>");
         return;
      }//end if

      Greeting();
      Retail esql = null;
      try{
         // use postgres JDBC driver.
         Class.forName ("org.postgresql.Driver").newInstance ();
         // instantiate the Retail object and creates a physical
         // connection.
         String dbname = args[0];
         String dbport = args[1];
         String user = args[2];
         esql = new Retail (dbname, dbport, user, "");

         boolean keepon = true;
         while(keepon) {
            // Thiese are sample SQL statements
            System.out.println("MAIN MENU");
            System.out.println("---------");
            System.out.println("1. Create user");
            System.out.println("2. Log in");
            System.out.println("9. < EXIT");
            String authorizedUser = null;
            switch (readChoice()){
               case 1: CreateUser(esql); break;
               case 2: authorizedUser = LogIn(esql); break;
               case 9: keepon = false; break;
               default : System.out.println("Unrecognized choice!"); break;
            }//end switch
            if (authorizedUser != null) {
              boolean usermenu = true;
              while(usermenu) {
		 if (authorizedUser.contains("admin")) {
                 System.out.println("MAIN MENU");
                 System.out.println("---------");
                 System.out.println("1. View Stores within 30 miles");
                 System.out.println("2. View Product List");
                 System.out.println("3. Place a Order");
                 System.out.println("4. View 5 recent orders");

               //the following functionalities basically used by managers
                 System.out.println("5. Update Product");
                 System.out.println("6. View 5 recent Product Updates Info");
                 System.out.println("7. View 5 Popular Items");
                 System.out.println("8. View 5 Popular Customers");
                 System.out.println("9. Place Product Supply Request to Warehouse");
		

                 System.out.println(".........................");
                 System.out.println("20. Log out");

		}
		else if (authorizedUser.contains("manager")) {
                 System.out.println("MAIN MENU");
                 System.out.println("---------");
                 System.out.println("1. View Stores within 30 miles");
                 System.out.println("2. View Product List");
                 System.out.println("3. Place a Order");
                 System.out.println("4. View 5 recent orders");

                 System.out.println("5. Update Product");
                 System.out.println("6. View 5 recent Product Updates of Your Stores");
                 System.out.println("7. View 5 Popular Items of Your Stores");
                 System.out.println("8. View 5 Popular Customers of Your Stores");
                 System.out.println("9. Place Product Supply Request to Warehouse");
		 System.out.println("10. View All Orders Of Your Stores");


                 System.out.println(".........................");
                 System.out.println("20. Log out");


} 
		else {
                 System.out.println("MAIN MENU");
                 System.out.println("---------");
                 System.out.println("1. View Stores within 30 miles");
                 System.out.println("2. View Product List");
                 System.out.println("3. Place a Order");
                 System.out.println("4. View 5 recent orders");
                 System.out.println(".........................");
                 System.out.println("20. Log out");

}          
                 switch (readChoice()){
                    case 1: viewStores(esql); break;
                    case 2: viewProducts(esql); break;
                    case 3: placeOrder(esql); break;
                    case 4: viewRecentOrders(esql); break;
                    case 5: updateProduct(esql); break;
                    case 6: viewRecentUpdates(esql); break;
                    case 7: viewPopularProducts(esql); break;
                    case 8: viewPopularCustomers(esql); break;
                    case 9: placeProductSupplyRequests(esql); break;
		    case 10: managerViewOrders(esql); break;

                    case 20: usermenu = false; break;
                    default : System.out.println("Unrecognized choice!"); break;
                 }
              }
            }
         }//end while
      }catch(Exception e) {
         System.err.println (e.getMessage ());
      }finally{
         // make sure to cleanup the created table and close the connection.
         try{
            if(esql != null) {
               System.out.print("Disconnecting from database...");
               esql.cleanup ();
               System.out.println("Done\n\nBye !");
            }//end if
         }catch (Exception e) {
            // ignored.
         }//end try
      }//end try
   }//end main

   public static void Greeting(){
      System.out.println(
         "\n\n*******************************************************\n" +
         "              User Interface      	               \n" +
         "*******************************************************\n");
   }//end Greeting

   /*
    * Reads the users choice given from the keyboard
    * @int
    **/
   public static int readChoice() {
      int input;
      // returns only if a correct value is given.
      do {
         System.out.print("Please make your choice: ");
         try { // read the integer, parse it and break.
            input = Integer.parseInt(in.readLine());
            break;
         }catch (Exception e) {
            System.out.println("Your input is invalid!");
            continue;
         }//end try
      }while (true);
      return input;
   }//end readChoice

   /*
    * Creates a new user
    **/
   public static void CreateUser(Retail esql){
      try{
         System.out.print("\tEnter name: ");
         String name = in.readLine();
         System.out.print("\tEnter password: ");
         String password = in.readLine();
         System.out.print("\tEnter latitude: ");   
         String latitude = in.readLine();       //enter lat value between [0.0, 100.0]
         System.out.print("\tEnter longitude: ");  //enter long value between [0.0, 100.0]
         String longitude = in.readLine();
         
         String type="Customer";

			String query = String.format("INSERT INTO USERS (name, password, latitude, longitude, type) VALUES ('%s','%s', %s, %s,'%s')", name, password, latitude, longitude, type);

         esql.executeUpdate(query);
         System.out.println ("User successfully created!");
      }catch(Exception e){
         System.err.println (e.getMessage ());
      }
   }//end CreateUser


   /*
    * Check log in credentials for an existing user
    * @return User login or null is the user does not exist
    **/
   public static String LogIn(Retail esql){
      try{
         System.out.print("\tEnter name: ");
         String name = in.readLine();
         System.out.print("\tEnter password: ");
         String password = in.readLine();

         String query = String.format("SELECT * FROM USERS WHERE name = '%s' AND password = '%s'", name, password);
         int userNum = esql.executeQuery(query);
	 String query2 = String.format("Select * from users where name='%s' AND password = '%s'", name, password);
	 List<List<String>> result = esql.executeQueryAndReturnResult(query2);
	 //no longer needSystem.out.print("Current UserID:");
	 //no longer need System.out.println(result.get(0).get(0));
	 userID = Integer.parseInt(result.get(0).get(0));
	// System.out.print("UserID Var:");
	// System.out.println(userID);
	 currUserLat = Double.parseDouble(result.get(0).get(4));
	 //System.out.println(currUserLat);
	 currUserLong = Double.parseDouble(result.get(0).get(3));
	 //System.out.println(currUserLong);
	 //System.out.println(result.get(0).get(5));
	 userType = result.get(0).get(5);
	 //System.out.println(userType.getClass());
	 //System.out.print("userType:");
	 //System.out.println(userType);
	 if (userNum > 0)
		return userType;
         return null;
      }catch(Exception e){
	 System.err.println("User Not Found!");
         System.err.println (e.getMessage ());
         return null;
      }
   }//end

// Rest of the functions definition go in here

   // end

   /**
    * 
    * @return
    */
   public String getdbname() {
      return dbname;
   }

   /**
    * 
    * @return
    */
   public String getdbport() {
      return dbport;
   }

   /**
    * 
    * @return
    */
   public String getUser() {
      return user;
   }

   /**
    * 
    * @return
    */
   public String getPassword() {
      return passwd;
   }

   /**
    * 
    * @return
    */
   public String getUserId() {
      return currUserIDString;
   }

   /**
    * 
    * @return
    */
   public Connection getConnection() {
      return _connection;
   }

   // Rest of the functions definition go in here


   public static void viewStores(Retail esql) {
	String currUserIDString = Integer.toString(userID);
	String query = String.format("select s.storeID, s.name, calculate_distance(u.latitude, u.longitude, s.latitude, s.longitude) as dist from users u, store s where u.userID = '%s' and calculate_distance(u.latitude, u.longitude, s.latitude, s.longitude) < 30", currUserIDString); 
	try {
	int userNum = esql.executeQueryAndPrintResult(query);
	}
	catch (Exception e) {
	System.err.println (e.getMessage());
	}
}
   public static void viewProducts(Retail esql) {
	try {
	String storeID = "";
	System.out.println("Enter Store ID:");
	storeID = in.readLine();
	String query = String.format("select * from Product p where storeID='%s'", storeID);
        int userNum = esql.executeQueryAndPrintResult(query);
	if (userNum == 0) {
	throw new Exception();
}
	}
        catch (Exception e) {
        System.out.println("Store ID does not exist!");
        }
}
   public static void placeOrder(Retail esql) {
	 try {
	 double distance;
	 String storeLat;
	 String storeLong;
	 int numProducts;
         System.out.print("\tEnter Store ID:");
         String storeID = in.readLine();
         System.out.print("\tEnter Product Name:");
         String productName = in.readLine();
	 System.out.print("\tEnter Number of Units:");
         String numUnits = in.readLine();

	 if (Integer.parseInt(numUnits) <= 0) {
		System.out.println("Incorrect Number of Items.");
	 }

         String query = String.format("select latitude, longitude from Store s where s.storeID='%s'", storeID);
	 List<List<String>> result = esql.executeQueryAndReturnResult(query);
	 storeLat = result.get(0).get(0);
	 storeLong = result.get(0).get(1);
	 //System.out.println(currUserLong);
	 //System.out.println(currUserLat);
	 //System.out.println(Double.parseDouble(storeLat));
	 //System.out.println(Double.parseDouble(storeLong));
         distance = calculateDistance(currUserLong, currUserLat, Double.parseDouble(storeLat), Double.parseDouble(storeLong));
	 //System.out.println(distance);
	 //check distance
	 if (distance > 30.0) {
	 System.out.println("You are too far away from this store! Sorry for the inconvenience.");
	 return;
	 }
	 //check product count
	 String query3 = String.format("select numberOfUnits from product where storeID = %s and productName = '%s';", storeID, productName);
         List<List<String>> result2 = esql.executeQueryAndReturnResult(query3);
         numProducts = Integer.parseInt(result2.get(0).get(0));

         //check product name
        String query4 = String.format("select productName from product where productName = '%s';", productName);
	int numRows = esql.executeQuery(query4);
            


	 if (Integer.parseInt(numUnits) > numProducts) {
	 System.out.println("This store does not have enough product of that type! Sorry for the inconvenience.");
	 return;
}

	 else {
	 String query2 = String.format("insert into orders (customerID, storeID, productName, unitsOrdered, orderTime) values (%s,%s,'%s',%s,now())", userID, storeID, productName, numUnits);
	 esql.executeUpdate(query2);
	 String query5 = String.format("update product set numberOfUnits = numberOfUnits - %s where storeID = %s and productName = '%s';", numUnits, storeID, productName);
	 esql.executeUpdate(query5);
	 System.out.println("Order Successfully made!");
	 }
	 }
         catch (Exception e) {
	 System.out.println("Product Name or StoreID does not exist!");
        // System.err.println (e.getMessage());
         }
	 
}

   public static void viewRecentOrders(Retail esql) {
	try {
	String currUserIDString = Integer.toString(userID);
	String query = String.format("select o.storeID, s.name, o.productName, o.unitsOrdered, o.orderTime from Store s, Orders o where o.storeID = s.storeID and o.customerID = '%s' order by o.orderTime desc limit 5", currUserIDString);
	int userNum = esql.executeQueryAndPrintResult(query);
}
        catch (Exception e) {
        System.err.println (e.getMessage());
        }

}
   public static void updateProduct(Retail esql) {
      try{
	 if (userType.contains("customer") || userType.contains("admin")) {
	 System.out.println("Only Managers Can Use This Function! (Nice try though :) )");
	 return;
	 }

	 System.out.print("\tEnter storeID of which you want to update products:");
	 int storeID = Integer.parseInt(in.readLine());
	 boolean ManagerDoesManage = false;
	 String query = String.format("select storeID from store where managerID = %s", userID);
	 List<List<String>> result = esql.executeQueryAndReturnResult(query);
	 for (List<String> list : result) {
		
		for (String item : list) {
			if (Integer.parseInt(item) == storeID) {
				ManagerDoesManage = true;	
			}
		}

	 }

	 if (ManagerDoesManage == false) {
		System.out.println("You do not manage this store or it does not exist. You can only update products of the stores which you manage.");
		return;
	 }

	 System.out.println("Please enter the name of the product which you want to update:");
	 String productName = in.readLine();

         String query3 = String.format("select * from product where productName = '%s' and storeID = %s;", productName, storeID);
         int check1 = esql.executeQuery(query3);
         if (check1 == 0) {
                System.out.println("Product Name Does Not Exist!");
                return;
         }


	 System.out.println("Please enter new number of units and price. (If you do not want to change one of these options, enter the pre-existing value)");
	 System.out.println("New Number of Units:");
	 int numUnits = Integer.parseInt(in.readLine());

         if (numUnits < 0) {
                System.out.println("New Number Of Units Can't Be Less Than 0!");
                return;
         }


	 System.out.println("New Product Price:");
	 float price = Float.parseFloat(in.readLine());
	 
	 if (price <= 0) {
                System.out.println("New Product Price Can't Be 0 Or Less!");
                return;
         }


	 String query2 = String.format("update product set pricePerUnit = %s, numberOfUnits = %s where productName = '%s' and storeId = %s;", price, numUnits, productName, storeID);
	 


	 esql.executeUpdate(query2);

	 //insert into product updates
	 

	 String query4 = String.format("insert into ProductUpdates(managerID, storeID, productName, updatedOn) values (%s,%s,'%s',now())", userID, storeID, productName);
	 esql.executeUpdate(query4);

	 System.out.println("Product Update Successfully made and Recent Product Updates Changed Accordingly!");
	
         //System.out.print("\tEnter product: ");
         //String product = in.readLine();

         //String currUserIDString = Integer.toString(userID);
         //String query = String.format("update product, " , product);

      }catch(Exception e) {
           System.err.println (e.getMessage());
      }
   }
   public static void viewRecentUpdates(Retail esql) {
      try{

	 if (userType.contains("customer") || userType.contains("admin")) {
        	 System.out.println("Only Managers Can Use This Function! (Nice try though :) )");
        	 return;
         }

         String currUserIDString = Integer.toString(userID);
         String query = String.format("select * from ProductUpdates p where p.managerID = %s order by p.updatedOn desc limit 5", userID);
         int userNum = esql.executeQueryAndPrintResult(query);
      }catch(Exception e){
           System.err.println (e.getMessage());
      }
      
   }

   public static void managerViewOrders(Retail esql) {
	try{

         if (userType.contains("customer") || userType.contains("admin")) {
         System.out.println("Only Managers Can Use This Function! (Nice try though :) )");
         return;
	 }


		String query = String.format("select o.orderNumber, u.name, o.storeID, o.productName, o.orderTime from Orders o, Users u, Store s where o.customerID=u.userID and o.storeID=s.storeID and s.managerID = %s;", userID);
		esql.executeQueryAndPrintResult(query);
	}
	catch(Exception e) {
		System.err.println(e.getMessage());
	}

   }

   public static void viewPopularProducts(Retail esql) {
      try{

         if (userType.contains("customer") || userType.contains("admin")) {
                 System.out.println("Only Managers Can Use This Function! (Nice try though :) )");
                 return;
         }


         String query = String.format("select o.productName, sum(o.unitsOrdered) as TotalUnitsOrdered from Orders o, Store s where o.storeID=s.storeID and s.managerID = %s group by o.productName order by sum(o.unitsOrdered) desc limit 5;", userID);
         int userNum = esql.executeQueryAndPrintResult(query);

      }catch(Exception e){
           System.err.println (e.getMessage());
      }

   }


   public static void viewPopularCustomers(Retail esql) {
      try{

         if (userType.contains("customer") || userType.contains("admin")) {
                 System.out.println("Only Managers Can Use This Function! (Nice try though :) )");
                 return;
         }

String query = String.format("select u.name, u.latitude, u.longitude, sum(o.unitsOrdered) as TotalUnitsOrdered from Orders o, Users u, Store s where o.customerID=u.userID and o.storeID=s.storeID and s.managerID=%s group by u.userID order by sum(o.unitsOrdered) desc limit 5;", userID);
int num = esql.executeQueryAndPrintResult(query);
         
      }catch(Exception e){
           System.err.println (e.getMessage());
      }
   }


   public static void placeProductSupplyRequests(Retail esql) {

      try{
         if (userType.contains("customer") || userType.contains("admin")) {
         System.out.println("Only Managers Can Use This Function! (Nice try though :) )");
         return;
         }

         System.out.print("Enter storeID of which you want to make a supply request:");
         int storeID = Integer.parseInt(in.readLine());
         boolean ManagerDoesManage = false;
         String query = String.format("select storeID from store where managerID = %s", userID);
         List<List<String>> result = esql.executeQueryAndReturnResult(query);
         for (List<String> list : result) {

                for (String item : list) {
                        if (Integer.parseInt(item) == storeID) {
                                ManagerDoesManage = true;
                        }
                }

         }

         if (ManagerDoesManage == false) {
                System.out.println("You do not manage this store or it does not exist. You can only update products of the stores which you manage.");
                return;
         }

	//ASK FOR PRODUCT NAME
	 System.out.print("Enter Product Name for Which You Want to Make a Supply Request:");
         String productName = in.readLine();	

	//CHECK IF PRODUCT NAME EXISTS AT SAID STORE

         String query2 = String.format("select * from product where productName = '%s' and storeID = %s;", productName, storeID);
         int check1 = esql.executeQuery(query2);
         if (check1 == 0) {
                System.out.println("Product Name Does Not Exist!");
                return;
         }

	//ASK FOR NUMBER OF UNITS
	
	 System.out.print("Enter Number of Units for the Product Request:");
         int numUnits = Integer.parseInt(in.readLine());

	//NUMBER OF UNITS CANT BE 0 OR LESS
	if (numUnits <= 0){
	System.out.println("Number Of Units Can't Be 0 or Less!");
	return;
	}

	//ASK FOR WAREHOUSE ID
	System.out.print("Enter Warehouse ID:");
        int warehouseID = Integer.parseInt(in.readLine());

	//CHECK THAT WAREHOUSE ID EXISTS
	 if (warehouseID < 1 || warehouseID > 5){
                System.out.println("This Warehouse ID Does Not Exist!");
                return;
         }

//INSERT INTO SUPPLYREQUESTS AND UPDATE PRODUCTS TABLE WITH NEW PRODUCT
         String query4 = String.format("insert into ProductSupplyRequests(managerID, warehouseID, storeID, productName, unitsRequested) values (%s,%s,%s, '%s', %s)", userID, warehouseID, storeID, productName, numUnits);
         esql.executeUpdate(query4);

         String query5 = String.format("update product set numberOfUnits = numberOfUnits+%s where productName = '%s' and storeId = %s;", numUnits, productName, storeID);
	 esql.executeUpdate(query5);

	 System.out.println("Supply Request Successfully Made! Product Supply Requests and Number of Products Updated Accordingly!");

	}catch(Exception e) {

	System.err.println(e.getMessage());
}

}//end Retail
}

