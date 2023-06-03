package example.managers;


import example.myCommands.*;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

public class Invoker {
    final Map<String, Command> commandMap = new HashMap<>();

    public Invoker() {
        register(new AddCommand());
        register(new AddIfMaxCommand());
        register(new AddIfMinCommand());
        register(new ClearCommand());
        register(new ExecuteScriptCommand());
        register(new ExitCommand());
        register(new HelpCommand());
        register(new InfoCommand());
        register(new PrintFieldDescendingCategoryCommand());
        register(new PrintFieldDescendingMeleeWeaponCommand());
        register(new PrintUniqueHealthCommand());
        register(new RemoveByIdCommand());
        register(new RemoveLowerCommand());
        register(new ShowCommand());
        register(new UpdateByIdCommand());
        register(new AuthenticationCommand());
        register(new TableCommand());
        register(new UpdateCommand());
        register(new ChatCommand());
    }

    void register(Command command) {
        commandMap.put(command.getName(), command);
    }

    public Product executeCommand(String name, Argument argument) {
        try (DatagramSocket socket = new DatagramSocket()){
            int retriesLeft = 3;
            InetAddress ip = InetAddress.getLocalHost();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(outputStream);
            if (!commandMap.containsKey(name)) {
                return new Product("No such command " + name, false);
            }
            PreCommandRequest preCommandRequest = commandMap.get(name).execute(argument);
            CommandRequest commandRequest = new CommandRequest(
                    preCommandRequest.commandName(),
                    preCommandRequest.argument(),
                    argument.login(),
                    argument.password());
            os.writeObject(commandRequest);
            byte[] data = outputStream.toByteArray();
            DatagramPacket dp = new DatagramPacket(data, data.length, ip, 1935);

            while (retriesLeft > 0) {
                socket.send(dp);
                byte[] buffer = new byte[65536];
                DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
                try {
                    socket.receive(incoming);
                    byte[] data2 = incoming.getData();
                    ByteArrayInputStream in = new ByteArrayInputStream(data2);
                    ObjectInputStream is = new ObjectInputStream(in);
                    return (Product) is.readObject();
                } catch (SocketTimeoutException e) {
                    // сервер не ответил в течение заданного времени
                    System.out.println("Server is not available. Retrying...");
                    retriesLeft--;
                    Thread.sleep(5000); // задержка между повторными попытками отправки запроса
                }
            }

        } catch (CommandExecutionException | IOException | ClassNotFoundException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
        return new Product("Error", false);
    }
}