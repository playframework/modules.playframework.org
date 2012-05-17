package models;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import play.test.FakeApplication;

import static play.test.Helpers.*;

public class ModelTest {

    public static FakeApplication app;

    @BeforeClass
    public static void startApp() {
        app = fakeApplication(inMemoryDatabase());
        start(app);
    }

    @AfterClass
    public static void stopApp() {
        stop(app);
    }

}
