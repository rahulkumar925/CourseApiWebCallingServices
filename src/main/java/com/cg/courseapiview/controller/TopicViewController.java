package com.cg.courseapiview.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
public String getTopic(@PathVariable(value = "id") int id, Model model) throws IOException {
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

/*@RequestMapping(value="/addTopic", method=RequestMethod.POST)
public String addTopic(@RequestBody Topic topic, Model model) {
RestTemplate restTemplate = new RestTemplate();
String uri = "http://localhost:8080/topics";
model.addAttribute("addTopic", restTemplate.postForObject(uri, topic, Topic.class));

return "success";

	
}*/

@RequestMapping(value = "/add")
public String addTopic() {

	return "add";
}
/*
@RequestMapping(value = "/addNewTopic", method = RequestMethod.POST)
public String addNewTopic(@RequestParam int id, String name, String description, Model model) throws IOException {
	RestTemplate restTemplate = new RestTemplate();

	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

	MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
	map.add("id", String.valueOf(id));
	map.add("name", name);
	map.add("description", description);

	HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

	ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8081/topics/", request,
			String.class);
	System.out.println(response);
	return "success";


	

	//getAllCustomer(model);

}*/
@RequestMapping(value = "/addNewTopic", method = RequestMethod.POST)
public String addNewTopic( @RequestParam("id") int id1, @RequestParam("name")String name1, @RequestParam("description") String description1, Model model) {
	RestTemplate restTemplate = new RestTemplate();
	int id =id1;
	String name=name1;
	String description=description1;
	String postUrl= "http://localhost:8081/topics";
	Topic topic = new Topic(id,name,description);
	ResponseEntity<String> postResponse = restTemplate.postForEntity(postUrl, topic, String.class);
	System.out.println(postResponse.getBody());
	
	return "success";
}

@RequestMapping(value = "/delete")
public String delTopic() {

	return "delete";
}

@RequestMapping(value = "/deleteTopic", method = RequestMethod.POST)
public String deleteNewTopic (@RequestParam("id") int id1, Model model) {
	RestTemplate restTemplate = new RestTemplate();
	int id= id1;
	String delUrl= "http://localhost:8081/topics/"+id;
	restTemplate.delete(delUrl);
	
	return "deletemsg";
	
}
}
