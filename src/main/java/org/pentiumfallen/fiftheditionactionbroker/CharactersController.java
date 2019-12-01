package org.pentiumfallen.fiftheditionactionbroker;

import org.pentiumfallen.fiftheditionactionbroker.models.Characters;
import org.pentiumfallen.fiftheditionactionbroker.repositories.CharactersRepository;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/characters")
public class CharactersController {
	@Autowired
	private CharactersRepository repository;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<Characters> getAllCharacters() {
		return repository.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Characters getCharacterById(@PathVariable("id") ObjectId id) {
		return repository.findBy_id(id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Characters modifyCharacterById(@PathVariable("id") ObjectId id, @Valid @RequestBody Characters characters) {
		characters.set_id(id);
		repository.save(characters);
		return characters;
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public Characters createCharacter(@Valid @RequestBody Characters characters) {
		characters.set_id(ObjectId.get());
		repository.save(characters);
		return characters;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Characters deleteCharacter(@PathVariable ObjectId id) {
		Characters deleteThis = repository.findBy_id(id);
		repository.delete(deleteThis);
		return deleteThis;
	}
}