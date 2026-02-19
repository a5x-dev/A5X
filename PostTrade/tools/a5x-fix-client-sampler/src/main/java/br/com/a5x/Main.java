package br.com.a5x;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import quickfix.DefaultMessageFactory;
import quickfix.FileLogFactory;
import quickfix.FileStoreFactory;
import quickfix.MessageFactory;
import quickfix.Session;
import quickfix.SessionID;
import quickfix.SessionSettings;
import quickfix.SocketInitiator;

public class Main {
	private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static final PrintWriter writer = new PrintWriter(System.out, true);

	public static void main(String[] args) throws Exception {
		InputStream cfgStream = Main.class.getClassLoader().getResourceAsStream("client.cfg");
		if (cfgStream == null) {
			throw new IllegalStateException("Could not find client.cfg on classpath (src/main/resources)");
		}
		SessionSettings settings = new SessionSettings(cfgStream);

		FixClient application = new FixClient(settings);
		FileStoreFactory storeFactory = new FileStoreFactory(settings);
		FileLogFactory logFactory = new FileLogFactory(settings);
		MessageFactory messageFactory = new DefaultMessageFactory();
		SocketInitiator initiator = new SocketInitiator(application, storeFactory, settings, logFactory,
				messageFactory);

		initiator.start();
		writer.println("FIX Client started.\n");

		SessionID sessionId = initiator.getSessions().get(0);

		while (true) {
			try {
				onChoice(sessionId, application, initiator);
			} catch (Exception e) {
				writer.println("Error: " + e.getMessage());
			}
		}
	}

	private static void showMenu() {
		writer.println("\n=== FIX Client Menu ===");
		writer.println("1 - Send New Message");
		writer.println("2 - Send Success Message (ex5-alc-ins-0401)");
		writer.println("3 - Send Error Message (ex5-col-exp-0501)");
		writer.println("4 - View Live Logs");
		writer.println("5 - Exit");
		writer.print("Choose: ");
		writer.flush();
	}

	private static void onChoice(SessionID sessionId, FixClient application, SocketInitiator initiator)
			throws IOException {
		showMenu();
		String choice = reader.readLine();
		
		if (choice == null || choice.isBlank()) {
			return;
		}

		if (!Session.lookupSession(sessionId).isLoggedOn() && !"5".equals(choice)) {
			writer.println("Session not logged on. Waiting...");
			return;
		}

		switch (choice) {
			case "1" -> {
				writer.print("Enter JSON file path: ");
				writer.flush();
				String jsonStringPath = reader.readLine();
				if (jsonStringPath == null || jsonStringPath.isBlank()) {
					writer.println("Invalid file path.");
					return;
				}
				writer.print("Enter the message name: ");
				writer.flush();
				String msgName = reader.readLine();
				if (msgName == null || msgName.isBlank()) {
					writer.println("Invalid message name.");
					return;
				}
				application.sendNewMessage(sessionId, jsonStringPath, msgName);
			}
			case "2" -> {
				writer.println("Sending success message...");
				application.sendNewMessage(sessionId, "samples/ex5-alc-ins-0401-sucesso.json", "ex5-alc-ins-0401");
			}
			case "3" -> {
				writer.println("Sending error message...");
				application.sendNewMessage(sessionId, "samples/ex5-col-exp-0501-erro.json", "ex5-col-exp-0501");
			}
			case "4" -> viewLiveLogs();
			case "5" -> {
				writer.println("Shutting down...");
				initiator.stop();
				reader.close();
				writer.close();
				System.exit(0);
			}
			default -> writer.println("Invalid option. Try again.");
		}
	}

	private static void viewLiveLogs() throws IOException {
		File logFile = new File("logs/application.log");
		if (!logFile.exists()) {
			writer.println("Log file not found.");
			return;
		}

		writer.println("\n=== Live Logs (Press ENTER to return) ===");
		writer.flush();

		try (BufferedReader logReader = new BufferedReader(new java.io.FileReader(logFile))) {
			String line;
			while ((line = logReader.readLine()) != null) {
				writer.println(line);
			}
			writer.flush();
		}

		writer.println("\nPress ENTER to return to menu...");
		writer.flush();
		reader.readLine();
	}
}
