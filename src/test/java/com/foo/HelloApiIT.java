package com.foo;

import com.foo.cayenne.Game;
import com.foo.cayenne.Team;
import io.bootique.BQRuntime;
import io.bootique.cayenne.test.CayenneTestDataManager;
import io.bootique.jetty.test.junit.JettyTestFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class HelloApiIT {

    @Rule
    public JettyTestFactory testFactory = new JettyTestFactory();

    @Before
    public void before() {

        BQRuntime runtime = testFactory.app("-c", "demo-test.yml")
                .autoLoadModules()
                .start();

        CayenneTestDataManager testDataManager = new CayenneTestDataManager(runtime, true, Team.class, Game.class);

        testDataManager.getTable(Team.class)
                .insertColumns("id", "name")
                .values(1, "New Jersey Devils")
                .values(2, "New York Rangers")
                .values(3, "Detroit Red Wings")
                .values(4, "LA Kings")
                .exec();

        testDataManager.getTable(Game.class)
                .insertColumns("id", "home_team_id", "visiting_team_id", "date_time", "arena")
                .values(1, 1, 2, "2016-10-25 19:00:00", "Prudential Center")
                .values(2, 3, 4, "2016-10-25 20:00:00", "Joe Luis Arena")
                .values(3, 1, 4, "2016-10-26 19:00:00", "Prudential Center")
                .exec();
    }

    @Test
    public void testGet() {
        Client client = ClientBuilder.newClient();
        Response r = client.target("http://127.0.0.1:8080/").request().get();

        assertEquals(200, r.getStatus());
        assertEquals("{\"data\":[{\"id\":1,\"arena\":\"Prudential Center\",\"dateTime\":\"2016-10-25T19:00\"},{\"id\":2,\"arena\":\"Joe Luis Arena\",\"dateTime\":\"2016-10-25T20:00\"},{\"id\":3,\"arena\":\"Prudential Center\",\"dateTime\":\"2016-10-26T19:00\"}],\"total\":3}",
                r.readEntity(String.class));
    }
}
