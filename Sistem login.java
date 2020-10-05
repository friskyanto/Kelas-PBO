import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner inputUser = new Scanner(System.in);

        String user, pass;

        for (int i = 1; i <= 3; i++) {

            System.out.print("Masukan username: ");
            user = inputUser.next();

            System.out.print("Masukan password:");
            pass = inputUser.next();

            if (user.equals("ibnu") && (pass.equals("cobra"))) {
                System.out.println("Selamat datang ibnu cobra");
                return;


            } else {
                System.out.println("username dan passowrd yang anda masukan salah ke-" +i);

            }
        }

        System.out.println("percobaam sudah sering dilakukan, silahkan cek kembali data anda");
    }
}