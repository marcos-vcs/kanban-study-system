package com.kanban.kanban.services;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.kanban.kanban.exceptions.CreateKanbanBoardException;
import com.kanban.kanban.exceptions.CreateKanbanDemandException;
import com.kanban.kanban.models.KanbanBoard;
import com.kanban.kanban.models.KanbanDemand;
import com.kanban.kanban.models.Response;

@Service
public class DAO {
	
	private final MongoTemplate template;
	
	public DAO(MongoTemplate template) {
		this.template = template;
	}
	
	public Response<KanbanBoard> createKanbanBoard(KanbanBoard newBoard) {
		
		if(newBoard == null || newBoard.getTitle() == null) {
			throw new CreateKanbanBoardException("Error creating new kanban board. Title cannot be null!");
		}
		
		Response<KanbanBoard> response = new Response<KanbanBoard>();
		response.setRegistersQuantity(template.count(new Query(), KanbanBoard.class));
		response.setEntityResponse(template.save(newBoard));
		return response;
	}
	
	public Response<KanbanBoard> createKanbanDemand(String kanbanBoardId, KanbanDemand newDemand){
		
		if(kanbanBoardId == null || newDemand == null || newDemand.getTitle() == null) {
			throw new CreateKanbanDemandException("Error creating new kanban demand. The title cannot be null, the reference to the kanban board cannot be null!");
		}

		KanbanBoard boardResponsible = template.findOne(new Query(Criteria.where("id").is(kanbanBoardId)), KanbanBoard.class);
		boardResponsible.getDemands().add(newDemand);
		Response<KanbanBoard> response = new Response<KanbanBoard>();
		response.setEntityResponse(template.save(boardResponsible));
		response.setRegistersQuantity((long) boardResponsible.getDemands().size());
		return response;
		
	}

}
