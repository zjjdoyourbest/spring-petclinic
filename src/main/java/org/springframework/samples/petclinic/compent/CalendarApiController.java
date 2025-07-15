package org.springframework.samples.petclinic.compent;

import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api")
public class CalendarApiController {

	private final OwnerRepository ownerRepository;

	public CalendarApiController(OwnerRepository ownerRepository) {
		this.ownerRepository = ownerRepository;
	}

	@GetMapping("/events")
	public List<Map<String, Object>> getEvents() {
		List<Map<String, Object>> events = new ArrayList<>();
		List<Owner> owners=ownerRepository.findAllVisits();

		owners.forEach(owner -> {
			Set<Pet> petSet = new HashSet<>(owner.getPets()); // 自动去重
			List<Pet> distinctPets = new ArrayList<>(petSet);

			distinctPets.forEach(pet -> {
				pet.getVisits().forEach(visit -> {
					events.add(Map.of(
						"title", owner.getFirstName()+" "+owner.getLastName(),
						"start", visit.getDate()+"T"+visit.getTime(),
						"color", "#f66"
					));
				});
			});
		});

//				// 示例事件：你也可以从数据库中取
//		events.add(Map.of(
//			"title", "会议",
//			"start", "2025-07-20",
//			"color", "#f66"
//		));
//
//		events.add(Map.of(
//			"title", "提交报告",
//			"start", "2025-07-22",
//			"end", "2025-07-24",
//			"color", "#3a87ad"
//		));

		return events;
	}
}

