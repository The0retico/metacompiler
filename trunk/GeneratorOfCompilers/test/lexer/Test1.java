package lexer;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class Test1 extends TestCase {
public void testccc(){
	assertTrue(true);
	
}

public void testcc(){
	int i = 1;
	assertEquals(1, i);
	
}

public void testc(){
	String i = "ahoj";
	assertEquals("ahoj", i);
	
}

public static Test suite(){
	return new TestSuite(Test1.class);
}

}