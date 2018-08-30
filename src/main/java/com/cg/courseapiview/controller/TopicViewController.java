package com.cg.courseapiview.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.cg.courseapiview.bean.Topic;

@Controller
public class TopicViewController {

@RequestMapping( value ="/viewAll")
public String getAllTopics(Model model) {
	
	RestTemplate restTemplate = new RestTemplate();
	Collection<Map> responseEntity = (ArrayList<Map>) restTemplate.getForObject("http://localhost:8081/topics",Collection.class);
	System.out.println(responseEntity);
	ArrayList<Topic> allTopic = new ArrayList<>();
	for (Map map : responseEntity) {
		allTopic.add(new Topic(Integer.parseInt(map.get("id").toString()), map.get("name").toString(),
				map.get("description").toString()));
	}

	model.addAttribute("allTopic",allTopic);
	return "viewTopics";
}
@RequestMapping(value = "/topics/{id}")
public String getAllCustomer(@PathVariable(value = "id") int id, Model model) throws IOException {
	RestTemplate restTemplate = new RestTemplate();
	ResponseEntity<Topic> responseEntity = restTemplate.getForEntity("http://localhost:8081/topics/" + id,
			Topic.class);
	System.out.println(responseEntity.getBody());

	Topic topic = new Topic(responseEntity.getBody().getId(),responseEntity.getBody().getName(),
			responseEntity.getBody().getDescription());
	ArrayList<Topic> list = new ArrayList<>();
	list.add(topic);
	model.addAttribute("allTopic", list);

	return "viewTopics";

}
}
