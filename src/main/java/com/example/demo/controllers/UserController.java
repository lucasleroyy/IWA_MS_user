package com.example.demo.controllers;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> list () {
        return userRepository.findAll ();
    }
    @GetMapping
    @RequestMapping("{id}")
    public User get(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID " + id + " not found"));
    }
    public User create (@RequestBody final User user) {
        return userRepository.saveAndFlush (user);
    }
    @RequestMapping( value = "{id}",method = RequestMethod.DELETE )
    public void delete (@PathVariable Long id) {
        userRepository.deleteById(id);
    }

    @RequestMapping( value="{id}" ,method = RequestMethod .PUT)
    public User update( @PathVariable Long id , @RequestBody User user ) {
        // TODO: Ajouter ici une validation si tous les champs ont ete passes Sinon , retourner une erreur 400 (Bad Payload)

        User existingUser = userRepository.getById (id);
        BeanUtils.copyProperties (user,existingUser ,"user_id");
        return userRepository . saveAndFlush (existingUser);
    }


}
