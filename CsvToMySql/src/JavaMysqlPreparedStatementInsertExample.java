import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * A java example to get csv file from desktop and store into mysql database
 * Created by	: Adzam Daim
 * Date Create	: 1 June 2016
 * Last Updated	: 1 June 2016
 */
public class JavaMysqlPreparedStatementInsertExample {

	public static void main(String[] args) {

		String csvFile = System.getProperty("user.home")+ "/desktop/customers.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		int counter = 0;

		try {
			// create a mysql database connection
			String myDriver = "com.mysql.jdbc.Driver";
			String myUrl = "jdbc:mysql://localhost:3306/world?useSSL=false";
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, "root", "root");

			br = new BufferedReader(new FileReader(csvFile));
			String[] customer = null;
			while ((line = br.readLine()) != null) {

				// use comma as separator
				customer = line.split(cvsSplitBy);
				counter++;
				if (counter > 1) {
					// the mysql insert statement
					String query = " INSERT INTO customer (name, email, flag, create_date, last_updated)"
							+ " values (?, ?, 'NEW', SYSDATE(),SYSDATE())";

					// create the mysql insert preparedstatement
					PreparedStatement preparedStmt = conn.prepareStatement(query);
					preparedStmt.setString(1, customer[0]);
					preparedStmt.setString(2, customer[1]);

					// execute the preparedstatement
					preparedStmt.execute();
				}
			}

			conn.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}catch (Exception e) {
			System.out.println("Got an exception!");
			System.out.println(e.getMessage());
		}finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
				}
			}
		}

		System.out.println("Done");
	}
}