package com.kanban.kanban.models;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
	@NotNull
	@NotEmpty
	private String title;
	@Valid
	private List<KanbanDemand> demands;
	

}
