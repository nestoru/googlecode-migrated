package com.nestorurquiza.drools;

import java.util.HashMap;
import java.util.Map;

import org.drools.KnowledgeBase;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;

/**
 * This is a sample class to launch a rule that provides a data transformation.
 */
public class DroolsDataTransformationTest {

	public static final void main(String[] args) {
		StatefulKnowledgeSession ksession = null;
		KnowledgeRuntimeLogger logger = null;
		
		try {
			// load up the knowledge base, create the session and logger (it creates file .droolsDataTransformationTest.log --> hidden on purpose)
			DroolsDataTransformation droolsDataTransformation = new DroolsDataTransformation("dataTransformation.drl");
			KnowledgeBase kbase = droolsDataTransformation.readKnowledgeBase();
			ksession = kbase.newStatefulKnowledgeSession();
			logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, ".droolsDataTransformationTest");
			
			// populate the session with input and output variables
			Map<String, Object> inMap = new HashMap<String, Object>();
		    inMap.put("a", "The Crab House");
		    inMap.put("b", 35.50);
		    DroolsDataTransformation.FeedRow feedRow = new DroolsDataTransformation.FeedRow();
		    feedRow.setFields(inMap);
		    ksession.insert(feedRow);
		    inMap = new HashMap<String, Object>();
		    inMap.put("a", "Red Lobster");
		    inMap.put("b", 25.60);
		    feedRow = new DroolsDataTransformation.FeedRow();
		    feedRow.setFields(inMap);
		    ksession.insert(feedRow);
		    
		    DroolsDataTransformation.OutputFeed outputFeed = new DroolsDataTransformation.OutputFeed();
		    ksession.insert(outputFeed);
		    
		    // Execute the rules
			ksession.fireAllRules();
			
			//Test how the domain is affected from the rules engine
			System.out.println(outputFeed);
			
			
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			//cleanup
			if(ksession != null) {
				ksession.dispose();
			}
			if(logger != null) {
				logger.close();
			}	
		}
	}
}