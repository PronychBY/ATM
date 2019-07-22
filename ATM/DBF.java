package ATM;
import java.io.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class DBF {
	//работа с файлами
	String pathToDBDir;
	String pathAccounts;
	String pathLogs;
	
	File dirDB;
	File fileAccounts;
	File fileLogs;
	
	public DBF() {
		//System.getProperty("user.dir");
		createDefaultFDBFiles();
	}
	
	public void createDefaultFDBFiles() {
		//pathToDBDir = "D://SomeDir"+"//FDB";
		pathToDBDir = System.getProperty("user.dir")+"//FDB";
		dirDB = new File(pathToDBDir);
        if(!dirDB.exists()) {
    		//System.out.println("Create new DB dir:"+pathToDBDir);
    		boolean created = dirDB.mkdir();
       		if (created) {
       			System.out.println("Create new DB dir:"+dirDB);
       		}
        }
        
        //---инициализируем ДБ файлы, если не существуют то добавляем.
        pathAccounts = pathToDBDir+"//accounts.txt";
        fileAccounts = new File(pathAccounts);
        if(!fileAccounts.exists()) {
            try
            {
           		boolean created = fileAccounts.createNewFile();
           		if (created) {
           			System.out.println("Create new Accounts file:"+fileAccounts);
           		}
       		}
            catch(IOException ex){
                
                System.out.println(ex.getMessage());
            }              
        }
        
        pathLogs = pathToDBDir+"//logs.txt";
        fileLogs = new File(pathLogs);
        if(!fileLogs.exists()) {
            try
            {
	    		boolean created = fileLogs.createNewFile();
	    		if (created) {
	        		System.out.println("Create new logs file:"+fileLogs);
	    		}
    		}
            catch(IOException ex){
                
                System.out.println(ex.getMessage());
            }              
            
        }
	}
	
	public ArrayList getCurentAccounts(){
		ArrayList<Account> curentAccounts = new ArrayList();
		
		
		//читаем из файла данные по зарегистрированным карточкам
		String text = "";
		try {
			byte[] data = Files.readAllBytes(fileAccounts.toPath());
			text = new String(data, StandardCharsets.UTF_8);
		} catch (IOException ex) {
	        System.out.println(ex.getMessage());
		}
		
		//парсим файл в объекты account
		String[] results = text.split("\\s+");
		//System.out.println(Arrays.toString(results));		
		
		int numberOfFieldsInFile = 5;//cardNumber,pin,balance,errPin,date
		int numOfCards = results.length/numberOfFieldsInFile;
		for (int i = 0; i < numOfCards; i++) {
			String cardNumber = results[i*numberOfFieldsInFile+0];
			if (!ResolveErrors.isThatCard(cardNumber)) {
				System.out.println("File is broken. Card number error.");
			}
			String pin = results[i*numberOfFieldsInFile+1];
			if (!ResolveErrors.isThatPin(pin)) {
				System.out.println("File is broken. Pin error.");
			}
			
			double balance = 0.0;
			try {
				balance = Double.parseDouble(results[i*numberOfFieldsInFile+2]);
		    } catch (NumberFormatException e) {
		        System.out.println("File is broken. Balance error.");
		    }
			
			int pinMistakes = 0;
			try {
				pinMistakes = Integer.parseInt(results[i*numberOfFieldsInFile+3]);
		    } catch (NumberFormatException e) {
		        System.out.println("File is broken. pinMistakes error.");
		    }
			
			
			Date dateOfBlock = new Date(0);
			try {
				dateOfBlock = new Date(Long.parseLong(results[i*numberOfFieldsInFile+4]));
		    } catch (NumberFormatException e) {
		        System.out.println("File is broken. dateOfBlock error.");
		    }
			
			Account newAcc = new Account(cardNumber, pin, balance, pinMistakes,dateOfBlock);
			curentAccounts.add(newAcc);
		}
		
		return curentAccounts; 
	}

	public void saveAccounts(ArrayList<Account> curentAccounts) {
        
		try(FileWriter writer = new FileWriter(fileAccounts))//, false
        {
			for (int i = 0; i < curentAccounts.size(); i++) {
				writer.write(curentAccounts.get(i).getStringForFile());
	            writer.append(" ");
			}
            
            writer.flush();
        }
        catch(IOException ex){
             
            System.out.println(ex.getMessage());
        } 		
	}


}
