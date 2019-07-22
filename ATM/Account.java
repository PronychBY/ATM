package ATM;

import java.util.Date;

public class Account {
	private double balance;
	private String cardNumber;
	private String pin;
	private int pinMistakes;
	private Date dateOfBlock;
	
	public Account(String inCardNumber, String inPin, double inBalance, int inPinMistakes,Date inDate) {
		cardNumber = inCardNumber;
		pin = inPin;
		balance = inBalance;
		pinMistakes = inPinMistakes;
		dateOfBlock = inDate;
	}
	public void deposit(double amount) {
		balance = balance + amount;
	    System.out.println("Баланс карточки пополнен на сумму:"+amount);
		
	}
	public boolean withdraw(double amount) {
		if (balance < amount) {
			System.out.println("Недостаточно средств на счету.");
			return false;
		}
		if (amount < 0) {
			System.out.println("Сумма не может быть отрицательной.");
			return false;
		}
		if (ATMin.limitATM < amount) {
			System.out.println("Лимит средств в банкомате превышен.");
			return false;
		}
		balance = balance - amount;
		ATMin.limitATM = ATMin.limitATM - amount;  
	    System.out.println("Снятие средств завершено успешно на сумму:"+amount);
		return true;
	}
	
	public void addPinMistakes() {
		pinMistakes = pinMistakes + 1;
		if (pinMistakes >= 3) {
			pinMistakes = 0;
			dateOfBlock = new Date();
		}
	}
	public boolean isBlocked() {
		Date curDate = new Date();
		long betweenDate = (curDate.getTime() - dateOfBlock.getTime());
		if (betweenDate < (24*3600*1000)) {
			return true;
		}
		return false;
	}
	
	
	public double getBalance() {
		return balance;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public String getPin() {
		return pin;
	}
	public int getPinMistakes() {
		return pinMistakes;
	}
	public Date getDateOfBlock() {
		return dateOfBlock;
	}
		
	public String getStringForFile() {
		return cardNumber+" "+pin+" "+balance+" "+pinMistakes+" "+dateOfBlock.getTime();
		//date.toString()
	}
}
