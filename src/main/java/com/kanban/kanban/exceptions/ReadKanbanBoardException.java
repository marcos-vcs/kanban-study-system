package com.kanban.kanban.exceptions;

public class ReadKanbanBoardException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ReadKanbanBoardException(String msg) {
		super(msg);
	}

}
