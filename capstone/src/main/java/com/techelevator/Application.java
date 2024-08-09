package com.techelevator;



import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;



public class Application {
	private Logger logger = new Logger("log.txt");
	private Logger report = new Logger("report" + LocalDateTime.now());
	private Scanner inputScanner = new Scanner(System.in);
	private Map<String, Inventory> inventoryMap = new HashMap<>();
	private double currentMoney;




	public static void main(String[] args) {
		Application program = new Application();
		program.run();
	}
	private void run() {
		logger.writeMessage("MACHINE STARTED");
		// 1. Load data from file
		loadFromFile();

		// 2. Show main menu
		showLevel1Menu();
		logger.writeMessage("MACHINE STOPPED");
	}

	private void loadFromFile() {
		inventoryMap = new HashMap<>();
		File file = new File("vendingmachine.csv");

		try( Scanner fileScanner = new Scanner(file) ) {
			while( fileScanner.hasNextLine() ) {
				String line = fileScanner.nextLine();
				String [] lineArr = line.split("\\|");

				String priceString = lineArr[2];
				double price = Double.parseDouble(priceString);

				if( lineArr[3].equals("Chips") ) {
					Inventory inventory = new Chips(lineArr[1], price);
					inventoryMap.put(lineArr[0], inventory);
				}else if(lineArr[3].equals("Gum")){
					Inventory inventory = new Gum(lineArr[1],price);
					inventoryMap.put(lineArr[0],inventory);
				}else if(lineArr[3].equals("Candy")){
					Inventory inventory = new Candy(lineArr[1],price);
					inventoryMap.put(lineArr[0],inventory);
				}else if(lineArr[3].equals("Drinks")){
					Inventory inventory = new Beverages(lineArr[1],price);
					inventoryMap.put(lineArr[0],inventory);
				}
//				// Index from vendingmachine.csv
//				// Slot ID
//				String slotId = lineArr[0];
//				// Item Name
//				String name = lineArr[1];
//				// Price of Item
//
//				// Type of Item
//				String type = lineArr[3];
//
//				// creates the inventory object
//				Inventory inventory;
//				switch (type.toLowerCase()) {
//					case "chip":
//					case "candy":
//					case "drink":
//					case "gum":
//						inventory = new Inventory(name, price, type);
//						break;
//					default:
//						logger.writeMessage("Unknown product type: " + type);
//						continue;
//
//				}
//				 inventoryMap.put(slotId, inventory);

			}
		} catch(FileNotFoundException e) {
			System.out.println("could not load file: " + e .getMessage());
		} catch (NumberFormatException e) {
			logger.writeMessage("Invalid price format: " + e .getMessage());
		}
		logger.writeMessage("FILE LOADED");
	}


	public void showLevel1Menu() {
		boolean stay = true;
		while(stay){
			System.out.println("1. Display Vending Machine Items 2. Purchase 3. Exit");
			String choice = inputScanner.nextLine();
			if(choice.equals("1")){
				printItems();
			}else if(choice.equals("2")){
				handlePurchase();
			}else if(choice.equals("3")) {
				System.out.println("THANK YOU!");
				stay = false;
			}else if(choice.equals("4")){
				saleReport();

			}else{
				System.out.println("Invalid Option");
			}
		}
	}


	private void printItems() {
		System.out.println("*** Vending Machine Item ***");
		for (Map.Entry<String, Inventory> map : inventoryMap.entrySet()) {
			System.out.println(map.getKey() + " " + map.getValue().getItem() + " $"
					+map.getValue().getPrice() +" Quantity:" + map.getValue().getQuantity());
		}
	}

	public void handlePurchase() {
		boolean stay = true;
		while (stay) {
			System.out.println("1. Feed Money 2. Select Product 3. Finish Transaction");
			String choice = inputScanner.nextLine();
			if(choice.equals("1")){
				System.out.println("Enter amount to add: ");
				String amountStr = inputScanner.nextLine();
				double amount = Double.parseDouble(amountStr);
				if(amount>0){
					currentMoney += amount;
					System.out.println("Current Money Provided: $" + currentMoney);
					logger.writeMessage("FEED MONEY: $" + amount + " $" + currentMoney);
				}else{
					System.out.println("Invalid amount. Please enter a positive amount");
				}
			}else if(choice.equals("2")){
				System.out.println("*** AVAILABLE PRODUCTS ***");
				for(Map.Entry<String,Inventory> entry : inventoryMap.entrySet()){
					System.out.println(entry.getKey() + " "+ entry.getValue().getItem() + ": $" + entry.getValue().getPrice()
							+ " Quantity: " +entry.getValue().getQuantity());
				}
				dispensed();

			}else if(choice.equals("3")){
				System.out.println("Transaction complete. returning change: $" + currentMoney);
				String changeMsg = changes(currentMoney);
				System.out.println(changeMsg);
				currentMoney=0.00;
				stay = false;
			}else{
				System.out.println("Invalid Option");
			}
		}
	}

	private void dispensed() {
		System.out.println("Please enter the slot ID to select: ");
		String inputSlotID = inputScanner.nextLine();
		if(inventoryMap.containsKey(inputSlotID)){
			Inventory item = inventoryMap.get(inputSlotID);
			double price = item.getPrice();
			int quantity = item.getQuantity();

			if(quantity==0){
				System.out.println("SOLD OUT");
			}else if(currentMoney > price && quantity>0){
				currentMoney -= price;
				item.setQuantity(quantity-1);
				inventoryMap.put(inputSlotID,item);
				System.out.println("Dispensed." + "Remaining balance: $" + currentMoney);
				System.out.println(inventoryMap.get(inputSlotID).getSound());
				logger.writeMessage(inputSlotID + " "+ item.getItem() +" $" + item.getPrice() +" $"+ currentMoney );
			}else{
				System.out.println("Insufficient funds. Please add more money.");
			}
		}else {
			System.out.println("Invalid Slot ID, please try again.");
		}
	}

	private void saleReport() {
		System.out.println("**** SALES REPORT ****");
		System.out.println("Product | Quantity Sold");
		System.out.println("----------------------------");
		double total = 0.0;
		for(Map.Entry<String, Inventory> entry: inventoryMap.entrySet()){
			int itemSaleQuantity = 5-entry.getValue().getQuantity();
			total += entry.getValue().getPrice() * itemSaleQuantity;
			System.out.println(entry.getValue().getItem() + " | " + itemSaleQuantity);
		}
		System.out.println("**TOTAL SALES** $" + total);
	}


	public String changes(double moneyLeft) {
		int cents = (int) (moneyLeft * 100);
		int quarters = cents/25;
		cents %= 25;
		int dimes = cents/10;
		cents %= 10;
		int nickels = cents/5;
		cents %=5;
		int pennies = cents;
		return "Quarters: " + quarters + " Dimes: " + dimes + " Nickels: " + nickels +" Pennies: " + pennies;
	}

}





