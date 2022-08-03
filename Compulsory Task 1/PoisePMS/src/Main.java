import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class Enter {

	// variables for entering a project

	int num;
	String proj_name;
	int address_id;
	float total_fee;
	float paid_amount;
	String deadline;
	int contractor_id;
	int engineer_id;
	int architect_id;
	int customer_id;

}

class Update {

	int num;
	float paid_amount;

}

class Delete {

	int num;
}

class Finalise {
	int num;
	String complete_date;
}

class Incomplete {
	String finalise;
}

class Due {
	String current_date;
}

class Search {

	int num;
	String proj_name;

}



public class Main {

	public static String getInput(String message, BufferedReader reader) throws IOException {
		System.out.println(message);
		return reader.readLine();
	}
	
	

	public static void main(String[] args) throws IOException {
		
		try {

			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/PoisePMS?useSSL=false",
					"otheruser", "swordfish");

			Statement statement = connection.createStatement();
			ResultSet results;
			int rowsAffected;

			// write out the project (read)
			results = statement.executeQuery(
					"SELECT num, proj_name, address_id, total_fee, paid_amount, deadline, engineer_id, contractor_id, architect_id, customer_id FROM project");

			while (results.next()) {
				System.out.println(results.getInt("num") + ", " + results.getString("proj_name") + ", "
						+ results.getInt("address_id") + ", " + results.getFloat("total_fee") + ", "
						+ results.getFloat("paid_amount") + ", " + results.getDate("deadline") + ", "
						+ results.getInt("engineer_id") + ", " + results.getInt("contractor_id") + ", "
						+ results.getInt("architect_id") + ", " + results.getInt("customer_id"));
			}
			// project address
			results = statement
					.executeQuery("SELECT id, num, street, city, postal, building_type, ERF FROM project_address");

			while (results.next()) {
				System.out.println(
						results.getInt("id") + ", " + results.getInt("num") + ", " + results.getString("street") + ", "
								+ results.getString("city") + ", " + results.getString("postal") + ", "
								+ results.getString("building_type") + ", " + results.getString("ERF"));
			}

			// engineer
			results = statement.executeQuery("SELECT id, eng_name, tel_number, email FROM engineer");

			while (results.next()) {
				System.out.println(results.getInt("id") + ", " + results.getString("eng_name") + ", "
						+ results.getString("tel_number") + ", " + results.getString("email"));
			}

			// contractor
			results = statement.executeQuery("SELECT id, contractor_name, tel_number, email FROM contractor");

			while (results.next()) {
				System.out.println(results.getInt("id") + ", " + results.getString("contractor_name") + ", "
						+ results.getString("tel_number") + ", " + results.getString("email"));
			}

			// architect
			results = statement.executeQuery("SELECT id, architect_name, tel_number, email FROM architect");

			while (results.next()) {
				System.out.println(results.getInt("id") + ", " + results.getString("architect_name") + ", "
						+ results.getString("tel_number") + ", " + results.getString("email"));
			}

			// customer
			results = statement.executeQuery("SELECT id, customer_name, tel_number, email FROM customer");

			while (results.next()) {
				System.out.println(results.getInt("id") + ", " + results.getString("customer_name") + ", "
						+ results.getString("tel_number") + ", " + results.getString("email"));
			}

			// new projects
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

			// choice by a person on the options from the menu
			// Ask the user which option they want to pick
			String choice = getInput(
					"Choose an option: \n" + "1. Enter a project\n" + "2. Update a project\n" + "3. Delete a project\n"
							+ "4. Finalise a project\n" + "5. Find projects that still need to be completed\n"
							+ "6. Find projects past due date\n" + "7. Find and select a project\n" + "0. Exit",
					reader);

			do {
				// if users choice is 1 do the following:
				if (choice.equals("1")) {
					// create a new project object to enter new values of the project
					Enter enterProject = new Enter();
					String numStr = getInput("Enter project number : ", reader);
					// cast string to integer
					enterProject.num = Integer.parseInt(numStr);
					enterProject.proj_name = getInput("Enter project name : ", reader);
					String addStr = getInput("Enter the address ID : ", reader);
					enterProject.address_id = Integer.parseInt(addStr);
					String totalStr = getInput("Enter total project fee : ", reader);
					// cast string to float
					enterProject.total_fee = Float.parseFloat(totalStr);
					String paidStr = getInput("Enter the paid amount : ", reader);
					enterProject.paid_amount = Float.parseFloat(paidStr);
					enterProject.deadline = getInput("Enter the deadline for the project : ", reader);
					String engIdStr = getInput("Enter the engineer's ID : ", reader);
					enterProject.engineer_id = Integer.parseInt(engIdStr);
					String conIdStr = getInput("Enter the constructor's ID : ", reader);
					enterProject.contractor_id = Integer.parseInt(conIdStr);
					String archIdStr = getInput("Enter the architect ID : ", reader);
					enterProject.architect_id = Integer.parseInt(archIdStr);
					String custIdStr = getInput("Enter the customer's ID : ", reader);
					enterProject.customer_id = Integer.parseInt(custIdStr);

					// Add a project to database:
					// I used the prepare statement action to firstly update the projects and also
					// used the insert action to the the projects to table
					PreparedStatement insertPreparedStatement = connection
							.prepareStatement("INSERT INTO project VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
					insertPreparedStatement.setInt(1, enterProject.num);
					insertPreparedStatement.setString(2, enterProject.proj_name);
					insertPreparedStatement.setInt(3, enterProject.address_id);
					insertPreparedStatement.setFloat(4, enterProject.total_fee);
					insertPreparedStatement.setFloat(5, enterProject.paid_amount);
					insertPreparedStatement.setString(6, enterProject.deadline);
					insertPreparedStatement.setInt(7, enterProject.engineer_id);
					insertPreparedStatement.setInt(8, enterProject.contractor_id);
					insertPreparedStatement.setInt(9, enterProject.architect_id);
					insertPreparedStatement.setInt(10, enterProject.customer_id);
					rowsAffected = insertPreparedStatement.executeUpdate();
					// print out rows affected
					System.out.println("Query complete, " + rowsAffected + "rows added.");
					// print table
					printAllFromTable(insertPreparedStatement);

				} else if (choice.equals("2")) {

					// create a new project object to enter new values of the project
					Update updateProject = new Update();
					String idStr = getInput(
							"Enter the project number of the project you would like to update the paid amount: ",
							reader);
					updateProject.num = Integer.parseInt(idStr);
					String paidStr = getInput("Enter the updated paid amount: ", reader);
					updateProject.paid_amount = Float.parseFloat(paidStr);

					// Update the project of choice with the new paid amount
					PreparedStatement insertPreparedStatement = connection
							.prepareStatement("UPDATE project SET paid_amount = (?) WHERE num = (?)");
					insertPreparedStatement.setFloat(1, updateProject.paid_amount);
					insertPreparedStatement.setInt(2, updateProject.num);
					rowsAffected = insertPreparedStatement.executeUpdate();
					// print out rows affected
					System.out.println("Query complete, " + rowsAffected + "rows updated.");
					// print table
					printAllFromTable(statement);

				} else if (choice.equals("3")) {
					// create a new book object to enter new values in the book
					Delete deleteProject = new Delete();
					String numStr = getInput("Enter the number of the project you want to delete: ", reader);
					// cast string to integer
					deleteProject.num = Integer.parseInt(numStr);
					
					//select the id of all the people 
					PreparedStatement insertPreparedStatement = connection.prepareStatement("SELECT address_id, engineer_id, contractor_id, architect_id, customer_id FROM project WHERE num = (?)");
					insertPreparedStatement.setInt(1, deleteProject.num);
					results = insertPreparedStatement.executeQuery();
					 results.next();
					
					//delete specified address
					PreparedStatement deletePreparedStatement = connection.prepareStatement("DELETE FROM project_address WHERE id = (?)");
					deletePreparedStatement.setInt(1, results.getInt("address_id"));
					rowsAffected = deletePreparedStatement.executeUpdate();
					System.out.println("Query complete, " + rowsAffected + "rows removed.");
					
					//delete specified engineer
					deletePreparedStatement = connection.prepareStatement("DELETE FROM engineer WHERE id = (?)");
					deletePreparedStatement.setInt(1, results.getInt("engineer_id"));
					rowsAffected = deletePreparedStatement.executeUpdate();
					System.out.println("Query complete, " + rowsAffected + "rows removed.");
					
					//delete specified contractor
					deletePreparedStatement = connection.prepareStatement("DELETE FROM contractor WHERE id = (?)");
					deletePreparedStatement.setInt(1, results.getInt("contractor_id"));
					rowsAffected = deletePreparedStatement.executeUpdate();
					System.out.println("Query complete, " + rowsAffected + "rows removed.");
					
					//delete specified architect
					deletePreparedStatement = connection.prepareStatement("DELETE FROM architect WHERE id = (?)");
					deletePreparedStatement.setInt(1, results.getInt("architect_id"));
					rowsAffected = deletePreparedStatement.executeUpdate();
					System.out.println("Query complete, " + rowsAffected + "rows removed.");
					
					//delete specified engineer
					deletePreparedStatement = connection.prepareStatement("DELETE FROM customer WHERE id = (?)");
					deletePreparedStatement.setInt(1, results.getInt("customer_id"));
					rowsAffected = deletePreparedStatement.executeUpdate();
					System.out.println("Query complete, " + rowsAffected + "rows removed.");
					
					//delete project, after deleting associated people
					insertPreparedStatement = connection.prepareStatement("DELETE FROM project WHERE num = (?)");
					insertPreparedStatement.setInt(1, deleteProject.num);
					rowsAffected = insertPreparedStatement.executeUpdate();
					System.out.println("Query complete, " + rowsAffected + "rows removed.");
					
					
					// print table
					printAllFromTable(statement);
				}

				else if (choice.equals("4")) {

					// Query to alter the table

					rowsAffected = statement.executeUpdate("ALTER TABLE project ADD COLUMN finalised VARCHAR(30)");
					rowsAffected = statement.executeUpdate("ALTER TABLE project ADD COLUMN complete_date VARCHAR(30)");

					Finalise finaliseProject = new Finalise();

					// enter id of project to finalise
					String numStr = getInput("Enter the number of the project you want to finalised : ", reader);
					finaliseProject.num = Integer.parseInt(numStr);
					// enter date of finalisation
					finaliseProject.complete_date = getInput("Enter the project completion date : ", reader);

					PreparedStatement insertPreparedStatement = connection.prepareStatement(
							"UPDATE project SET finalised = 'finalised', complete_date = (?) WHERE num = (?)");
					insertPreparedStatement.setString(1, finaliseProject.complete_date);
					insertPreparedStatement.setInt(2, finaliseProject.num);
					rowsAffected = insertPreparedStatement.executeUpdate();
					// print out rows affected
					System.out.println("Query complete, " + rowsAffected + "rows updated.");
					// print table with the finalised
					finalisePrintAllFromTable(statement);

				} else if (choice.equals("5")) {

					//print out projects not finalised
					results = statement.executeQuery( 
							"SELECT num, proj_name, address_id, total_fee, paid_amount, deadline, engineer_id, contractor_id, architect_id, customer_id, finalised, complete_date FROM project WHERE finalised IS NULL");

					while (results.next()) {
						System.out.println(results.getInt("num") + ", " + results.getString("proj_name") + ", "
								+ results.getInt("address_id") + ", " + results.getFloat("total_fee") + ", "
								+ results.getFloat("paid_amount") + ", " + results.getDate("deadline") + ", "
								+ results.getInt("engineer_id") + ", " + results.getInt("contractor_id") + ", "
								+ results.getInt("architect_id") + ", " + results.getInt("customer_id") + ", "
								+ results.getString("finalised") + ", " + results.getString("complete_date"));
					}
				} else if (choice.equals("6")) {
					// projects less than current date
					// enter current date

					Due dueProject = new Due();

					dueProject.current_date = getInput("Enter the current date : ", reader);

					PreparedStatement insertPreparedStatement = connection.prepareStatement(
							"SELECT num, proj_name, address_id, total_fee, paid_amount, deadline FROM project WHERE deadline < (?)");
					insertPreparedStatement.setString(1, dueProject.current_date);
					results = insertPreparedStatement.executeQuery();

					while (results.next()) {
						System.out.println(results.getInt("num") + ", " + results.getString("proj_name") + ", "
								+ results.getInt("address_id") + ", " + results.getFloat("total_fee") + ", "
								+ results.getFloat("paid_amount") + ", " + results.getDate("deadline"));
					}

				}

				else if (choice.equals("7")) {

					Search searchProject = new Search();
					// ask user how they would like to search for the project
					String option = getInput("How would you like to search for the project: \n" + "1. Project number\n"
							+ "2. Project name\n", reader);

					if (option.equals("1")) {
						// if user chooses project number select that matching project
						String numStr = getInput("Enter the number of the project you want to search : ", reader);
						searchProject.num = Integer.parseInt(numStr);
						PreparedStatement insertPreparedStatement = connection.prepareStatement(
								"SELECT num, proj_name, address_id, total_fee, paid_amount, deadline, engineer_id, contractor_id, architect_id, customer_id,  FROM project WHERE num = (?) AND finalised IS NULL");
						insertPreparedStatement.setInt(1, searchProject.num);
						results = insertPreparedStatement.executeQuery();
						while (results.next()) {
							System.out.println(results.getInt("num") + ", " + results.getString("proj_name") + ", "
									+ results.getInt("address_id") + ", " + results.getFloat("total_fee") + ", "
									+ results.getFloat("paid_amount") + ", " + results.getDate("deadline") + ", "
									+ results.getInt("engineer_id") + ", " + results.getInt("contractor_id") + ", "
									+ results.getInt("architect_id") + ", " + results.getInt("customer_id"));
						}
					} else if (option.equals("2")) {
						// if user chooses project name select that matching project
						searchProject.proj_name = getInput("Enter the project name you want to search : ", reader);
						PreparedStatement insertPreparedStatement = connection.prepareStatement(
								"SELECT num, proj_name, address_id, total_fee, paid_amount, deadline, engineer_id, contractor_id, architect_id, customer_id, finalised, complete_date FROM project WHERE proj_name = (?)");
						insertPreparedStatement.setString(1, searchProject.proj_name);
						results = insertPreparedStatement.executeQuery();
						while (results.next()) {
							System.out.println(results.getInt("num") + ", " + results.getString("proj_name") + ", "
									+ results.getInt("address_id") + ", " + results.getFloat("total_fee") + ", "
									+ results.getFloat("paid_amount") + ", " + results.getDate("deadline") + ", "
									+ results.getInt("engineer_id") + ", " + results.getInt("contractor_id") + ", "
									+ results.getInt("architect_id") + ", " + results.getInt("customer_id"));
						}
					}

				}

				choice = getInput(
						"Choose an option: \n" + "1. Enter a project\n" + "2. Update a project\n"
								+ "3. Delete a project\n" + "4. Finalise a project\n"
								+ "5. Find projects that still need to be completed\n"
								+ "6. Find projects past due date\n" + "7. Find and select a project\n" + "0. Exit",
						reader);

			} while (!choice.equals("0"));

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public static void printAllFromTable(Statement statement) throws SQLException {

		ResultSet results = statement.executeQuery(
				"SELECT num, proj_name, address_id, total_fee, paid_amount, deadline, engineer_id, contractor_id, architect_id, customer_id FROM project");

		while (results.next()) {
			System.out.println(results.getInt("num") + ", " + results.getString("proj_name") + ", "
					+ results.getInt("address_id") + ", " + results.getFloat("total_fee") + ", "
					+ results.getFloat("paid_amount") + ", " + results.getDate("deadline") + ", "
					+ results.getInt("engineer_id") + ", " + results.getInt("contractor_id") + ", "
					+ results.getInt("architect_id") + ", " + results.getInt("customer_id"));
		}

	}

	public static void finalisePrintAllFromTable(Statement statement) throws SQLException {

		ResultSet results = statement.executeQuery(
				"SELECT num, proj_name, address_id, total_fee, paid_amount, deadline, engineer_id, contractor_id, architect_id, customer_id, finalised, complete_date FROM project");

		while (results.next()) {
			System.out.println(results.getInt("num") + ", " + results.getString("proj_name") + ", "
					+ results.getInt("address_id") + ", " + results.getFloat("total_fee") + ", "
					+ results.getFloat("paid_amount") + ", " + results.getDate("deadline") + ", "
					+ results.getInt("engineer_id") + ", " + results.getInt("contractor_id") + ", "
					+ results.getInt("architect_id") + ", " + results.getInt("customer_id") + ", "
					+ results.getString("finalised") + ", " + results.getString("complete_date"));
		}

	}
}
