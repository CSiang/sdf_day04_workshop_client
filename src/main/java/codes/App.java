package codes;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public final class App {

    public static void main(String[] args) throws UnknownHostException, IOException {

        Socket soc = new Socket("localhost",7000);

        Console con = System.console();

        String readInput ="", msgReceived = "";

        try (OutputStream os = soc.getOutputStream()) {
            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);

            try (InputStream is = soc.getInputStream()){
                BufferedInputStream bis = new BufferedInputStream(is);
                DataInputStream dis = new DataInputStream(bis);

            while(!(readInput.equalsIgnoreCase("close"))) {
                readInput = con.readLine("Enter a command: ");
                dos.writeUTF(readInput);
                dos.flush();

                msgReceived = dis.readUTF();
                System.out.println("Your cookie is " + msgReceived);
            }

                dis.close();
                bis.close();
                is.close();

            } catch (EOFException ex) { // for inputstream
                System.out.println("Client shut down.");
                soc.close();
            }

        } catch (EOFException ex) {
            System.out.println("Client shut down.");
            soc.close();
        }

    }
}
