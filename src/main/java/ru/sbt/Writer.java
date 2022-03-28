package ru.sbt;

import java.util.Date;
import org.apache.ignite.Ignition;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.configuration.BinaryConfiguration;
import org.apache.ignite.configuration.ClientConfiguration;

import static java.util.Calendar.FEBRUARY;
import static java.util.Calendar.JULY;
import static java.util.Calendar.MARCH;
import static java.util.Calendar.MAY;

/**
 * This class connects to Ignite server node and writes some data to it.
 */
public class Writer {
    public static void main(String[] args) {
        System.out.println("[WriterMain] Starting client.");

        ClientConfiguration cfg = new ClientConfiguration().setAddresses("127.0.0.1:10800")
            .setBinaryConfiguration(new BinaryConfiguration().setCompactFooter(true));

        try (IgniteClient client = Ignition.startClient(cfg)) {
            ClientCache<Integer, User> cache = client.getOrCreateCache("user-cache");

            Department management = new Department(1, "Management");

            cache.put(1, new User("Big boss", management, new Date(1960, FEBRUARY, 01)));
            cache.put(2, new User("Vice president", management, new Date(1971, MARCH, 01)));
            cache.put(3, new User("Young and furious", management, new Date(2000, JULY, 05)));

            Department movieHeroes = new Department(2, "Movie");

            cache.put(4, new User("Sarah Connor", management, new Date(1965, MAY, 01)));
            cache.put(5, new User("John Connor", management, new Date(1985, FEBRUARY, 28)));
            cache.put(6, new User("Kyle Reese", management, new Date(2004, JULY, 05)));
        }

        System.out.println("[WriterMain] Data written.");
    }
}
