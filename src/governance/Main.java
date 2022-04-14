package governance;
import dataHandling.DataLoader;
import display.Window;
public class Main {
	public static void main(String[] args) {
		Window w = new Window();
		DataLoader.load("data", w);
		Window.refresh();
	}
}