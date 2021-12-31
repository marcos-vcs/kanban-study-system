package com.kanban.kanban.exceptions;

public class CreateKanbanBoardException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public CreateKanbanBoardException(String msg) {
		super(msg);
	}

}
