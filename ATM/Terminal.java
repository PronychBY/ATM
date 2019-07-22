package ATM;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;

public class Terminal {
	//private ArrayList<Account> accounts = new ArrayList();
	
	public static void main(String[] args) {
		ATMin.initialization();
		ATMin.dbf.saveAccounts(ATMin.curentAccounts);
		
		//---������ ����� ��������
		ATMin.scanner = new Scanner(System.in);
	    do {
	    	System.out.println("������� ����� ��������: ");
		    String inputCard = ATMin.scanner.nextLine();
		    if (inputCard.equals("exit")) {
		    	break;
		    }
		    
		    if (!ResolveErrors.isThatCard(inputCard)) {
			    System.out.println("����������� ����. ������� �� ���������� �������:XXXX-XXXX-XXXX-XXXX");
			    continue;
		    }
		    //---���� ��������
		    int positionCard = ATMin.findCardNumber(inputCard);
		    if (ATMin.findCardNumber(inputCard)==-1) {
			    System.out.println("�������� � ����� ������� �� ���������� � ����. ���������� ������ ��� ���.");
			    continue;
			}
		    
		    System.out.println("������� ������: ");
		    String pin = ATMin.scanner.nextLine();
		    if (pin.equals("exit")) {
		    	break;
		    }
		    if (!ResolveErrors.isThatPin(pin)) {
			    System.out.println("����������� ������ �������. ������� �� ���������� �������:XXXX");
			    continue;
		    }
		    if (ATMin.authorization(inputCard,pin)<0) {
				continue;
			}
		    
		    //---������������ ������ ������ � ����������
		    ATMin.transaction(positionCard);
		    
			
	    }while (true);
	   
	    ATMin.scanner.close();
	    //---������ ������
		
	}

}
