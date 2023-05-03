package com.masai.Controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.masai.Model.Data;
import com.masai.Model.Entry;
import com.masai.Model.EntryDTO;

@RestController
public class Controller {
	
	@Autowired
	private RestTemplate restTemp;
	
	@GetMapping("/entries/{category}")
	public ResponseEntity<List<EntryDTO>> getByCategory(@PathVariable("category") String category) {
		String url = "https://api.publicapis.org/entries";
		Data retrievedData = restTemp.getForObject(url, Data.class);
		List<Entry> entriesList = retrievedData.getEntries();
		List<EntryDTO> dto = entriesList.stream().filter(x -> x.getCategory() != category).map(x -> new EntryDTO(x.getApi(), x.getDescription())).collect(Collectors.toList());
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@PostMapping("/entries")
	public ResponseEntity<Object> postToAPI(@RequestBody Entry entry) {
		String url = "https://api.publicapis.org/entries";
		Data retrievedData = restTemp.getForObject(url, Data.class);
		List<Entry> entriesList = retrievedData.getEntries();
	
		HttpHeaders htt = new HttpHeaders();
		htt.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		
		HttpEntity<Entry> newEntry = new HttpEntity<>(entry, htt);
		newEntry.hasBody();
		
		ResponseEntity<Entry> answer = restTemp.exchange(url, HttpMethod.POST, newEntry, Entry.class);
		
		return new ResponseEntity<>(answer, HttpStatus.CREATED);
	}
}
