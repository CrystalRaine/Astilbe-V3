package internalData;

public class Assignment {
	
	public String text;
	public String date;	// DD/MM/YYYY
	
	public Assignment(String s) {
		if(s.length() > 9) {
			date = s.substring(s.length()-10);
			text = s.substring(0, s.length()-10);
		} else {
			text = s;
			date = java.time.LocalDate.now().getDayOfMonth() + "/" + java.time.LocalDate.now().getMonth() + "/" + java.time.LocalDate.now().getYear();
		}
	}
	
	public String getSaveText() {
		return text + date;
	}
	
	public boolean isValid() {
		if(date.length() != 10) {
			return false;
		}
		if(date.charAt(2) != '/' && date.charAt(5) != '/') {
			return false;
		}
		try {
			Integer.parseInt(date.substring(0,2));
			Integer.parseInt(date.substring(3,5));
			Integer.parseInt(date.substring(6));
		} catch(NumberFormatException e) {
			return false;
		}
		return true;
	}
}
