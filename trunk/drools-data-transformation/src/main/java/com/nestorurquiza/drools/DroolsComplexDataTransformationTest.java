package com.nestorurquiza.drools;

import java.util.HashMap;
import java.util.Map;

import org.drools.KnowledgeBase;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;

/**
 * This is a sample class to launch several rules that provides a data transformation in a per "type of row basis" basis.
 */
public class DroolsComplexDataTransformationTest {

	public static final void main(String[] args) {
		StatefulKnowledgeSession ksession = null;
		KnowledgeRuntimeLogger logger = null;
		
		try {
			// load up the knowledge base, create the session and logger (it creates file .droolsDataTransformationTest.log --> hidden on purpose)
			DroolsDataTransformation droolsDataTransformation = new DroolsDataTransformation("complexDataTransformation.drl");
			KnowledgeBase kbase = droolsDataTransformation.readKnowledgeBase();
			ksession = kbase.newStatefulKnowledgeSession();
			logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, ".droolsComplexDataTransformationTest");
			
			// populate the session with input and output variables
			DroolsDataTransformation.OutputFeed outputFeed = new DroolsDataTransformation.OutputFeed();
		    
			Map<String, Object> inMap = new HashMap<String, Object>();
		    inMap.put("a", "The Crab House");
		    inMap.put("b", 35.50);
		    inMap.put("c", "Miami Beach");
		    DroolsDataTransformation.InputFeedRow feedRow = new DroolsDataTransformation.InputFeedRow();
		    DroolsDataTransformation.OutputFeedRow outFeedRow = new DroolsDataTransformation.OutputFeedRow();
		    feedRow.setFields(inMap);
		    ksession.insert(feedRow);
		    ksession.insert(outFeedRow);
		    // Execute the rules per row
			ksession.fireAllRules();
			// Add the resulting row to the output feed
			outputFeed.add(outFeedRow);
			
		    inMap = new HashMap<String, Object>();
		    inMap.put("a", "Red Lobster");
		    inMap.put("b", 25.60);
		    inMap.put("c", "Miami");
		    feedRow = new DroolsDataTransformation.InputFeedRow();
		    outFeedRow = new DroolsDataTransformation.OutputFeedRow();
		    feedRow.setFields(inMap);
		    ksession.insert(feedRow);
		    ksession.insert(outFeedRow);
		    // Execute the rules per row
			ksession.fireAllRules();
			// Add the resulting row to the output feed
			outputFeed.add(outFeedRow);
			
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