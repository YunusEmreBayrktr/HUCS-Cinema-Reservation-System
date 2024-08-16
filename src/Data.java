import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

public class Data {
	
	public static HashMap<String,User> users;
	public static HashMap<String,Film> films;
	public static HashMap<String,Hall> halls;
	public static HashMap<String,ArrayList<Seat>> seats;
	public static int maxError;
	public static String title;
	public static int discountPercentage;
	public static int blockTime;
	
	public Data() {
		
		try {
			Data.users = getUser();
			Data.films = getFilm();
			Data.halls = getHall();
			Data.seats = getSeat();
			readProperties();
		}
		catch(IOException e) {
			System.out.println("IOException");
		}
	}
	
	
	public void readProperties() throws IOException {

		FileReader fr = new FileReader("assets/data/properties.dat");
		BufferedReader br = new BufferedReader(fr);
		
		String line;
		while((line = br.readLine()) != null) {
			if (line.charAt(0) != '#'){
				String[] temp = line.split("=");

				switch (temp[0]) {
					case "maximum-error-without-getting-blocked":
						Data.maxError = Integer.parseInt(temp[1]);
						break;
					case "title":
						Data.title = temp[1];
						break;
					case "discount-percentage":
						Data.discountPercentage = Integer.parseInt(temp[1]);
						break;
					case "block-time":
						Data.blockTime = Integer.parseInt(temp[1]);
						break;
				}
			}
		}
		br.close();
	}


	public ArrayList<String> readData() throws IOException {

		ArrayList<String> data = new ArrayList<>();

		FileReader fr = new FileReader("assets/data/backup.dat");
		BufferedReader br = new BufferedReader(fr);

		String line;
		while((line = br.readLine()) != null) {
			data.add(line);
		}
		br.close();
		return data;
	}
	
	
	public HashMap<String,User> getUser() throws IOException {
		
		ArrayList<String> data = readData();
		HashMap<String,User> users = new HashMap<>();
		
		for(String i : data) {
			String[] temp = i.split("\t");
			
			if(temp[0].equals("user")) {
				users.put(temp[1], new User(temp[1],temp[2],temp[3],temp[4]) );
			}
		}
		if (users.isEmpty()){
			users.put("admin",new User("admin",hashPassword("password"),"true","true"));
		}
		return users;
	}
	
	
	public HashMap<String,Film> getFilm() throws IOException {
		
		ArrayList<String> data = readData();
		HashMap<String,Film> films = new HashMap<>();
		
		for(String i : data) {
			String[] temp = i.split("\t");
			
			if(temp[0].equals("film")) {
				films.put(temp[1], new Film(temp[1],temp[2],temp[3]) );
			}
		}
		return films;
	}
	
	
	public HashMap<String,Hall> getHall() throws IOException {
		
		ArrayList<String> data = readData();
		HashMap<String,Hall> halls = new HashMap<>();
		
		for(String i : data) {
			String[] temp = i.split("\t");
			
			if(temp[0].equals("hall")) {
				halls.put(temp[2], new Hall(temp[2],temp[1],temp[3],temp[4],temp[5]) );
			}
		}
		return halls;
	}
	
	
	public HashMap<String,ArrayList<Seat>> getSeat() throws IOException {

		ArrayList<String> data = readData();
		HashMap<String,ArrayList<Seat>> seats = new HashMap<>();

		for(String i : data) {
			String[] temp = i.split("\t");

			if(temp[0].equals("seat")) {
				int row = Integer.parseInt(temp[3]);
				int column = Integer.parseInt(temp[4]);
				int price = Integer.parseInt(temp[6]);
				if(seats.containsKey(temp[2])){
					seats.get(temp[2]).add(new Seat(temp[1],temp[2],row,column,temp[5],price));
				}
				else{
					seats.put(temp[2], new ArrayList<Seat>());
					seats.get(temp[2]).add(new Seat(temp[1],temp[2],row,column,temp[5],price));
				}
			}
		}

		return seats;
	}
	

	public static String hashPassword (String password ) {
		
		byte[] bytesOfPassword = password.getBytes(StandardCharsets.UTF_8);
		byte[] md5Digest = new byte[0];
		
		try {
			md5Digest = MessageDigest.getInstance("MD5").digest(bytesOfPassword);
		}
		catch(NoSuchAlgorithmException e) {
			return null;
		}
		return Base64.getEncoder().encodeToString(md5Digest);
		
	}


	public static void saveBakupData()  {
		try {

			File fl = new File("assets/data/backup.dat");
			FileWriter writer = new FileWriter(fl);

			for(User user : Data.users.values()) {
				writer.write(user.toString());
			}

			for(Film film : Data.films.values()) {
				writer.write(film.toString());
			}

			for(Hall hall : Data.halls.values()) {
				writer.write(hall.toString());
			}
			for(String hall : Data.halls.keySet()){
				for(Seat seat : Data.seats.get(hall)){
					writer.write(seat.toString());
				}
			}

			writer.close();
		}
		catch (Exception e){
		}
	}
}
