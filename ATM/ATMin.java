package ATM;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class ATMin {
	public static ArrayList<Account> curentAccounts;
	public static DBF dbf;
	public static Scanner scanner;
	public static double limitATM;
	
	
	public static void addSomeDefaultData() {
		//---добавим немного данных для тестирования
		if (curentAccounts.size()==0) {
			Account newDefaultData = new Account("1234-5555-6666-7777","1234",0.0,0,new Date(0));
			curentAccounts.add(newDefaultData);
			newDefaultData = new Account("4444-1111-6688-9999","1554",100.0,1,new Date(0));
			curentAccounts.add(newDefaultData);
			newDefaultData = new Account("3333-2111-6677-0000","1554",100.0,1,new Date());
			curentAccounts.add(newDefaultData);
		}
	}
	
	public static void initialization() {
		//получим в curentAccounts все данные по карточкам из файла
		dbf = new DBF();
		curentAccounts = dbf.getCurentAccounts();
		limitATM = 300000;
		
		//---добавим немного данных для тестирования
		addSomeDefaultData();
	}
	
	public static int authorization(String cardNumber, String pin) {
		for (int i = 0; i < curentAccounts.size(); i++) {
			if (curentAccounts.get(i).getCardNumber().equals(cardNumber)) {
				
				if (curentAccounts.get(i).getPin().equals(pin)) {
					//проверить на блокировку на сутки
					if (curentAccounts.get(i).isBlocked()) {
					    System.out.println("Аккаунт заблокирован.");
						return -3;//акаунт заблокирован
					}
					return i;//все ок
				}
				else {
				    System.out.println("Неверный пинкод.");
					curentAccounts.get(i).addPinMistakes();
					ATMin.dbf.saveAccounts(ATMin.curentAccounts);
					return -2;//ошибка ввода пинкода
				}
					
			}
		}
	    System.out.println("Неопознанная ошибка.");//поидее сюда никогда незайдет
		return -1;
	}
	
	public static int findCardNumber(String cardNumber) {
	    for (int i = 0; i < curentAccounts.size(); i++) {
			if (curentAccounts.get(i).getCardNumber().equals(cardNumber)) {
				return i;
			}
		}
		return -1;
	}
	
	public static void transaction(int positionCard) {
	    boolean logOut = false;
		do {
		    System.out.println("Выберите действие:");
			System.out.println("1:Посмотреть баланс");
			System.out.println("2:Пополнить баланс");
			System.out.println("3:Снять средства со счета");
			System.out.println("4:Выход");
		    
			int choice;
			if (!scanner.hasNextInt()) {
				System.out.println("Ошибка ввода попробуйте еще раз.");
				System.out.println();
				scanner.next();
				continue;
			}
			
			choice = scanner.nextInt();
		    scanner.nextLine();
			
			double amount=0;
			switch(choice) {
				case 1: 
					System.out.println("Текущий баланс:"+curentAccounts.get(positionCard).getBalance());
					System.out.println();
					break;
				case 2: 
					System.out.println("Какую сумму хотите положить на счет?");
					System.out.println();
					if (!scanner.hasNextDouble()) {
						System.out.println("Ошибка ввода суммы.");
						System.out.println();
						scanner.next();
						continue;
					}
					amount = scanner.nextDouble();
					if (amount > 1000000) {
						System.out.println("Сумма пополнения не может превышать 1000000");
						break;
					}
					if (amount < 0) {
						System.out.println("Сумма не может быть отрицательной.");
						break;
					}
					curentAccounts.get(positionCard).deposit(amount);
					ATMin.dbf.saveAccounts(ATMin.curentAccounts);
					break;
				case 3: 
					System.out.println("Какую сумму хотите снять со счета?");
					if (!scanner.hasNextDouble()) {
						System.out.println("Ошибка ввода суммы.");
						System.out.println();
						scanner.next();
						continue;
					}
					amount = scanner.nextDouble();
					curentAccounts.get(positionCard).withdraw(amount);
					ATMin.dbf.saveAccounts(ATMin.curentAccounts);
					break;
				case 4: 
					logOut = true;
					ATMin.dbf.saveAccounts(ATMin.curentAccounts);
					return;
			}
			
		}while (!logOut);
	}

}
