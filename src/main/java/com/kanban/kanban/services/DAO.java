package com.kanban.kanban.services;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.kanban.kanban.exceptions.CreateKanbanBoardException;
import com.kanban.kanban.exceptions.CreateKanbanDemandException;
import com.kanban.kanban.exceptions.ReadKanbanDemandException;
import com.kanban.kanban.exceptions.UpdateKanbanBoardException;
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

	public Response<List<KanbanBoard>> readKanbanBoard(int skip, int limit, String boardTitle){
		
		Response<List<KanbanBoard>> response = new Response<List<KanbanBoard>>();
		
		Query query = new Query();
		if(boardTitle != null) {
			query.addCriteria(Criteria.where("title").is(boardTitle));
		}
		if(limit == 0) limit+=10;
		query.skip(skip).limit(limit);
		
		
		response.setEntityResponse(template.find(query, KanbanBoard.class));
		response.setRegistersQuantity(template.count(new Query(), KanbanBoard.class));
		return response;
		
	}
	
	public Response<KanbanBoard> updateKanbanBoard(KanbanBoard updateBoard){
		
		if(updateBoard == null) {
			throw new UpdateKanbanBoardException("Unable to execute operation, object to be changed is possibly null.");
		}
		
		KanbanBoard old = template.findOne(new Query(Criteria.where("id").is(updateBoard.getId())), KanbanBoard.class);
		
		Update update = new Update();
		update.set("title", updateBoard.getTitle());
		update.set("demands", updateBoard.getDemands());
		
		Response<KanbanBoard> response = new Response<KanbanBoard>();
		template.updateFirst(new Query(Criteria.where("id").is(updateBoard.getId())), update, KanbanBoard.class);
		response.setRegistersQuantity(template.count(new Query(), KanbanBoard.class));
		
		KanbanBoard newBoard = template.findOne(new Query(Criteria.where("id").is(updateBoard.getId())), KanbanBoard.class);
		
		if(newBoard == old) {
			throw new UpdateKanbanBoardException("Error performing update operation, registry value has not changed.");
		}
		
		response.setEntityResponse(newBoard);
		return response;
	
	}
	
}
