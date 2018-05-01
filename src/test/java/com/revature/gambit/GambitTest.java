package com.revature.gambit;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Parent test configuration class.
 * 
 * @author Peter Alagna Jr.
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/data-h2.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/tear-down.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
@DirtiesContext
public class GambitTest {
	
	@ClassRule
	public static KafkaEmbedded embeddedKafka =
			new KafkaEmbedded(1, true, 1);
	
	@Test
	public void run() {}
}
