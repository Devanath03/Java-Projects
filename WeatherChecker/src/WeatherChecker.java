
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class WeatherChecker {

    private static final String API_KEY = "da7ab6e8dc966251e8f8380dfe63d249"; //this key can be used for particular period
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather?q=CITY_NAME,COUNTRY_CODE&APPID=" + API_KEY;

    public static void main(String[] args) {
    	Scanner p = new Scanner(System.in);
        String cityName = p.next();
        String countryCode = p.next();
        String url = API_URL.replace("CITY_NAME", cityName).replace("COUNTRY_CODE", countryCode);
        double temp=0,humidity=0;
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            int responseCode = con.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject json = new JSONObject(response.toString());
                JSONObject main = json.getJSONObject("main");
                temp = main.getDouble("temp") - 273.15; // convert from Kelvin to Celsius
                humidity = main.getDouble("humidity");

                System.out.printf("Temperature: %.2f°C\n",temp);
                System.out.println("Humidity: " + humidity + "%");
            } else {
                System.out.println("Error: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    //ADDING RESULT TO DATABASE
        try {
        	 Class.forName("com.mysql.cj.jdbc.Driver");
        	 //keela iruka link ahh first uhh open perspective poyi database management kku poyi new database creat panni athula mysql oda password podu
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/weather", "root", "Deva@2002");
            //Query kudukura keela iruka line la accounts nu irukarathu tha table name i.e weather schema oda accounts ngara table
            String query = "INSERT INTO accounts values('" + cityName + "','" + countryCode + "','" + temp + "','" +
                humidity + "')";
            Statement sta = connection.createStatement();
            sta.executeUpdate(query);
            connection.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        
        
        //FOR SEEING VALUES ADDED IN TABLE
        
        System.out.println("Data updated in database");
        try {
 		   Class.forName("com.mysql.cj.jdbc.Driver");
 		   Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/weather","root","Deva@2002");
 		   Statement stmt=con.createStatement();
 		   ResultSet rs=stmt.executeQuery("Select * from accounts");
 	   while(rs.next())
 	   {
 		   System.out.println(rs.getString(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+" "+rs.getString(4));
 	   }
 	   }
 	   catch(Exception e)
 	   {
 		   System.out.println(e.toString());
 	   }
        
    }
}
