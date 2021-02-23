package com.example.backend_disaster_project.disasterbackend.controllers;


import com.example.backend_disaster_project.disasterbackend.entities.Victim;
import com.example.backend_disaster_project.disasterbackend.repositories.VictimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/")
@CrossOrigin(origins="http://localhost:3000")
public class VictimController {

	@Autowired
	private VictimRepository victimRepository;

	@GetMapping("/victims")
	public Iterable<Victim> getVictims() {
		return this.victimRepository.findAll();
	}

	@GetMapping("/victims/{username}")
	public ResponseEntity<String> getVictimByUsername(@PathVariable String username){
		Victim victim = victimRepository.findByUsername(username);
		return  ResponseEntity.ok(victim.getMessageToVictim());
	}
	@PutMapping("/victims/{username}")
	public ResponseEntity<Victim> updateVictimMessage(@PathVariable String username, @RequestBody Victim victimDetails){
		Victim victim = victimRepository.findByUsername(username);
		victim.setMessageToVictim(victimDetails.getMessageToVictim());

		Victim updatedVictim = victimRepository.save(victim);
		return ResponseEntity.ok(updatedVictim);
	}
	@PutMapping("/victimsMessage/{username}")
	public ResponseEntity<Victim> updateFromVictimMessage(@PathVariable String username, @RequestBody Victim victimDetails){
		Victim victim = victimRepository.findByUsername(username);
		victim.setMessage(victimDetails.getMessage());

		Victim updatedVictim = victimRepository.save(victim);
		return ResponseEntity.ok(updatedVictim);
	}

	@DeleteMapping("/delete")
	public void delete(){
		victimRepository.deleteAll();
	}


}




