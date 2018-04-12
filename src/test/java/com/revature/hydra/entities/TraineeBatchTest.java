package com.revature.hydra.entities;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.revature.gambit.entities.TraineeBatch;

public class TraineeBatchTest {
    
    Logger log = Logger.getRootLogger();
    TraineeBatch tb;
    
    @Before
    public void setUp() throws Exception {
        tb =new TraineeBatch();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testTraineeBatchNoArgsConstructor() {
        log.warn("Testing Trainee Batch No Args Constructor.");
        tb =new TraineeBatch();
        Assert.assertNotNull("No args certification is null",tb);
    }
    
    @Test
    public void testTraineeBatchFieldsConstructor() {
        log.warn("Testing Trainee Batch Fields Constructor.");
        tb =new TraineeBatch(0,0);
        Assert.assertNotNull("No args certification is null",tb);
    }
    
    @Test
    public void testGetTrainee_id() {
        log.warn("Testing Getter for Trainee_id");
        tb =new TraineeBatch(0,0);
        Assert.assertEquals((Integer)0 ,tb.getTrainee_id());
    }
    
    @Test
    public void testSetTrainee_id() {
        log.warn("Testing Setter for Trainee_id");
        tb.setTrainee_id(1);
        Assert.assertEquals((Integer)1 ,tb.getTrainee_id());
    }
    
    @Test
    public void testGetBatch_id() {
        log.warn("Testing Getter for Batch_id");
        tb =new TraineeBatch(0,0);
        Assert.assertEquals((Integer)0 ,tb.getBatch_id());
    }
    
    @Test
    public void testSetBatch_id() {
        log.warn("Testing Setter for Batch_id");
        tb.setBatch_id(1);
        Assert.assertEquals((Integer)1 ,tb.getBatch_id());
    }

}