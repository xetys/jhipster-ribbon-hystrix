package de.stytex.foobar.web.rest;

import de.stytex.foobar.FooApp;
import de.stytex.foobar.client.BarClient;
import de.stytex.foobar.domain.Bar;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Created by on 27.03.16.
 *
 * @author David Steiman
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FooApp.class)
@WebAppConfiguration
@IntegrationTest
public class BarClientTest {
    Logger log = LoggerFactory.getLogger(BarClientTest.class);

    @Inject
    BarClient barClient;

    @Test
    public void testContextLoads() throws Exception {
        //should be loaded

    }

    @Test
    public void testEntityLifeCycle() throws Exception {
        Collection<Bar> bars = barClient.findAll();
        int barCount = bars.size();

        Bar myBar = new Bar();
        myBar.setValue("my awesome bar!");

        //test creating
        Bar result = barClient.create(myBar);

        assert result.getId() > 0;
        log.info("created bar entity with id {}", result.getId());


        //test entity get
        myBar = barClient.getOne(result.getId());

        assertEquals(myBar.getId(), result.getId());

        //test collection get
        bars = barClient.findAll();
        assertEquals(barCount + 1, bars.size());

        //test entity update
        myBar.setValue("my changed value");
        result = barClient.update(myBar);

        assertEquals(myBar.getValue(), result.getValue());

        //test delete
        barClient.delete(result.getId());
    }
}
