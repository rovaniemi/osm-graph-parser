package omsparser;

import org.junit.*;
import osmparser.XmlReader;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class XmlReaderTest {

    public XmlReaderTest(){

    }

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
