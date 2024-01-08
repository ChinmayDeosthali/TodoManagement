package com.todo.rest.webservices.restfulwebservices.todobackend;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

//@CrossOrigin(origins="http://localhost:4200")
@RestController
public class TodoJPAResource {

	@Autowired
	private TodoJPARepository todoJPARepository;
	
	
	@GetMapping(path="/")
	public String getStatus()
	{
		return "Application is up and running";
	}
	@GetMapping(path="/jpa/users/{username}/todos")
	public List<Todo> getAllTodos(@PathVariable String username){
		System.out.println("----getAllTodos called,Params -  Username: " + username);
		System.out.println();
		return todoJPARepository.findByUsername(username);
	}
	
	@GetMapping(path="/jpa/users/{username}/todos/{id}")
	public Todo getTodo(@PathVariable String username, @PathVariable long id){
		System.out.println("----getTodo called,Params -  Username: " + username + ", id: "+ id);
		System.out.println();
		return todoJPARepository.findById(id).get();
	}
	
	
	@PutMapping("/jpa/users/{username}/todos/{id}")
	public ResponseEntity<Todo> updateTodo(@PathVariable String username, @PathVariable long id, @RequestBody Todo todo) {
		
		System.out.println("----updateTodo called,Params -  Username: " + username + ", id: "+ id+ ", todo: "+ todo);
		System.out.println();
		Todo updatedTodo =todoJPARepository.save(todo);
		return new ResponseEntity<Todo>(updatedTodo, HttpStatus.OK);
	}
	
	@PostMapping("/jpa/users/{username}/todos")
	public ResponseEntity<Todo> createTodo(@PathVariable String username, @RequestBody Todo todo) {
		
		System.out.println("----createTodo called,Params -  Username: " + username +", todo: "+ todo);
		System.out.println();
		todo.setUsername(username);
		Todo createdTodo =todoJPARepository.save(todo);
		
		
		//Return the location of the resource generated
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdTodo.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	//@PostMapping()
	
	@DeleteMapping("/jpa/users/{username}/todos/{id}")
	public ResponseEntity<Void> deleteTodo(@PathVariable String username, @PathVariable long id){
		
		System.out.println("----deleteTodo called,Params -  Username: " + username +", id: "+ id);
		System.out.println();

		
		todoJPARepository.deleteById(id);
		
		return ResponseEntity.noContent().build();
		
//		if(todo!=null) {
//			return ResponseEntity.noContent().build();
//		}
//		
//		return ResponseEntity.notFound().build();
		
	}
	
	
	
}
