package org.pentiumfallen.fiftheditionactionbroker;

import org.bson.types.ObjectId;
import org.pentiumfallen.fiftheditionactionbroker.models.Characters;
import org.pentiumfallen.fiftheditionactionbroker.models.Users;
import org.pentiumfallen.fiftheditionactionbroker.repositories.CharactersRepository;
import org.pentiumfallen.fiftheditionactionbroker.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {
	@Autowired
	private UsersRepository repository;
	private CharactersRepository charRepo;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<Users> getAllUsers() {
		return repository.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Users getUserById(@PathVariable("id") ObjectId id) {
		return repository.findBy_id(id);
	}

	@RequestMapping(value = "/{id}/characters", method = RequestMethod.GET)
	public List<Characters> getUserCharacters(@PathVariable("id") ObjectId id) {
		List<Characters> allChars = new ArrayList<>();
		for (String charId: repository.findBy_id(id).getCharacters()) {
			allChars.add(charRepo.findBy_id(new ObjectId(charId)));
		}
		return allChars;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Users modifyUserById(@PathVariable("id") ObjectId id, @Valid @RequestBody Users users) {
		users.set_id(id);
		repository.save(users);
		return users;
	}

	@RequestMapping(value = "/{id}/{char}", method = RequestMethod.PUT)
	public Users addCharacter(@PathVariable("id") ObjectId id, @PathVariable("char") ObjectId newChar) {
		Users thisGuy = repository.findBy_id(id);
		thisGuy.addCharacter(newChar);
		repository.save(thisGuy);
		return thisGuy;
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public Users createUser(@Valid @RequestBody Users users) {
		users.set_id(ObjectId.get());
		repository.save(users);
		return users;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Users deleteUser(@PathVariable ObjectId id) {
		Users deleted = repository.findBy_id(id);
		repository.delete(deleted);
		return deleted;
	}
}