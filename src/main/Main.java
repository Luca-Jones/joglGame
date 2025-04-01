package main;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import networking.Client;
import networking.Server;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        JFrame frame = new JFrame("Start Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String[] options = {"Single Player", "Host a Game", "Join a Game"};
        int choice = JOptionPane.showOptionDialog(
            frame, 
            "Choose an option:", 
            "Options", 
            JOptionPane.DEFAULT_OPTION, 
            JOptionPane.INFORMATION_MESSAGE, 
            null, 
            options, 
            options[0]
        );

        frame.dispose();

        switch (choice) {
            case 0:
                Game game = new Game(true);
                game.start();
                break;
            case 1:
                Server server = new Server();
                server.start();
                break;
            case 2:
                Client client;
                try {
                    client = new Client(InetAddress.getLocalHost(), "Joe");
                    client.start();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                break;
            default:
                System.out.println("No option selected");
        }

    }
}