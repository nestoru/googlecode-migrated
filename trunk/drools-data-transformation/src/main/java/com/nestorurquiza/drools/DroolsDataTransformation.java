package com.nestorurquiza.drools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;


public class DroolsDataTransformation {
	private String drlFileName;
	
	public DroolsDataTransformation(String drlFileName) {
		super();
		this.drlFileName = drlFileName;
	}
	
	public KnowledgeBase readKnowledgeBase() throws Exception {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource(drlFileName), ResourceType.DRL);
		KnowledgeBuilderErrors errors = kbuilder.getErrors();
		if (errors.size() > 0) {
			for (KnowledgeBuilderError error: errors) {
				System.err.println(error);
			}
			throw new IllegalArgumentException("Could not parse knowledge.");
		}
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
		return kbase;
	}

	public static class Feed {
		List<FeedRow> feedRows;

		public List<FeedRow> getFeedRows() {
			return feedRows;
		}

		public void setFeedRows(List<FeedRow> feedRows) {
			this.feedRows = feedRows;
		} 
		
		public boolean add(FeedRow feedRow) {
			if(feedRows == null){
				feedRows = new ArrayList<FeedRow>();
			}
			return feedRows.add(feedRow);
		}

		public boolean add(Map<String, Object> fields) {
			FeedRow feedRow = new FeedRow();
			feedRow.setFields(fields);
			return add(feedRow);
		}

		@Override
		public String toString() {
			return "Feed [feedRows=" + feedRows + "]";
		}
		
	}
	
	public static class InputFeed extends Feed {
	}
	
	public static class OutputFeed extends Feed {
	}
	
	public static class FeedRow {
		Map<String, Object> fields;

		public Map<String, Object> getFields() {
			return fields;
		}

		public void setFields(Map<String, Object> fields) {
			this.fields = fields;
		}

		@Override
		public String toString() {
			return "FeedRow [fields=" + fields + "]";
		}
		
		public void putField(String key, Object value) {
			if(fields == null){
				fields = new HashMap<String, Object>();
			}
			fields.put(key, value);
		}

	}
	
	public static class InputFeedRow extends FeedRow {
	}
	
	public static class OutputFeedRow extends FeedRow {
	}

}
