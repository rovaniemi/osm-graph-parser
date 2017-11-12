package omsparser;

import org.junit.*;
import osmparser.XmlReader;

import java.io.*;

public class XmlReaderTest {

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void ifDocumentationNotFoundReturnNull(){
        XmlReader reader = new XmlReader();
        Assert.assertEquals(null, reader.getDocument(new File("/map/asdfji")));
    }
}
