package com.kanban.kanban.controllers;

import java.util.List;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.kanban.kanban.exceptions.CreateKanbanBoardException;
import com.kanban.kanban.exceptions.DeleteKanbanBoardException;
import com.kanban.kanban.exceptions.ReadKanbanBoardException;
import com.kanban.kanban.exceptions.UpdateKanbanBoardException;
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
	
	@PostMapping
	public ResponseEntity<Response<KanbanBoard>> create(@Valid @RequestBody KanbanBoard kanbanBoard, BindingResult result){
		try {
			
			if(result.hasErrors()) {
				System.out.println(result.getFieldError());
				return new ResponseEntity<Response<KanbanBoard>>(HttpStatus.NOT_ACCEPTABLE);
			}
			
			Response<KanbanBoard> response = service.createKanbanBoard(kanbanBoard);
			if(response == null) {
				return new ResponseEntity<Response<KanbanBoard>>(HttpStatus.NOT_MODIFIED);
			}else {
				return new ResponseEntity<Response<KanbanBoard>>(response, HttpStatus.OK);
			}
			
		}catch (CreateKanbanBoardException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<Response<KanbanBoard>>(HttpStatus.CONFLICT);
		}catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<Response<KanbanBoard>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping
	public ResponseEntity<Response<List<KanbanBoard>>> read(@RequestParam(required = false) int skip, @RequestParam(required = false) int limit, @RequestParam(required = false) String kanbanBoardTitle, @RequestParam(required = false) String kanbanBoardId){
		try {
			
			Response<List<KanbanBoard>> response = service.readKanbanBoard(skip, limit,kanbanBoardTitle, kanbanBoardId);
			return new ResponseEntity<Response<List<KanbanBoard>>>(response, HttpStatus.OK);
			
		}catch (ReadKanbanBoardException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<Response<List<KanbanBoard>>>(HttpStatus.CONFLICT);
		}catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<Response<List<KanbanBoard>>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping
	public ResponseEntity<Response<KanbanBoard>> update(@Valid @RequestBody KanbanBoard oldKanbanBoard, BindingResult result){
		try {
			
			if(result.hasErrors()) {
				System.out.println(result.getFieldError());
				return new ResponseEntity<Response<KanbanBoard>>(HttpStatus.NOT_ACCEPTABLE);
			}
			
			Response<KanbanBoard> response = service.updateKanbanBoard(oldKanbanBoard);
			return new ResponseEntity<Response<KanbanBoard>>(response, HttpStatus.OK);
			
		}catch (UpdateKanbanBoardException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<Response<KanbanBoard>>(HttpStatus.CONFLICT);
		}catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<Response<KanbanBoard>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping
	public ResponseEntity<Response<KanbanBoard>> delete(@RequestParam String kanbanBoardId){
		try {
			
			Response<KanbanBoard> response = service.deleteKanbanBoard(kanbanBoardId);
			return new ResponseEntity<Response<KanbanBoard>>(response, HttpStatus.OK);
			
		}catch (DeleteKanbanBoardException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<Response<KanbanBoard>>(HttpStatus.CONFLICT);
		}catch (RuntimeException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<Response<KanbanBoard>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
