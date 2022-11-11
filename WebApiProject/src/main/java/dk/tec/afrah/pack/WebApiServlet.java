package dk.tec.afrah.pack;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import dk.tec.afrah.AnalyzeRequest;

import dk.tec.afrah.DataBaseTools;

import dk.tec.afrah.Person;
import dk.tec.afrah.UrlMatchRequest;

/**
 * Servlet implementation class WebApiServlet
 */
@WebServlet("/WebApiServlet")
public class WebApiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WebApiServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
   @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
   {
			System.out.print("Not working");
			
		BufferedReader in = request.getReader();
		String json=in.readLine();
		System.out.print("json");
		
		ObjectMapper mapper = new ObjectMapper();
		DataBaseTools db = new DataBaseTools();
		
		Person newPerson = mapper.readValue(json, Person.class); //.class fordi det er type af en class
		db.insertPerson(newPerson);
		System.out.println(newPerson.navn);
		PrintWriter out= response.getWriter();
		out.println("New person is inserted");
	}
   
   
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		AnalyzeRequest analyze = new AnalyzeRequest(request.getPathInfo());
		DataBaseTools db = new DataBaseTools();
		
		ObjectMapper mapper = new ObjectMapper();
		
		switch(analyze.getMatch())
		{
			case PersonId:
			int personId= analyze.getId();
			Person person= db.getPersonById(personId);
			String json= mapper.writeValueAsString(person);
			out.print(json);
			
			
			break;
			
			case Person:
				List<Person> person_list = db.GetAllPersons();
				//out.println("Match på Elev");
				out.println(mapper.writeValueAsString(person_list));
				//out.println("Match på Person");
				break;
			
			
			case NoMatch:
			out.print("NoMatch!!!!!  " );
			break;
		}
	}
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		DataBaseTools db= new DataBaseTools();
		PrintWriter out= response.getWriter();
		out.println("Update is calling");
		
		BufferedReader reader = request.getReader();
		String jsonStr = reader.readLine();
		//System.out.println(jsonStr);
		
		Person p = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			out.println(p);
			p=mapper.readValue(jsonStr,Person.class );
		}
		catch(Exception ex) {
			out.println(p);
			ex.printStackTrace();
			return;			
		}
		if(p!=null) {
			out.println(p);
			db.updatePerson(p);
			out.println("Updated");
		}
	}
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		DataBaseTools db= new DataBaseTools();
		PrintWriter out= response.getWriter();
		out.println("Delete called");
		
		AnalyzeRequest analyse = new AnalyzeRequest(request.getPathInfo());
		if(analyse.getMatch()== UrlMatchRequest.PersonId) {
			int id= analyse.getId();
			db.deletedPerson(id);
			out.println(id);
		}
		else
			out.println("Invalid Path/URL");
			response.setStatus(400);
			
		}
}

	


