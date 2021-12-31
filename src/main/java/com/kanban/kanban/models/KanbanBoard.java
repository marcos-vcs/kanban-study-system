package com.kanban.kanban.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document("kanbanBoards")
public class KanbanBoard {
	
	@Id
	private String id;
	private String title;
	private List<KanbanDemand> demands;
	

}
