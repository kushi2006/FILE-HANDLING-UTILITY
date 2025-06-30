package target.Javafiles.MultithreadedChatApp;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final int PORT = 1234;
    private static Set<ClientHandler> clients = new HashSet<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Chat Server started on port " + PORT);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("New client connected.");
            ClientHandler handler = new ClientHandler(clientSocket);
            clients.add(handler);
            new Thread(handler).start();
        }
    }

    static void broadcast(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.send(message);
            }
        }
    }

    static void remove(ClientHandler client) {
        clients.remove(client);
    }

    static class ClientHandler implements Runnable {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String name;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                out.println("Enter your name: ");
                name = in.readLine();

                if (name == null) return;

                broadcast(name + " has joined the chat.", this);
                String msg;
                while ((msg = in.readLine()) != null) {
                    if (msg.equalsIgnoreCase("exit")) break;
                    broadcast(name + ": " + msg, this);
                }
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            } finally {
                try {
                    broadcast(name + " has left the chat.", this);
                    remove(this);
                    socket.close();
                } catch (IOException e) {}
            }
        }

        void send(String message) {
            out.println(message);
        }
    }
}
