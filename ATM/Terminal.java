package ATM;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;

public class Terminal {
	//private ArrayList<Account> accounts = new ArrayList();
	
	public static void main(String[] args) {
		ATMin.initialization();
		ATMin.dbf.saveAccounts(ATMin.curentAccounts);
		
		//---ввести номер карточки
		ATMin.scanner = new Scanner(System.in);
	    do {
	    	System.out.println("Введите номер карточки: ");
		    String inputCard = ATMin.scanner.nextLine();
		    if (inputCard.equals("exit")) {
		    	break;
		    }
		    
		    if (!ResolveErrors.isThatCard(inputCard)) {
			    System.out.println("Некоректный ввод. Вводить по следующему шаблону:XXXX-XXXX-XXXX-XXXX");
			    continue;
		    }
		    //---ищем карточку
		    int positionCard = ATMin.findCardNumber(inputCard);
		    if (ATMin.findCardNumber(inputCard)==-1) {
			    System.out.println("Карточка с таким номером не существует в базе. Попробуйте ввести еще раз.");
			    continue;
			}
		    
		    System.out.println("Введите пинкод: ");
		    String pin = ATMin.scanner.nextLine();
		    if (pin.equals("exit")) {
		    	break;
		    }
		    if (!ResolveErrors.isThatPin(pin)) {
			    System.out.println("Некоректный формат пинкода. Вводить по следующему шаблону:XXXX");
			    continue;
		    }
		    if (ATMin.authorization(inputCard,pin)<0) {
				continue;
			}
		    
		    //---залогинились теперь работа с банкоматом
		    ATMin.transaction(positionCard);
		    
			
	    }while (true);
	   
	    ATMin.scanner.close();
	    //---ввести пинкод
		
	}

}
