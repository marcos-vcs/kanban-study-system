package com.kanban.kanban.exceptions;

public class DeleteKanbanBoardException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public DeleteKanbanBoardException(String msg) {
		super(msg);
	}

}
