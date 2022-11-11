package dk.tec.afrah;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalyzeRequest {

	
		int personId;
		UrlMatchRequest matchLevel;
		
		public UrlMatchRequest getMatch() 
		{ 
			return matchLevel;
		}
		
		public int getId()
		{
			return personId;
		}
		
		public AnalyzeRequest(String pathInfo) 
		{
				
			Matcher matcher = Pattern.compile("/Person/([0-9]+)").matcher(pathInfo);
			//System.out.print("Hejmed dig" +pathInfo);
			if(matcher.find())
			{
				System.out.print("working");
				matchLevel= UrlMatchRequest.PersonId;
				personId = Integer.parseInt(matcher.group(1));
				
			}
			
			else
			{
				matcher = Pattern.compile("/Person").matcher(pathInfo);
				if(matcher.find())
				{
					matchLevel = UrlMatchRequest.Person;
				}
				else 
				{
					matchLevel= UrlMatchRequest.NoMatch;
				}
			}
		}


}
