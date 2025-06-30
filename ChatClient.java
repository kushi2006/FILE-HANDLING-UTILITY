package target.Javafiles.MultithreadedChatApp;
import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 1234);
            new Thread(new Read(socket)).start();
            new Thread(new Write(socket)).start();
        } catch (IOException e) {
            System.out.println("Unable to connect to server.");
        }
    }

    static class Read implements Runnable {
        private BufferedReader in;

        public Read(Socket socket) {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            String msg;
            try {
                while ((msg = in.readLine()) != null) {
                    System.out.println(msg);
                }
            } catch (IOException e) {
                System.out.println("Connection closed.");
            }
        }
    }

    static class Write implements Runnable {
        private PrintWriter out;
        private BufferedReader input;

        public Write(Socket socket) {
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
                input = new BufferedReader(new InputStreamReader(System.in));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            String msg;
            try {
                while ((msg = input.readLine()) != null) {
                    out.println(msg);
                    if (msg.equalsIgnoreCase("exit")) break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

