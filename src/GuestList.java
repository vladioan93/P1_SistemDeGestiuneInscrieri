import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GuestList {

    private Scanner scan = new Scanner(System.in);

    private int nrLocuri;
    private List<Guest> listaParticipanti = new ArrayList<>();
    private List<Guest> listaAsteptare = new ArrayList<>();

    public GuestList(int nrLocuri) {
        this.nrLocuri = nrLocuri;
    }

    public void help() {
        System.out.println("help - Afiseaza aceasta lista de comenzi\n" +
                "add - Adauga o noua persoana (inscriere)\n" +
                "check - Verifica daca o persoana este inscrisa la eveniment\n" +
                "remove - Sterge o persoana existenta din lista\n" +
                "update - Actualizeaza detaliile unei persoane\n" +
                "guests - Lista de persoane care participa la eveniment\n" +
                "waitlist - Persoanele din lista de asteptare\n" +
                "available - Numarul de locuri libere\n" +
                "guests_no - Numarul de persoane care participa la eveniment\n" +
                "waitlist_no - Numarul de persoane din lista de asteptare\n" +
                "subscribe_no - Numarul total de persoane inscrise\n" +
                "search - Cauta toti invitatii conform sirului de caractere introdus\n" +
                "quit - Inchide aplicatia");
        nextCommand();
    }

    public int add() {
        System.out.println("Se adauga o noua persoana...");
        System.out.print("Introduceti numele de familie: ");
        String lastName = scan.next();
        System.out.print("Introduceti prenumele: ");
        String firstName = scan.next();
        System.out.print("Introduceti email: ");
        String email = scan.next();
        System.out.print("Introduceti numar de telefon (format „+40733386463“): ");
        String phoneNumber = scan.next();

        Guest guest = new Guest(lastName, firstName, email, phoneNumber);

        if (listaParticipanti.contains(guest) || listaAsteptare.contains(guest)) {
            System.out.println("[" + lastName + " " + firstName + "] " +
                    "Esti deja inscris la eveniment.");
            return -1;
        } else if (checkEmail(guest) || checkLastNameFirstName(guest) || checkPhoneNumber(guest)) {
            System.out.println("[" + lastName + " " + firstName + "] " +
                    "Esti deja inscris la eveniment.");
            return -1;
        }

        if (listaParticipanti.size() < nrLocuri) {
            listaParticipanti.add(guest);
            System.out.println("[" + lastName + " " + firstName + "] " +
                    "Felicitari! Locul tau la eveniment este confirmat. Te asteptam!");
            return 0;
        }

        listaAsteptare.add(guest);
        System.out.println("[" + lastName + " " + firstName + "] " +
                "Te-ai inscris cu succes in lista de asteptare si ai primit numarul de ordine " +
                listaAsteptare.size() +
                ". Te vom notifica daca un loc devine disponibil.");
        return listaAsteptare.size() - 1;
    }

    public void check() {
        System.out.println("Selectati criteriul de cautare:\n " +
                "1. Nume si prenume\n" +
                "2. Email\n" +
                "3. Numar de telefon");
        int criteriu = scan.nextInt();
        switch (criteriu) {
            case 1:
                findAfterName();
                break;
            case 2:
                findAfterEmail();
                break;
            case 3:
                findAfterPhoneNumber();
                break;
            default:
                check();
        }
    }

    public void findAfterName() {
        System.out.print("Introduceti numele: ");
        String lastname = scan.next();
        System.out.print("Introduceti prenumele: ");
        String firstname = scan.next();

        boolean isNotFound = true;

        for (Guest guest : listaParticipanti) {
            if (lastname.toLowerCase().equals(guest.getLastName().toLowerCase()) &&
                    firstname.toLowerCase().equals(guest.getFirstName().toLowerCase())) {
                System.out.println("Utilizatorul cu numele " +
                        firstname + " " + lastname +
                        " este pe lista participantilor.");
                isNotFound = false;
                break;
            }
        }

        if (isNotFound) {
            for (Guest guest : listaAsteptare) {
                if (lastname.toLowerCase().equals(guest.getLastName().toLowerCase()) &&
                        firstname.toLowerCase().equals(guest.getFirstName().toLowerCase())) {
                    System.out.println("Utilizatorul cu numele " +
                            firstname + " " + lastname +
                            " este pe lista de asteptare.");
                    isNotFound = false;
                    break;
                }
            }
        }

        if (isNotFound) {
            System.out.println("Persoana " +
                    lastname + " " + firstname +
                    " nu este inscrisa la eveniment.");
        }
    }

    public void findAfterEmail() {
        System.out.print("Introduceti adresa de email: ");
        String email = scan.next();

        boolean isNotFound = true;

        for (Guest guest : listaParticipanti) {
            if (email.toLowerCase().equals(guest.getEmail().toLowerCase())) {
                System.out.println("Utilizatorul cu adresa de email " + email +
                        " este pe lista participantilor.");
                isNotFound = false;
                break;
            }
        }

        if (isNotFound) {
            for (Guest guest : listaAsteptare) {
                if (email.toLowerCase().equals(guest.getEmail().toLowerCase())) {
                    System.out.println("Utilizatorul cu adresa de mail " +
                            email + " este pe lista de asteptare.");
                    isNotFound = false;
                    break;
                }
            }
        }

        if (isNotFound) {
            System.out.println("Persoana cu adresa de email " + email +
                    " nu este inscrisa la eveniment.");
        }
    }

    public void findAfterPhoneNumber() {
        System.out.print("Introduceti numarul de telefon: ");
        String phoneNumber = scan.next();

        boolean isNotFound = true;

        for (Guest guest : listaParticipanti) {
            if (phoneNumber.equals(guest.getPhoneNumber())) {
                System.out.println("Utilizatorul cu numarul de telefon " + phoneNumber +
                        " este pe lista participantilor.");
                isNotFound = false;
                break;
            }
        }

        if (isNotFound) {
            for (Guest guest : listaAsteptare) {
                if (phoneNumber.equals(guest.getPhoneNumber())) {
                    System.out.println("Utilizatorul cu adresa de mail " +
                            phoneNumber + " este pe lista de asteptare.");
                    isNotFound = false;
                    break;
                }
            }
        }

        if (isNotFound) {
            System.out.println("Persoana cu numarul de telefon " + phoneNumber +
                    " nu este inscrisa la eveniment.");
        }
    }

    public boolean remove() {
        System.out.println("Se sterge o persoana din lista...");
        System.out.println("Alege modul de autentificare, tastand:\n" +
                "\"1\" - Nume si prenume\n" +
                "\"2\" - Email\n" +
                "\"3\" - Numar de telefon (format \"+40733386463\")");
        int option = scan.nextInt();

        switch (option) {
            case 1:
                removeByName();
                break;
            case 2:
                removeByEmail();
                break;
            case 3:
                removeByPhoneNumber();
                break;
            default:
                remove();
        }
        return false;
    }

    private boolean removeByName() {
        System.out.print("Introduceti numele: ");
        String lastname = scan.next();
        System.out.print("Introduceti prenumele: ");
        String firstname = scan.next();

        for (int i = 0; i < listaParticipanti.size(); i++) {
            if (lastname.toLowerCase().equals(listaParticipanti.get(i).getLastName().toLowerCase()) &&
                    firstname.toLowerCase().equals(listaParticipanti.get(i).getFirstName().toLowerCase())) {
                listaParticipanti.remove(i);
                if (!listaAsteptare.isEmpty()) {
                    listaParticipanti.add(listaAsteptare.get(0));
                    listaAsteptare.remove(0);
                }
                System.out.println("Stergerea persoanei s-a realizat cu succes.");
                return true;
            }
        }

        for (int i = 0; i < listaAsteptare.size(); i++) {
            if (lastname.toLowerCase().equals(listaAsteptare.get(i).getLastName().toLowerCase()) &&
                    firstname.toLowerCase().equals(listaAsteptare.get(i).getFirstName().toLowerCase())) {
                listaAsteptare.remove(i);
                System.out.println("Stergerea persoanei s-a realizat cu succes.");
                return true;
            }
        }
        return false;
    }

    private boolean removeByEmail() {
        System.out.print("Introduceti adresa de email: ");
        String email = scan.next();

        for (int i = 0; i < listaParticipanti.size(); i++) {
            if (email.toLowerCase().equals(listaParticipanti.get(i).getEmail().toLowerCase())) {
                listaParticipanti.remove(i);
                if (!listaAsteptare.isEmpty()) {
                    listaParticipanti.add(listaAsteptare.get(0));
                    listaAsteptare.remove(0);
                }
                System.out.println("Stergerea persoanei s-a realizat cu succes.");
                return true;
            }
        }

        for (int i = 0; i < listaAsteptare.size(); i++) {
            if (email.toLowerCase().equals(listaAsteptare.get(i).getEmail().toLowerCase())) {
                listaAsteptare.remove(i);
                System.out.println("Stergerea persoanei s-a realizat cu succes.");
                return true;
            }
        }
        return false;
    }

    private boolean removeByPhoneNumber() {
        System.out.print("Introduceti numarul de telefon: ");
        String phoneNumber = scan.next();

        for (int i = 0; i < listaParticipanti.size(); i++) {
            if (phoneNumber.equals(listaParticipanti.get(i).getPhoneNumber())) {
                listaParticipanti.remove(i);
                if (!listaAsteptare.isEmpty()) {
                    listaParticipanti.add(listaAsteptare.get(0));
                    listaAsteptare.remove(0);
                }
                System.out.println("Stergerea persoanei s-a realizat cu succes.");
                return true;
            }
        }

        for (int i = 0; i < listaAsteptare.size(); i++) {
            if (phoneNumber.equals(listaAsteptare.get(i).getPhoneNumber())) {
                listaAsteptare.remove(i);
                System.out.println("Stergerea persoanei s-a realizat cu succes.");
                return true;
            }
        }
        return false;
    }

    public void guests() {
        if (listaParticipanti.isEmpty()) {
            System.out.println("Lista de participanti este goala...");
        }

        for (int i = 0; i < listaParticipanti.size(); i++) {
            Guest guest = listaParticipanti.get(i);
            System.out.println(i + 1 + ". Nume: " + guest.getLastName() + " " +
                    guest.getFirstName() + ", Email: " + guest.getEmail() + ", Telefon: " +
                    guest.getPhoneNumber());
        }
    }

    public void waitlist() {
        if (listaAsteptare.isEmpty()) {
            System.out.println("Lista de asteptare este goala...");
        }

        for (int i = 0; i < listaAsteptare.size(); i++) {
            Guest guest = listaAsteptare.get(i);
            System.out.println(i + 1 + ". Nume: " + guest.getLastName() + " " +
                    guest.getFirstName() + ", Email: " + guest.getEmail() + ", Telefon: " +
                    guest.getPhoneNumber());
        }
    }

    public void available() {
        System.out.println("Numarul de locuri ramase: " + (this.nrLocuri - this.listaParticipanti.size()));
    }

    public void guests_no() {
        System.out.println("Numarul de participanti: " + this.listaParticipanti.size());
    }

    public void waitlist_no() {
        System.out.println("Dimensiunea listei de asteptare: " + this.listaAsteptare.size());
    }

    public void subscribe_no() {
        System.out.println("Numarul total de persoane: " + (this.listaAsteptare.size() +
                this.listaParticipanti.size()));
    }

    public void search() {
        System.out.println("Type the search text: ");
        String search = scan.next();

        for(Guest guest : listaParticipanti){
            if (guest.getLastName().contains(search)) {
                System.out.println("\"" + search + "\" found in " + guest.toString());
                continue;
            }
            if (guest.getFirstName().contains(search)) {
                System.out.println("\"" + search + "\" found in " + guest.toString());
                continue;
            }
            if (guest.getEmail().contains(search)) {
                System.out.println("\"" + search + "\" found in " + guest.toString());
                continue;
            }
            if (guest.getPhoneNumber().contains(search)) {
                System.out.println("\"" + search + "\" found in " + guest.toString());
            }
        }

        for(Guest guest : listaAsteptare){
            if (guest.getLastName().contains(search)) {
                System.out.println("\"" + search + "\" found in " + guest.toString());
                continue;
            }
            if (guest.getFirstName().contains(search)) {
                System.out.println("\"" + search + "\" found in " + guest.toString());
                continue;
            }
            if (guest.getEmail().contains(search)) {
                System.out.println("\"" + search + "\" found in " + guest.toString());
                continue;
            }
            if (guest.getPhoneNumber().contains(search)) {
                System.out.println("\"" + search + "\" found in " + guest.toString());
            }
        }
    }

    public void update() {
        System.out.println("Alege modul de autentificare, tastand:\n" +
                "\"1\" - Nume si prenume\n" +
                "\"2\" - Email\n" +
                "\"3\" - Numar de telefon (format \"+40733386463\")");
        int option = scan.nextInt();
        switch (option) {
            case 1:
                updateByName();
                break;
            case 2:
                updateByEmail();
                break;
            case 3:
                updateByPhoneNumber();
                break;
            default:
                update();
        }
    }

    public void updateMethod(Guest guest) {
        System.out.println("Alege campul de actualizat, tastand:\n" +
                "\"1\" - Nume\n" +
                "\"2\" - Prenume\n" +
                "\"3\" - Email\n" +
                "\"4\" - Numar de telefon (format \"+40733386463\")");
        int option = scan.nextInt();

        switch (option) {
            case 1:
                System.out.println("Introduceti numele de familie: ");
                String s1 = scan.next();
                guest.setLastName(s1);
                break;
            case 2:
                System.out.println("Introduceti prenumele: ");
                String s2 = scan.next();
                guest.setFirstName(s2);
                break;
            case 3:
                System.out.println("Introduceti noua adresa de email: ");
                String s3 = scan.next();
                guest.setEmail(s3);
                break;
            case 4:
                System.out.println("Introduceti noul numar de telefon: ");
                String s4 = scan.next();
                guest.setPhoneNumber(s4);
                break;
            default:
                System.out.println("Optiunea nu exista");
        }
    }

    public void updateByName() {
        System.out.print("Introduceti numele: ");
        String lastname = scan.next();
        System.out.print("Introduceti prenumele: ");
        String firstname = scan.next();

        boolean toBeUpdated = true;

        for (Guest guest : listaParticipanti) {
            if (lastname.toLowerCase().equals(guest.getLastName().toLowerCase()) &&
                    firstname.toLowerCase().equals(guest.getFirstName().toLowerCase())) {
                updateMethod(guest);
                toBeUpdated = false;
            }
        }
        if (toBeUpdated) {
            for (Guest guest : listaAsteptare) {
                if (lastname.toLowerCase().equals(guest.getLastName().toLowerCase()) &&
                        firstname.toLowerCase().equals(guest.getFirstName().toLowerCase())) {
                    updateMethod(guest);
                    toBeUpdated = false;
                }
            }
        }
        if (toBeUpdated) {
            System.out.println("Nu s-a realizat update-ul...");
        }
    }

    public void updateByEmail() {
        System.out.print("Introduceti adresa de email: ");
        String email = scan.next();

        boolean toBeUpdated = true;

        for (Guest guest : listaParticipanti) {
            if (email.toLowerCase().equals(guest.getEmail().toLowerCase())) {
                updateMethod(guest);
                toBeUpdated = false;
            }
        }
        if (toBeUpdated) {
            for (Guest guest : listaAsteptare) {
                if (email.toLowerCase().equals(guest.getEmail().toLowerCase())) {
                    updateMethod(guest);
                    toBeUpdated = false;
                }
            }
        }
        if (toBeUpdated) {
            System.out.println("Nu s-a realizat update-ul...");
        }
    }

    public void updateByPhoneNumber() {
        System.out.print("Introduceti numarul de telefon: ");
        String phoneNumber = scan.next();

        boolean toBeUpdated = true;

        for (Guest guest : listaParticipanti) {
            if (phoneNumber.equals(guest.getPhoneNumber())) {
                updateMethod(guest);
                toBeUpdated = false;
            }
        }
        if (toBeUpdated) {
            for (Guest guest : listaAsteptare) {
                if (phoneNumber.equals(guest.getPhoneNumber())) {
                    updateMethod(guest);
                    toBeUpdated = false;
                }
            }
        }
        if (toBeUpdated) {
            System.out.println("Nu s-a realizat update-ul...");
        }
    }

    public void quit() {
        System.out.println("Aplicatia se inchide...");
    }

    private boolean checkEmail(Guest guest) {
        for (Guest value : listaParticipanti) {
            if (guest.getEmail().equals(value.getEmail())) {
                return true;
            }
        }
        for (Guest value : listaAsteptare) {
            if (guest.getEmail().equals(value.getEmail())) {
                return true;
            }
        }
        return false;
    }

    private boolean checkLastNameFirstName(Guest guest) {
        for (Guest value : listaParticipanti) {
            if (guest.getLastName().equals(value.getLastName()) &&
                    guest.getFirstName().equals(value.getFirstName())) {
                return true;
            }
        }
        for (Guest value : listaAsteptare) {
            if (guest.getLastName().equals(value.getLastName()) &&
                    guest.getFirstName().equals(value.getFirstName())) {
                return true;
            }
        }
        return false;
    }

    private boolean checkPhoneNumber(Guest guest) {
        for (Guest value : listaParticipanti) {
            if (guest.getPhoneNumber().equals(value.getPhoneNumber())) {
                return true;
            }
        }
        for (Guest value : listaAsteptare) {
            if (guest.getPhoneNumber().equals(value.getPhoneNumber())) {
                return true;
            }
        }
        return false;
    }

    public void nextCommand() {
        System.out.println("\n\nAsteapta comanda: (help - Afiseaza lista de comenzi)");
        String command = scan.next();

        switch (command) {
            case "help":
                help();
                nextCommand();
            case "add":
                add();
                nextCommand();
            case "check":
                check();
                nextCommand();
            case "remove":
                remove();
                nextCommand();
            case "update":
                update();
                nextCommand();
            case "guests":
                guests();
                nextCommand();
            case "waitlist":
                waitlist();
                nextCommand();
            case "available":
                available();
                nextCommand();
            case "guests_no":
                guests_no();
                nextCommand();
            case "waitlist_no":
                waitlist_no();
                nextCommand();
            case "subscribe_no":
                subscribe_no();
                nextCommand();
            case "search":
                search();
                nextCommand();
            case "quit":
                quit();
                break;
            default:
                System.out.println("Ati introdus o comanda gresita");
                nextCommand();
        }
    }
}
