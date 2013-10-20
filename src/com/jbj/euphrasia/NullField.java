package com.jbj.euphrasia;

public class NullField extends Field {

	private static final String NULL_DATA = "THIS IS NULL";

	public NullField() {
		super(NULL_DATA);
	}

	@Override
	public EntryDatabaseManager updateEntryField(EntryDatabaseManager entryManager) {
		return entryManager;
	}

}
