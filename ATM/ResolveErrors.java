package ATM;

public class ResolveErrors {
	public static boolean isThatCard(String cardNumber) {
		String RegStr = "\\d{4}[-]{1}\\d{4}[-]{1}\\d{4}[-]{1}\\d{4}";
		return cardNumber.matches(RegStr);		
	}
	public static boolean isThatPin(String pin) {
		String RegStr = "\\d{4}";
		return pin.matches(RegStr);		
	}


}
