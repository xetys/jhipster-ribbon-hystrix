package de.stytex.foobar.web.rest;

import de.stytex.foobar.BarApp;
import de.stytex.foobar.client.FooClient;
import de.stytex.foobar.domain.Foo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by on 26.03.16.
 *
 * @author David Steiman
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BarApp.class)
@WebAppConfiguration
@IntegrationTest
public class FooClientTest {
    Logger log = LoggerFactory.getLogger(FooClientTest.class);

    @Inject
    FooClient fooClient;

    @Test
    public void testContextLoads() throws Exception {
        //should be loaded

    }

    @Test
    public void testEntityLifeCycle() throws Exception {
        List<Foo> foos = fooClient.findAll();
        int fooCount = foos.size();

        Foo myFoo = new Foo();
        myFoo.setValue("my awesome foo!");

        //test creating
        Foo result = fooClient.create(myFoo);

        assert result.getId() > 0;
        log.info("created foo entity with id {}", result.getId());


        //test entity get
        myFoo = fooClient.getOne(result.getId());

        assertEquals(myFoo.getId(), result.getId());

        //test collection get
        foos = fooClient.findAll();
        assertEquals(fooCount + 1, foos.size());

        //test entity update
        myFoo.setValue("my changed value");
        result = fooClient.update(myFoo);

        assertEquals(myFoo.getValue(), result.getValue());

        //test delete
        fooClient.delete(result.getId());
    }
}
