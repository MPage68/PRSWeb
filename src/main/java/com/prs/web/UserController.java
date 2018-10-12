package com.prs.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.prs.business.user.User;
import com.prs.business.user.UserRepository;
import com.prs.util.JsonResponse;
@CrossOrigin
@Controller
@RequestMapping("/Users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/List")
	public @ResponseBody JsonResponse getAllUsers() {
		try {
			return JsonResponse.getInstance(userRepository.findAll());
		} catch (Exception e) {
			return JsonResponse.getErrorInstance("User list failure:" + e.getMessage(), e);
		}
	}

	@GetMapping("/Get/{id}")
	public @ResponseBody JsonResponse getUser(@PathVariable int id) {
		try {
			Optional<User> user = userRepository.findById(id);
			if (user.isPresent()) {
				return JsonResponse.getInstance(user.get());
			} else {
				return JsonResponse.getErrorInstance("User ID not found:" + id, null);
			}
		} catch (Exception e) {
			return JsonResponse.getErrorInstance("Error, User ID not valid:" + e.getMessage(), null);
		}
	}

	@PostMapping("/Login")
	public @ResponseBody JsonResponse authenticate(@RequestBody User user) {
		try {
			User u = userRepository.findByUserNameAndPassword(user.getUserName(), user.getPassword());
			return JsonResponse.getInstance(u);
		} catch (Exception e) {
			return JsonResponse.getErrorInstance("Error, login not valid. Please try again:" + e.getMessage(), null);
		}
	}

	@PostMapping("/Add")
	public @ResponseBody JsonResponse addUser(@RequestBody User user) {
		return saveUser(user);
	}

	@PostMapping("/Change")
	public @ResponseBody JsonResponse updateUser(@RequestBody User user) {
		return saveUser(user);
	}

	private @ResponseBody JsonResponse saveUser(@RequestBody User user) {
		try {
			userRepository.save(user);
			return JsonResponse.getInstance(user);
		} catch (DataIntegrityViolationException e) {
			return JsonResponse.getErrorInstance(e.getRootCause().toString(), e);
		} catch (Exception e) {
			return JsonResponse.getErrorInstance(e.getMessage(), e);
		}
	}

	@PostMapping("/Remove")
	public @ResponseBody JsonResponse removeUser(@RequestBody User user) {
		try {
			userRepository.delete(user);
			return JsonResponse.getInstance(user);
		} catch (Exception e) {
			return JsonResponse.getErrorInstance(e.getMessage(), e);
		}
	}
}
