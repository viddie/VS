package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class Main {
	
	public static final String API_KEY = "722920868a0a0266c859a174da690bc1";
	public static final String FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast?id={CITY_ID}&APPID={API_KEY}";
	public static final String FIND_URL = "http://api.openweathermap.org/data/2.5/find?type=accurate&q={CITY_NAME}&APPID={API_KEY}";
	
	public static ArrayList<String> timesInOrder = new ArrayList<String>();
	
	static {
		timesInOrder.add("06:00:00");
		timesInOrder.add("12:00:00");
		timesInOrder.add("18:00:00");
		timesInOrder.add("0:00:00");
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int selectedCityID = Integer.MIN_VALUE;
		boolean done = false;
		
		while(done == false){
			done = true;
			
			System.out.print("Please enter a city name: ");
			String cityName = in.nextLine();
			
			if(cityName.isEmpty()){
				System.out.println("Exiting...");
				in.close();
				System.exit(0);
			}
			
			/*
			 * Find CityID by citie's name
			 */
			
			String callURL = FIND_URL.replace("{API_KEY}", API_KEY);
			callURL = callURL.replace("{CITY_NAME}", cityName);
			
			JSONObject parentObj = new JSONObject(readHTTPWebpage(callURL));
			
			
			
			int count = parentObj.getInt("count");
			
			if(count > 1){
				JSONArray arr = parentObj.getJSONArray("list");
				
				String printString = "\nSelect one of the following cities:\n";
				
				for(int i = 0; i < arr.length(); i++){
					JSONObject cityObj = arr.getJSONObject(i);
					String cityNameTemp = cityObj.getString("name");
					int cityIDTemp = cityObj.getInt("id");
					
					JSONObject coord = cityObj.getJSONObject("coord");
					double lonTemp = coord.getDouble("lon");
					double latTemp = coord.getDouble("lat");
					
					JSONObject sys = cityObj.getJSONObject("sys");
					String cityCountryTemp = sys.getString("country");
					
					printString += "\n"+i+": "+cityNameTemp+" ("+cityCountryTemp+") - Longitude: "+lonTemp+", Latitude: "+latTemp+", CityID: "+cityIDTemp;
				}
				
				int selectedIndex = Integer.MIN_VALUE;
				
				while (true){
					System.out.println(printString+"\n");
					System.out.print("Selected Index: ");
					String lineIn = in.nextLine();
					
					if(lineIn.isEmpty()){
						in.close();
						System.exit(0);
					}
					
					try{
						int index = Integer.parseInt(lineIn);
						if(index < 0 || index > arr.length()-1){
							throw new NumberFormatException();
						}
						selectedIndex = index;
						break;
					}catch(NumberFormatException e){
						System.out.println("\nThat was not a valid Integer! (Press <Enter> to exit)\n");
					}
				}
				
				selectedCityID = arr.getJSONObject(selectedIndex).getInt("id");
				
			}else if(count == 1){
				JSONArray arr = parentObj.getJSONArray("list");
				
				JSONObject cityObj = arr.getJSONObject(0);
				selectedCityID = cityObj.getInt("id");
				
			}else{
				System.out.println("\nThe city you entered was not found...\n");
				done = false;
			}
		}
		
		
		System.out.println();
		
		
		/*
		 * Forecast parsing
		 */
		String callURL = FORECAST_URL.replace("{API_KEY}", API_KEY);
		callURL = callURL.replace("{CITY_ID}", ""+selectedCityID);
		
		JSONObject parentObj = new JSONObject(readHTTPWebpage(callURL));
		
		
		JSONArray arr = parentObj.getJSONArray("list");
		
		//i < 9, because we only want the next days values, as the values are seperated in 3 hour intervals (24/3 = 8)
		for(int i = 0; i < 9; i++){
			JSONObject timePointObj = arr.getJSONObject(i);
			String dt_txt = timePointObj.getString("dt_txt");
			
			if(isWantedTime(dt_txt)){
				JSONObject mainObj = timePointObj.getJSONObject("main");
				float minTemp = mainObj.getFloat("temp_min");
				float maxTemp = mainObj.getFloat("temp_max");
				
				minTemp -= 273.15;
				maxTemp -= 273.15;
				
				System.out.println("-------"+dt_txt+"-------");
				System.out.println(String.format("Max. Temperatur: %.2f", maxTemp));
				System.out.println(String.format("Min. Temperatur: %.2f", minTemp));
				System.out.println("---------------------------------\n");
			}
		}
		
		in.close();
//		System.out.println(parentObj.toString());
	}
	
	public static boolean isWantedTime(String dt_txt){
		for(String wantedTime : timesInOrder){
			if(dt_txt.contains(wantedTime)){
				return true;
			}
		}
		
		return false;
	}
	
	public static String readHTTPWebpage(String callURL){
		URL url = null;
		try {
			url = new URL(callURL);
			
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
			con.setRequestMethod("GET");
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
			
			return content.toString();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
