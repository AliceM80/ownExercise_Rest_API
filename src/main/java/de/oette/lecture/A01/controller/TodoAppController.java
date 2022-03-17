package de.oette.lecture.A01.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value ="/todo")
public class TodoAppController {
private Map<Integer, String> todos = new HashMap<>();

  //@RequestMapping(method = RequestMethod.POST) same like @PostMapping, useful if several methods (i.e. POST & PUT) for the same method
  @PostMapping
  public Integer add(@RequestParam(value="todo", required = false) String todo){
    // mit default wert--> public void add(@RequestParam(value="todo", defaultValue = "Something") String task){
    Integer newPosition = todos.entrySet().stream().map(Map.Entry::getKey).reduce(Integer::max).orElse(0)+1;
    //todos.put(newPosition, todo);
    //return newPosition;
    return null;
  }

  @PutMapping
  public void update(Integer position, String todo){
    todos.put(position, todo);
  }

  //Methode zum ABrufen von Daten //terminal: curl -XGET "localhost:8090/todo"
  @GetMapping
  public String get(){
    return todos.entrySet().stream().map(en -> String.format("%s %s - ", en.getKey(), en.getValue()))
    .collect(Collectors.joining());
  }

  @DeleteMapping  //curl -XDELETE "localhost:8090/todo?position=1"
  public void delete(Integer position){
    todos.remove(position);
  }
}
