package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        User user1 = new User("Nick", "Stefan", (byte) 44);
        User user2 = new User("Steve", "Stefan", (byte) 32);
        User user3 = new User("Jack", "Stefan", (byte) 54);
        User user4 = new User("John", "Stefan", (byte) 12);

        UserService service = new UserServiceImpl();

        service.createUsersTable();
        service.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
        service.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
        service.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
        service.saveUser(user4.getName(), user4.getLastName(), user4.getAge());


        System.out.println(service.getAllUsers());

        service.removeUserById(3);

        service.cleanUsersTable();

        service.dropUsersTable();

        Util.getSessionFactory().close();






    }
}
