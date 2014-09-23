package tools;

public class Log {
	public Log() {}

	// For the lazy
	public static void print(String msg) {
		boolean testing = true;
		if(testing) System.out.println(msg);
	}

	// Wow, next-gen graphics!
	public static void printTitle() {
		print("█  █▀   ▄   ▄█   ▄▀   ▄  █    ▄▄▄▄▀      ");
		print("█▄█      █  ██ ▄▀    █   █ ▀▀▀ █         ");
		print("█▀▄  ██   █ ██ █ ▀▄  ██▀▀█     █         ");
		print("█  █ █ █  █ ▐█ █   █ █   █    █          ");
		print("  █  █  █ █  ▐  ███     █    ▀           ");
		print(" ▀   █   ██            ▀                 ");
		print("▄█▄    ██   █ ▄▄  ▄█    ▄▄▄▄▀ ██   █     ");
		print("█▀ ▀▄  █ █  █   █ ██ ▀▀▀ █    █ █  █     ");
		print("█   ▀  █▄▄█ █▀▀▀  ██     █    █▄▄█ █     ");
		print("█▄  ▄▀ █  █ █     ▐█    █     █  █ ███▄  ");
		print("▀███▀     █  █     ▐   ▀         █     ▀ ");
		print("         █    ▀                 █        ");
		print("        ▀                      ▀         ");
		print("-----------------2014--------------------");
	}
}
