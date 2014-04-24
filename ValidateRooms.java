/*
 * Code for validating rooms.txt file
 * 
 * Checks for correct number of fields per line (pipe count for rooms file should be 6 per line)
 * Checks for empty or white space values
 * Checks that Building values are alphanumeric and no more than 10 char
 * Checks that RoomNumber values are alphanumeric and no more than 5 char (Do we have an assumption of the maximum character length for RoomNumber?)
 * Checks that Campus values are either 'North','South','East', or 'West' (ignoreCase)
 * Checks that MaintenanceDay values are either 'Monday','Tuesday','Wednesday','Thursday', or 'Friday' (ingnoreCase)
 * Checks that Maintenance StartTime is a valid integer and in range of 1-24 (Assumption start time will always be on the hour in military time)
 * Checks that Maintenance Duration is a valid integer and in range of 1-4 (Assumption duration will always be a whole number.  Do we have an assumption of the maximum length of duration? I set it to 5 hours for now)
 * Checks that Capacity is a valid integer (do we have an assumption yet for maximum Capacity?)
 * Keeps track of errorCount.  Prints errors by line and total errorCount
 * Finally prints all data from input file in column format for review
 */


//
package validation;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


//hi!
public class ValidateRooms {
	
	static int errorCount = 0;													//int to track errorCount
	
	public static void main(String[] args) throws IOException {
		
		try {
            File f = new File("C:/rooms.txt");       	 						//locates file
            Scanner s1 = new Scanner(f);										//creates scanner object
            List<Room> room = new ArrayList<Room>();							//creates array list of room objects
            int count1 = 1;
            s1.nextLine();														//tell scanner to skip first line in input file containing column headings
            while(s1.hasNextLine()){											//start while loop to scan all input file until no more lines
                String line = s1.nextLine();									//put each line of data into a string
                checkPipeCountPerLine(line, count1);							//call method to check column count per line (pipe count) to validate each line has correct number of fields
                String[] details = line.split("\\|");							//split each string of data between pipes | in the line and put them into an array
                String roomNum = details[0];									//put each string from the array into a string
                String building = details[1];
                String capacity = details[2];
                String campus = details[3];
                String maintenanceDay = details[4];
                String startTime = details[5];
                String duration = details[6];
                
                isNotEmpty(roomNum, building, capacity, campus, maintenanceDay, startTime, duration, count1);  //call method to test for empty values
                
                Room r = new Room(roomNum, building, capacity, campus, maintenanceDay, startTime, duration);    //add those strings into the room object
                room.add(r);													//add that room object to the array of rooms created earlier
                count1++;
            }
            s1.close();															//close scanner when done scanning lines
            
            int count2 = 1;														//create a count for the line number in the loop
            for(Room r: room){													//test loop to loop through the array of room objects and call methods to validate data in each line
            	isValidInteger(r.getCapacity(), "Capacity", count2);			//call method to test if capacity is valid integer
            	isCorrectDay(r.getMaintenanceDay(), count2);					//call method to test day value inputs
            	isCorrectCampus(r.getCampus(), count2);							//call method to test campus value inputs
            	isValidStartTime(r.getStartTime(), count2);						//call method to test if startTime is valid integer and in range of 1-24
            	isValidDuration(r.getDuration(), count2);						//call method to test if duration is valid integer and in range of 1-5
            	checkBuilding(r.getBuilding(), count2);							//call method to test building is alphanumeric and not greater than 10 char
            	checkRoomNum(r.getRoomNum(), count2);							//call method to test roomNum is alphanumeric and not greater than 5 char
            	count2++;														//increase count
            } 
            
            System.out.println("ErrorCount: " + errorCount);					//optional print errorCount
            System.out.println();       
            printInput(room);  													//call method to print the input stored in list of room objects
            
        } catch (FileNotFoundException e) {     								//if file not found catch exception    
            e.printStackTrace();
        }		
	}
	
	
	private static void printInput(List<Room> r1) {								//method that accepts list or room objects and prints them
		List<Room> room = r1;								
		 System.out.printf("\t\t" +  "RoomNum"									//print statement to print details of each line
     			+  " Building"
     			+  " Capacity"
     			+  " Campus"
     			+  " MaintenanceDay"
     			+  " StartTime"
     			+  " Duration" + "\n");
         int count = 1;														//create a count for the line number in the loop
         for(Room r: room){														//test loop to loop through the array of room objects and look at the data
         	System.out.printf("Line " + count 
         			+ ": " + "\t" +  r.getRoomNum()								//print statement to print details of each line formatted in columns
         			+ "\t" + r.getBuilding()
         			+ "\t " + r.getCapacity()
         			+ "\t  " + r.getCampus()
         			+ "\t  " + r.getMaintenanceDay()
         			+ "  \t" + r.getStartTime()
         			+ "\t  " + r.getDuration() + "\n");
         	count++;															//increase count
         }
	}
	
