package com.revature.hydra.entities;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CertificationTest {
	
	Logger log = Logger.getRootLogger();
	Certification c;

	@Before
	public void setUp() throws Exception {
		c = new Certification("URL", "Name", -1);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testNoArgsConstructor() {
		log.warn("Testing no args constructor");
		Certification c2 = new Certification();
		Assert.assertNotNull("No args certification is null", c2);
	}
	
	@Test
	public void testFullConstructor() {
		log.warn("Testing fully parameterized constructor");
		Certification c2 = new Certification("URL", "Name", 0);
		Assert.assertNotNull("Certification is null", c2);
	}
	
	@Test
	public void testGetUrl() {
		log.warn("Testing URL getter");
		Assert.assertTrue("Getter did not return expected value", c.getUrl().equals("URL"));
	}
	
	@Test
	public void testSetUrl() {
		log.warn("Testing URL setter");
		c.setUrl("newUrl");
		Assert.assertNotNull("URL is null", c.getUrl());
		Assert.assertTrue("Setter did not set expected value", c.getUrl().equals("newUrl"));
	}
	
	@Test
	public void testGetName() {
		log.warn("Testing Name getter");
		Assert.assertNotNull("Name is null", c.getName());
		Assert.assertTrue("Getter did not return expected value", c.getName().equals("Name"));
	}
	
	@Test
	public void testSetName() {
		log.warn("Testing Name setter");
		c.setName("newName");
		Assert.assertNotNull("Name is null", c.getName());
		Assert.assertTrue("Setter did not set expected value", c.getName().equals("newName"));
	}
	
	@Test
	public void testToString() {
		log.warn("Testing toString");
		Assert.assertTrue("ToString did not return expected value", c.toString().equals("Certification [File=" + c.getUrl() + ", name=" + c.getName() + "]"));
		
	}	

}
