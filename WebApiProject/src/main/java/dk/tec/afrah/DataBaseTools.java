package dk.tec.afrah;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataBaseTools {
	
private String connectionStr="jdbc:sqlserver://localhost;databaseName=ProjectWebApi; encrypy =true;trustServerCertificate=true;";
	
	private Connection conn;
	private Statement stmt;
	
	
	private void connect() throws SQLException {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(connectionStr, "sa","1234");
			stmt = conn.createStatement();
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		
	}
	
	public Person getPersonById(int id) 
	{
		try {
			connect();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String selectStr= "Select * from Person where id = " + id;
		Person person =null;
		try {
			ResultSet result= stmt.executeQuery(selectStr);
			
			if(result.next())
			{
				person = new Person();
				person.id= result.getInt("id");
				person.navn=result.getString("navn");
				person.addresse=result.getString("addresse");
				person.hairFarve=result.getInt("hairFarve");
				person.favorit=result.getBoolean("favorit");
				person.tlf=result.getString("tlf");
				person.programSprog=result.getString("programSprog");
				//result.getRowId("Id");
			}
			conn.close();
			
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		return person;
	}
	public List<Person> GetAllPersons() 
	{
			
			List<Person> persons = new ArrayList<>();
			
			//Establishing connection 
			try {
				connect();
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			ResultSet result;
			String selectStr = String.format("Select * from Person" );
			
			try 
			{
				result = stmt.executeQuery(selectStr);
				
				while(result.next()) {
				
					Person ListPerson = new Person();
					ListPerson.setId(result.getInt("id"));
					ListPerson.setNavn(result.getString("navn"));
					ListPerson.setAddresse(result.getString("addresse"));
					ListPerson.setHairFarve(result.getInt("hairFarve"));
					ListPerson.setFavorit(result.getBoolean("favorit"));
					ListPerson.setTlf(result.getString("tlf"));
					ListPerson.setProgramSprog(result.getString("programSprog"));
					
					persons.add(ListPerson);
					
					
				}
				//elever.add(ListElev);
			} catch (SQLException e) 
			{
				e.printStackTrace();
			}
			
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			return persons;
		  }
	
	public void insertPerson(Person person) 
	{
	
			
	try {
		connect();
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
	int favorit = person.favorit? 1: 0;
	//String selectStr = "Insert into Person values("+person.id+", '"+person.navn+"', '"+person.addresse+"','"+person.hairFarve+"','"+favorit+"','"+person.tlf+\"','"+person.programSprog+"')";
	String selectStr ="Insert into Person (navn,addresse,hairFarve, favorit, tlf, programSprog) values(?,?,?,?,?,?)";
	try 
	{
		PreparedStatement pstmt= conn.prepareStatement(selectStr);

		pstmt.setString(1, person.getNavn());
		pstmt.setString(2, person.getAddresse());
		pstmt.setInt(3, person.getHairFarve());
		pstmt.setInt(4, favorit);
		pstmt.setString(5, person.getTlf());
		pstmt.setString(6, person.getProgramSprog());
	
		 pstmt.executeUpdate();
	
		 System.out.print("inserted successfully");
		
		
					
	} catch (SQLException e) 
	{
		System.out.println("Error:"+ e.getMessage());
	}
	
	
		
			
	}
	
	public void updatePerson(Person person) 
	{
		try {
			connect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(person);
		
	
		
		
		String sql= "Update Person set navn = ?, addresse= ?, hairFarve=?, favorit=?, tlf=?,programSprog=?  WHERE id = ?" ;
		
		try {
			int favorit = person.favorit?1:0;
			PreparedStatement stmt= conn.prepareStatement(sql);
			stmt.setInt(7, person.getId());
			stmt.setString(1,person.getNavn());
			stmt.setString(2,person.getAddresse());
			stmt.setInt(3,person.getHairFarve());
			stmt.setInt(4,favorit);
			stmt.setString(5, person.tlf);
			stmt.setString(6, person.programSprog);
			
			int result= stmt.executeUpdate();
			System.out.println("Updated rows are"+ result);
			conn.close();}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
	}
	
	public String deletedPerson(int personid) 
	{
		try {
			connect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(personid);
		
		
		String sql= "Delete FROM Person WHERE id = ?";
		PreparedStatement stmt= null;
		
		try {
			stmt= conn.prepareStatement(sql);			
			stmt.setInt(1,personid);
			stmt.executeUpdate();
						}
		catch(SQLException ex)
		{
			System.out.println("Error cant delete with id: " + personid+ ex.getMessage());
			ex.printStackTrace();
		}
		return "Person ID: " + personid + "is deleted successfully!";
	}
	


	

}
