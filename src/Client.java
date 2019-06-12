import com.google.gson.JsonSyntaxException;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import com.google.gson.Gson;

import java.util.List;
import java.util.Scanner;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


public class Client {
    public static void main(String[] args){
        String email = new String();
        String password = new String();
        try {
            InetAddress address = InetAddress.getByName("localhost");
            DatagramSocket socket = new DatagramSocket();

            byte[] buffer = new byte[3000];
            byte[] bufferResponce = new byte[3000];
            int port = 6879;
            Scanner scanner = new Scanner(System.in);
            String line = new String();
            do{
                line = scanner.nextLine();
                /*if (line.split(" ")[0].equals("import")){
                    try {
                        String path = new String(line.split(" ")[1]);
                        File importedFile = new File(path);
                        if (!(importedFile.canRead())) throw new SecurityException("Данный файл не может быть прочитан.");
                        if (!(importedFile.exists())) throw new FileNotFoundException("Файла по указанному пути не существует.");
                        if (!(importedFile.canRead())) throw new SecurityException("Данный файл не может быть прочитан.");
                        //date = new Date(importedFile.lastModified());
                        Scanner importedFileText = new Scanner(System.in);
                        importedFileText = new Scanner(importedFile);
                        String importedCollectionText = new String();
                        while (importedFileText.hasNextLine()) {
                            importedCollectionText = importedCollectionText.concat(importedFileText.nextLine());
                        }
                        importedFileText.close();
                        line = "import " + email + " " + password + " " + importedCollectionText;
                    } catch (FileNotFoundException mes) {
                        System.out.println(mes);
                    } catch (SecurityException mes) {
                        System.out.println(mes);
                    }
                    catch(JsonSyntaxException mes){
                        System.out.println("Неверный формат данных");
                    }
                }
                if (line.split(" ")[0].equals("auth")){
                    email = line.split(" ")[1];
                    password = line.split(" ")[2];
                }*/
                switch (line.split(" ")[0]){
                    case ("import"):{
                        try {
                            String path = new String(line.split(" ")[1]);
                            System.out.println(path);
                            File importedFile = new File(path);
                            if (!(importedFile.canRead())) throw new SecurityException("Данный файл не может быть прочитан.");
                            if (!(importedFile.exists())) throw new FileNotFoundException("Файла по указанному пути не существует.");
                            if (!(importedFile.canRead())) throw new SecurityException("Данный файл не может быть прочитан.");
                            //date = new Date(importedFile.lastModified());
                            Scanner importedFileText = new Scanner(importedFile);
                            String importedCollectionText = new String();
                            while (importedFileText.hasNextLine()) {
                                importedCollectionText = importedCollectionText.concat(importedFileText.nextLine());
                            }
                            importedFileText.close();
                            line = "import " + email + " " + password + " " + importedCollectionText;
                        } catch (FileNotFoundException mes) {
                            System.out.println(mes);
                        } catch (SecurityException mes) {
                            System.out.println(mes);
                        }
                        catch(JsonSyntaxException mes){
                            System.out.println("Неверный формат данных");
                        }
                    }
                    break;
                    case ("auth"):
                        try {
                            email = line.split(" ")[1];
                            password = line.split(" ")[2];
                        }
                        catch (Exception e){
                            System.out.println("Введено недостаточно данных");
                        }
                        break;
                    case("insert"):
                    case("remove_greater_key"):
                    case ("remove"):{
                        try {
                            line = line.split(" ")[0] + " " + email + " " + password + " " + line.split(" ", 2)[1];
                        }
                        catch (Exception e){
                            System.out.println("Введено недостаточно данных");
                        }
                    }
                    break;
                    case("info"):
                    case("show"):{
                        line = line.split(" ")[0] + " " + email + " " + password;
                    }
                }
                Sendi sendi = new Sendi();
                sendi.fromClient = line;
                ByteArrayOutputStream by = new ByteArrayOutputStream();
                ObjectOutput out = null;
                try {
                    out = new ObjectOutputStream(by);
                    out.writeObject(sendi);
                    out.flush();
                    out.close();
                }
                catch (IOException ex) {
                }
                //buffer = line.getBytes();
                buffer = by.toByteArray();
                DatagramPacket sending = new DatagramPacket(buffer,buffer.length,address,port);
                DatagramPacket response = new DatagramPacket(bufferResponce, bufferResponce.length);
                socket.send(sending);
                socket.setSoTimeout(3000);

                    try {
                        socket.receive(response);
                        ObjectInput in = null;
                        Sendi sendiReceive;
                        ByteArrayInputStream bis = new ByteArrayInputStream(bufferResponce);

                        in = new ObjectInputStream(bis);
                        sendiReceive = (Sendi) in.readObject();
                        System.out.println(sendiReceive.fromServer);
                    }
                    catch (SocketTimeoutException e) {
                        // timeout exception.
                        System.out.println("Ответ от сервера не получен");
                    }


            }while(!line.equalsIgnoreCase("exit"));
        }catch (Exception o){

        }
    }
    public String add(String key ,String name, int weight, int x, int y, String email, String password){
        String result = "insert " + email + " " + password + " {\"key\":\""+ key + "\"}  {\"name\":\"" + name + "\",\"weight\":" + weight + ",\"x\":\"" + x + "\",\"y\":\"" + y + "\"}";
        System.out.println(result);
        Sendi sendi = new Sendi();
        sendi.fromClient = result;
        ByteArrayOutputStream by = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] buffer = new byte[3000];
        byte[] bufferResponce = new byte[3000];
        try {
            out = new ObjectOutputStream(by);
            out.writeObject(sendi);
            out.flush();
            out.close();

        buffer = by.toByteArray();
        DatagramPacket sending = new DatagramPacket(buffer,buffer.length,Settings.address,Settings.port);
        DatagramPacket response = new DatagramPacket(bufferResponce, bufferResponce.length);
        Settings.socket.send(sending);
        Settings.socket.setSoTimeout(3000);
            ObjectInput in = null;
            Sendi sendiReceive;
            ByteArrayInputStream bis = new ByteArrayInputStream(bufferResponce);

            in = new ObjectInputStream(bis);
            sendiReceive = (Sendi) in.readObject();
            return sendiReceive.fromServer;
        }
        catch (SocketTimeoutException e) {
            // timeout exception.
            return ("Ответ от сервера не получен");
        }
        catch (Exception e) {
            return ("Ошибка");
        }
    }

    public String remove(int id, String email, String password){
        String result = "remove " + email + " " + password + " " + id;
        System.out.println(result);
        Sendi sendi = new Sendi();
        sendi.fromClient = result;
        ByteArrayOutputStream by = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] buffer = new byte[3000];
        byte[] bufferResponce = new byte[3000];
        try {
            out = new ObjectOutputStream(by);
            out.writeObject(sendi);
            out.flush();
            out.close();

            buffer = by.toByteArray();
            DatagramPacket sending = new DatagramPacket(buffer,buffer.length,Settings.address,Settings.port);
            DatagramPacket response = new DatagramPacket(bufferResponce, bufferResponce.length);
            Settings.socket.send(sending);
            Settings.socket.setSoTimeout(3000);
            ObjectInput in = null;
            Sendi sendiReceive;
            ByteArrayInputStream bis = new ByteArrayInputStream(bufferResponce);

            in = new ObjectInputStream(bis);
            sendiReceive = (Sendi) in.readObject();
            return sendiReceive.fromServer;
        }
        catch (SocketTimeoutException e) {
            // timeout exception.
            return ("Ответ от сервера не получен");
        }
        catch (Exception e) {
            return ("Ошибка");
        }
    }

    public String info(String email, String password){
        String result = "info " + email + " " + password;
        System.out.println(result);
        Sendi sendi = new Sendi();
        sendi.fromClient = result;
        ByteArrayOutputStream by = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] buffer = new byte[3000];
        byte[] bufferResponce = new byte[3000];
        try {
            out = new ObjectOutputStream(by);
            out.writeObject(sendi);
            out.flush();
            out.close();

            buffer = by.toByteArray();
            DatagramPacket sending = new DatagramPacket(buffer,buffer.length,Settings.address,Settings.port);
            DatagramPacket response = new DatagramPacket(bufferResponce, bufferResponce.length);
            Settings.socket.send(sending);
            Settings.socket.setSoTimeout(3000);
            ObjectInput in = null;
            Sendi sendiReceive;
            ByteArrayInputStream bis = new ByteArrayInputStream(bufferResponce);

            in = new ObjectInputStream(bis);
            sendiReceive = (Sendi) in.readObject();
            return sendiReceive.fromServer;
        }
        catch (SocketTimeoutException e) {
            // timeout exception.
            return ("Ответ от сервера не получен");
        }
        catch (Exception e) {
            return ("Ошибка");
        }
    }

    public String removeGreater(String key, String email, String password){
        String result = "remove_greater_key " + email + " " + password + " {\"key\":\"" + key + "\"}";
        System.out.println(result);
        Sendi sendi = new Sendi();
        sendi.fromClient = result;
        ByteArrayOutputStream by = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] buffer = new byte[3000];
        byte[] bufferResponce = new byte[3000];
        try {
            out = new ObjectOutputStream(by);
            out.writeObject(sendi);
            out.flush();
            out.close();

            buffer = by.toByteArray();
            DatagramPacket sending = new DatagramPacket(buffer,buffer.length,Settings.address,Settings.port);
            DatagramPacket response = new DatagramPacket(bufferResponce, bufferResponce.length);
            Settings.socket.send(sending);
            Settings.socket.setSoTimeout(3000);
            ObjectInput in = null;
            Sendi sendiReceive;
            ByteArrayInputStream bis = new ByteArrayInputStream(bufferResponce);

            in = new ObjectInputStream(bis);
            sendiReceive = (Sendi) in.readObject();
            return sendiReceive.fromServer;
        }
        catch (SocketTimeoutException e) {
            // timeout exception.
            return ("Ответ от сервера не получен");
        }
        catch (Exception e) {
            return ("Ошибка");
        }
    }

    public List<Human> show(String email, String password){
        List<Human> a = new List<Human>;
        String result = "show " + email + " " + password;
        System.out.println(result);
        Sendi sendi = new Sendi();
        sendi.fromClient = result;
        ByteArrayOutputStream by = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] buffer = new byte[3000];
        byte[] bufferResponce = new byte[3000];
        try {
            out = new ObjectOutputStream(by);
            out.writeObject(sendi);
            out.flush();
            out.close();

            buffer = by.toByteArray();
            DatagramPacket sending = new DatagramPacket(buffer,buffer.length,Settings.address,Settings.port);
            DatagramPacket response = new DatagramPacket(bufferResponce, bufferResponce.length);
            Settings.socket.send(sending);
            Settings.socket.setSoTimeout(3000);
            ObjectInput in = null;
            Sendi sendiReceive;
            ByteArrayInputStream bis = new ByteArrayInputStream(bufferResponce);

            in = new ObjectInputStream(bis);
            sendiReceive = (Sendi) in.readObject();
            return sendiReceive.humanList;
        }
        catch (SocketTimeoutException e) {
            // timeout exception.
            return a;
        }
        catch (Exception e) {
            return a;
        }
    }
}