	private static void checkPipeCountPerLine(String l1, int c){				//method to test number of fields (pipes) per line of input data
		int pipeCount = 0;														//initialize pipeCount
		
		for(int i = 0; i < l1.length(); i++) {									//loop through characters in the line
		    if ('|' == l1.charAt(i)) {											//if character is pipe increase pipeCount
		      pipeCount++;
		    }
		}
		if(pipeCount != 6){														//for rooms file each line should have 6 pipes. If not 6 pipes then print out line number and pipeCount
			System.out.println("Line " + c +" Does not have correct number or fields.  PipeCount = " + pipeCount);
			errorCount++;
			//System.exit(1);													//option to exit after printing above line error
		}
	}
	
	
	private static void isValidInteger(String num, String value, int c) {		//method to test string to see if it is a valid integer
        boolean testValue = true;												//initialize boolean for outcome
		try{
            Integer.parseInt(num);												//try to parse string to int
            testValue = true;													//if parse to int works then testValue is true
        }catch(NumberFormatException e){										//if parse to int does not work catch exception and testValue is false
        	testValue = false;
        }
        if(testValue == false) {												//if testValue is false then print out which line and value is not an integer
    		System.out.println("Line: " + c + " " + value + " is not a valid integer");	
    		errorCount++;
    		//System.exit(1);													//option to exit after printing above line error
    	}
    }
	
	private static void isValidStartTime(String st, int c) {					//method to test startTime is valid integer and in range of 1-24
		try {
		    int stNum = Integer.parseInt(st);									//first try to parse string to int
		    if(0 > stNum || stNum > 25){										//test if int is not in range of 1-24
		    	System.out.println("Line: " + c + " StartTime value not in range 1-24");		//if not, print which line
		    	errorCount++;
		    }
		} catch (NumberFormatException e) {										//if could not parse startTime to int catch exception
			System.out.println("Line: " + c + " StartTime not valid integer");		//print which line could not parse to int
			errorCount++;
		}
	}
	
	
	private static void isValidDuration(String d, int c) {						//method to test duration is valid integer and in range of 1-5
		try {
		    int durNum = Integer.parseInt(d);									//first try to parse string to int
		    if(0 > durNum || durNum > 5){										//test if int is not in range of 1-5
		    	System.out.println("Line: " + c + " Duration value not in range 1-5");		//if not, print which line
		    	errorCount++;
		    }
		} catch (NumberFormatException e) {										//if could not parse duration to int catch exception
			System.out.println("Line: " + c + " Duration not valid integer");		//print which line could not parse to int
			errorCount++;
		}
	}
	
	
	private static void isNotEmpty(String i1, String i2, String i3, String i4, String i5, String i6, String i7, int c) {	 //method to test for empty values
		String[] inputs = {i1, i2, i3, i4, i6, i7};     				 		//place inputs into an array of strings
		for(String test : inputs) {												//loop through the strings to test each one
			if (test == null || test.trim().isEmpty()) {						//test for null,empty,whitespace
				System.out.println("Line: " + c + " Has an empty value");		//print which line has empty value
				errorCount++;
				//System.exit(1);												//option to exit after printing above line error
			}
		}
	}	
	
	
	private static void isCorrectDay(String day, int c) {						//Method to test MaintenanceDay values
		String [] days = {"monday", "tuesday", "wednesday", "thursday", "friday"}; //create array of day values that are valid for field
		boolean found = false;													//initialize boolean to false
		for (String element : days) {											//loop through valid day values and compare to actual input
		    if (day.equalsIgnoreCase(element)) {								//compare ignoring case
		        found = true;													//if input matches a valid day set boolean to true
		        break;															//and break out of loop
		    }
		}
		if (!found) {															//if input doesnt match a valid day then print which line doesnt match
			System.out.println("Line: " + c + " MaintenanceDay is not valid input");
			errorCount++;
			//System.exit(1);													//option to exit after printing above line error
		}
	}
	
	private static void isCorrectCampus(String campus, int c) {					//Method to test Campus values
		String [] campuses = {"north", "south", "east", "west"}; 					//create array of campus values that are valid for field
		boolean found = false;													//initialize boolean to false
		for (String element : campuses) {											//loop through valid campus values and compare to actual input
		    if (campus.equalsIgnoreCase(element)) {								//compare ignoring case
		        found = true;													//if input matches a valid campus set boolean to true
		        break;															//and break out of loop
		    }
		}
		if (!found) {															//if input doesnt match a valid campus value then print which line doesnt match
			System.out.println("Line: " + c + " Campus is not valid input");
			errorCount++;
			//System.exit(1);													//option to exit after printing above line error
		}
	}
	
	private static void checkBuilding(String b, int c){							//method to check building input is alphanumeric and no more than 10 char
		String pattern= "^[a-zA-Z0-9]*$";										//alphanumeric pattern to test against
        if(!b.matches(pattern)){												//if input does not match pattern
        	System.out.println("Line: " + c + " Building input is not valid");	//print line that does not match
        	errorCount++;
        }
        if(b.length() > 10) {													//if building input is greater than 10 char
        	System.out.println("Line: " + c + " Building input greater than 10 char");  //print which line
        	errorCount++;
        }
    }
	
	private static void checkRoomNum(String r, int c){							//method to check roomNum input is alphanumeric and no more than 5 char
		String pattern= "^[a-zA-Z0-9]*$";										//alphanumeric pattern to test against
        if(!r.matches(pattern)){												//if input does not match pattern
        	System.out.println("Line: " + c + " RoomNum input is not valid");	//print line that does not match
        	errorCount++;
        }
        if(r.length() > 5) {													//if roomNum input is greater than 5 char
        	System.out.println("Line: " + c + " RoomNum input greater than 10 char");  //print which line
        	errorCount++;
        }
    }
	
}	


