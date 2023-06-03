package example.managers;

import example.myCommands.*;
import example.ripManager.SqlConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);
    private final Map<String, Command> commandMap;
    private final int port;
    MyLinkedList myLinkedList;

    public Server(int port, MyLinkedList myLinkedList) {
        this.port = port;
        this.commandMap = new HashMap<>();
        this.myLinkedList = myLinkedList;
        register(new AddCommand(myLinkedList));
        register(new AddIfMaxCommand(myLinkedList));
        register(new AddIfMinCommand(myLinkedList));
        register(new ClearCommand(myLinkedList));
        register(new HelpCommand(myLinkedList));
        register(new InfoCommand(myLinkedList));
        register(new ExecuteScriptCommand(myLinkedList));
        register(new PrintFieldDescendingCategoryCommand(myLinkedList));
        register(new PrintFieldDescendingMeleeWeaponCommand(myLinkedList));
        register(new PrintUniqueHealthCommand(myLinkedList));
        register(new RemoveByIdCommand(myLinkedList));
        register(new RemoveLowerCommand(myLinkedList));
        register(new ShowCommand(myLinkedList));
        register(new UpdateByIdCommand(myLinkedList));
        register(new AuthenticationCommand(myLinkedList));
        register(new TableCommand(myLinkedList));
        register(new ChatCommand(myLinkedList));
    }

    private void register(Command command) {
        commandMap.put(command.getName(), command);
    }

    public void start() {
        try {
            LOGGER.info("server started on port " + port + "...");
            DatagramChannel channel = DatagramChannel.open();
            channel.bind(new InetSocketAddress(port));
            channel.configureBlocking(false);

            ByteBuffer buffer = ByteBuffer.allocate(65536);
            ExecutorService readExecutor = Executors.newCachedThreadPool();
            ExecutorService handleExecutor = Executors.newFixedThreadPool(10);
            ExecutorService sendExecutor = Executors.newFixedThreadPool(10);

            Thread thread = new Thread(() -> {
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    if (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        if (line.equals("exit")) {
                            new SqlConnection().saveCollectionToDatabase(myLinkedList);
                            LOGGER.info("Server stopped");
                            System.exit(0);
                        }
                    }
                }
            });
            thread.start();

            while (true) {
                buffer.clear();
                SocketAddress address = channel.receive(buffer);
                if (address != null) {
                    readExecutor.execute(() -> {
                        LOGGER.info("received request from " + address);
                        CommandRequest commandRequest = null;
                        try {
                            buffer.flip();
                            ByteArrayInputStream in = new ByteArrayInputStream(buffer.array());
                            ObjectInputStream is = new ObjectInputStream(in);
                            commandRequest = (CommandRequest) is.readObject();
                            is.close();
                        } catch (IOException e) {
                            LOGGER.error("IOException: " + e.getMessage());
                        } catch (ClassNotFoundException e) {
                            LOGGER.error("ClassNotFoundException: " + e.getMessage());
                        }

                        if (commandRequest != null) {
                            CommandRequest finalCommandRequest = commandRequest;
                            handleExecutor.execute(() -> {
                                CommandRequestHandler commandRequestHandler = new CommandRequestHandler();
                                Product product = commandRequestHandler.handle(finalCommandRequest, commandMap);
                                sendExecutor.execute(() -> {
                                    try {
                                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                                        ObjectOutputStream os = new ObjectOutputStream(outputStream);
                                        os.writeObject(product);
                                        byte[] data = outputStream.toByteArray();
                                        DatagramPacket packet = new DatagramPacket(data, data.length, address);
                                        channel.send(ByteBuffer.wrap(packet.getData()), address);
                                        os.close();
                                        LOGGER.info("sent response to " + address);
                                    } catch (IOException e) {
                                        LOGGER.error("IOException: " + e.getMessage());
                                    }
                                });
                            });
                        }
                    });
                }
            }
        } catch (SocketException e) {
            LOGGER.error("SocketException: " + e.getMessage());
        } catch (IOException e) {
            LOGGER.error("IOException: " + e.getMessage());
        }
    }
}
