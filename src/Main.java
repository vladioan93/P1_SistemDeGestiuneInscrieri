import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int nrLocuri = -1;

        while(nrLocuri<0) {
            System.out.print("Bun venit! Introduceti numarul de locuri disponibile: ");
            nrLocuri = scan.nextInt();
        }

        GuestList guestList = new GuestList(nrLocuri);

        guestList.nextCommand();
    }
}
