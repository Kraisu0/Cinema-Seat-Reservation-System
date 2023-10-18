import java.io.*;
import java.util.*;

public class App {
    private static final String CLIENTS_FILE_PATH = "clients.ser";
    private static final String SCREENINGS_FILE_PATH = "screenings.ser";

    public static void main(String[] args) throws Exception {
        Map<Character, Map<Integer, Boolean>> seats1 = new HashMap<>();
        seats1.put('A', new HashMap<>());
        seats1.put('B', new HashMap<>());
        seats1.put('C', new HashMap<>());
        seats1.put('D', new HashMap<>());
        seats1.put('E', new HashMap<>());
        seats1.put('F', new HashMap<>());
        seats1.put('G', new HashMap<>());
        seats1.put('H', new HashMap<>());
        seats1.put('I', new HashMap<>());
        seats1.put('J', new HashMap<>());

        for (Map.Entry<Character, Map<Integer, Boolean>> row : seats1.entrySet()) {
            for (int i = 1; i <= 10; i++) {
                row.getValue().put(i, false);
            }
        }

        Map<Character, Map<Integer, Boolean>> seats2 = new HashMap<>();
        seats2.put('A', new HashMap<>());
        seats2.put('B', new HashMap<>());
        seats2.put('C', new HashMap<>());
        seats2.put('D', new HashMap<>());
        seats2.put('E', new HashMap<>());
        seats2.put('F', new HashMap<>());
        seats2.put('G', new HashMap<>());
        seats2.put('H', new HashMap<>());
        seats2.put('I', new HashMap<>());
        seats2.put('J', new HashMap<>());

        for (Map.Entry<Character, Map<Integer, Boolean>> row : seats2.entrySet()) {
            for (int i = 1; i <= 10; i++) {
                row.getValue().put(i, false);
            }
        }

        
        Screening screening1 = new Screening("The Matrix", "2021-10-01", "20:00", 16, seats1);
        Screening screening2 = new Screening("The Godfather", "2021-10-02", "19:30", 12, seats2);

        List<Client> clients = new ArrayList<>();

        File clientsFile = new File(CLIENTS_FILE_PATH);
        if (clientsFile.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(clientsFile))) {
                clients = (List<Client>) ois.readObject();
            } catch (ClassNotFoundException e) {
                System.err.println("Error reading clients file: " + e.getMessage());
            }
        }

        File screeningsFile = new File(SCREENINGS_FILE_PATH);
        if (screeningsFile.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(screeningsFile))) {
                screening1 = (Screening) ois.readObject();
                screening2 = (Screening) ois.readObject();
            } catch (ClassNotFoundException e) {
                System.err.println("Error reading screenings file: " + e.getMessage());
            }
        }

        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning)
        {
            System.out.println("Welcome to the Cinema Reservation System!");
            System.out.println("Please choose an option:");
            System.out.println("1. Make a reservation");
            System.out.println("2. Print reservations");
            System.out.println("3. Exit");
            int choice = scanner.nextInt();
            if (choice == 1)
            {
                System.out.println("Choose a screening:");
                System.out.println("1. " + screening1.getTitle() + " " + screening1.getDay() + " " + screening1.getTime());
                System.out.println("2. " + screening2.getTitle() + " " + screening2.getDay() + " " + screening2.getTime());
                choice = scanner.nextInt();
                scanner.nextLine();

                Screening screening;
                if (choice == 1) {
                    screening = screening1;
                } else if (choice == 2) {
                    screening = screening2;
                } else {
                    System.out.println("Invalid choice.");
                    return;
                }

                System.out.print("Enter your surname: ");
                String surname = scanner.nextLine();

                System.out.print("Enter your name: ");
                String name = scanner.nextLine();

                System.out.print("Enter your email: ");
                String email = scanner.nextLine();

                System.out.print("Enter your phone number: ");
                String phoneNumber = scanner.nextLine();

                System.out.println("Screening: " + screening.getTitle() + " " + screening.getDay() + " " + screening.getTime());

                Map<Character, Map<Integer, Boolean>> availableSeats = new HashMap<>();
                for (Map.Entry<Character, Map<Integer, Boolean>> row : screening.getSeats().entrySet()) {
                    availableSeats.put(row.getKey(), new HashMap<>(row.getValue()));
                    
                }

                for (Client client : clients) {
                    if (client.getScreening().getTitle().equals(screening.getTitle()) &&
                            client.getScreening().getDay().equals(screening.getDay()) &&
                            client.getScreening().getTime().equals(screening.getTime())) {
                        for (String seat : client.getSeats()) {
                            char row = seat.charAt(0);
                            int seatNumber = Integer.parseInt(seat.substring(1));
                            availableSeats.get(row).put(seatNumber, true);
                        }
                    }
                }

                System.out.println("Available seats:");
                for (Map.Entry<Character, Map<Integer, Boolean>> row : availableSeats.entrySet()) {
                    System.out.print(row.getKey() + " ");
                    for (Map.Entry<Integer, Boolean> seat : row.getValue().entrySet()) {
                        if (!seat.getValue()) {
                            System.out.print(seat.getKey() + " ");
                        }
                    }
                    System.out.println();
                }

                System.out.print("Enter the number of seats you want to reserve: ");
                int numberOfSeats = scanner.nextInt();
                scanner.nextLine();

                List<String> seatsToReserve = new ArrayList<>();
                int counter = 0;
                while (seatsToReserve.size() < numberOfSeats && counter < numberOfSeats) {
                    System.out.print("Enter the seat " + (counter + 1) + " (e.g. A1): ");
                    String seatNumberInput = scanner.nextLine();
                    char rowInput = seatNumberInput.charAt(0);
                    int seatInput = Integer.parseInt(seatNumberInput.substring(1));
                    if (availableSeats.get(rowInput).get(seatInput)) {
                        System.out.println("This seat is already taken.");
                    } else {
                        availableSeats.get(rowInput).put(seatInput, true);
                        seatsToReserve.add(seatNumberInput);
                        counter++;
                    }
                }

                Client client = new Client(surname, name, email, phoneNumber, screening, seatsToReserve);
                clients.add(client);

                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(clientsFile))) {
                    oos.writeObject(clients);
                }

                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(screeningsFile))) {
                    oos.writeObject(screening1);
                    oos.writeObject(screening2);
                }

                System.out.println("Reservation successful.");
            }
            else if (choice == 2)
            {
                printAllReservations();
            }
            else if (choice == 3)
            {
                isRunning = false;
            }
            else
            {
                System.out.println("Invalid choice!");
            }
            
        }
    }

 private static void printAllReservations() throws Exception {
        FileInputStream fis = new FileInputStream(CLIENTS_FILE_PATH);
        ObjectInputStream ois = new ObjectInputStream(fis);
        List<Client> clients = (List<Client>) ois.readObject();
        ois.close();
        fis.close();
        for (Client client : clients) {
            System.out.println(client);
        }
    }
}
   