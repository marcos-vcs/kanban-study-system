package com.kanban.kanban.controllers;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kanban.kanban.exceptions.CreateKanbanBoardException;
import com.kanban.kanban.models.KanbanBoard;
import com.kanban.kanban.models.Response;
import com.kanban.kanban.services.DAO;

@Controller
@RequestMapping("/kanban-api")
public class KanbanController {

	private final DAO service;
	public KanbanController(DAO service) {
		this.service = service;
	}
	
	@GetMapping
	public ResponseEntity<Response<KanbanBoard>> create(@Valid @RequestBody KanbanBoard kanbanBoard, BindingResult result){
		try {
			
			if(result.hasErrors()) {
				return new ResponseEntity<Response<KanbanBoard>>(HttpStatus.NOT_ACCEPTABLE);
			}
			
			Response<KanbanBoard> response = service.createKanbanBoard(kanbanBoard);
			if(response == null) {
				return new ResponseEntity<Response<KanbanBoard>>(HttpStatus.NOT_MODIFIED);
			}else {
				return new ResponseEntity<Response<KanbanBoard>>(response, HttpStatus.OK);
			}
			
		}catch (CreateKanbanBoardException e) {
			return new ResponseEntity<Response<KanbanBoard>>(HttpStatus.CONFLICT);
		}catch (Exception e) {
			return new ResponseEntity<Response<KanbanBoard>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}
