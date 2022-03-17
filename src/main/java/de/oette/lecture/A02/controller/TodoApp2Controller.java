package de.oette.lecture.A02.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value ="/todo")
public class TodoApp2Controller {

    private Map<Integer, TaskDto> todos = new HashMap<>();

  //Methode zum Abrufen von Daten //terminal: curl -XGET "localhost:8090/todo"
  @GetMapping
  public Collection<TaskDto> list(){
    return todos.values();
  }


    //@RequestMapping(method = RequestMethod.POST) same like @PostMapping, useful if several methods (i.e. POST & PUT) for the same method
    @PostMapping
    public Integer add(@RequestBody TaskDto task){
            Integer newPosition = todos.entrySet().stream()
            .map(Map.Entry::getKey).reduce(Integer::max)
            .orElse(0)+1;
      todos.put(newPosition, task);
      return newPosition;

    }

    @PutMapping
    public void update(@RequestParam(value ="todo") TaskDto todo,
                       @RequestParam Integer position){
    TaskDto currentTask = todos.get(position);
    currentTask.description = todo.description;
    currentTask.dueAt = todo.dueAt;
    currentTask.version++;

    todos.put(position, todo);
    }


    @DeleteMapping  //curl -XDELETE "localhost:8090/todo?position=1"
    public void delete(@RequestParam Integer position){
      todos.remove(position);
    }
  }


