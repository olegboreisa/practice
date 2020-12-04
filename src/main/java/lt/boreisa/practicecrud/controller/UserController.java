package lt.boreisa.practicecrud.controller;

import lombok.extern.slf4j.Slf4j;
import lt.boreisa.practicecrud.model.User;
import lt.boreisa.practicecrud.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@Slf4j
public class UserController {

    /**
     * [FIRST WE NEED TO CREATE USER REPO TO USE CRUD METHODS]
     * [@AUTOWIRED IS NOT NECESSARY AS SPRING BOOT GIVES IT TO US AUTOMATICALLY]
     * [@AUTOWIRED TELLS SPRING WHERE TO INJECT THE BEAN]
     */

    private UserRepo userRepo;

    @Autowired
    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    //[IN ORDER TO GET A MAIN PAGE]
    @RequestMapping(path = {"", "/",})
    private String getIndex() {
        return "user/main-user";
    }


    // ------------------------------------------------------------
    // [TO ADD A NEW USER]

    /**
     * An @ModelAttribute on a method argument indicates the argument will be retrieved from the model.
     * If not present in the model, the argument will be instantiated first and then added to the model
     *
     * https://stackoverflow.com/questions/23576213/what-is-the-difference-between-modelattribute-model-addattribute-in-spring
     */

    @RequestMapping(path = "/getUserForm", method = RequestMethod.GET)
    private String getAddForm(@ModelAttribute User user) {
        return "user/add-user";
    }

    @RequestMapping(path = "/addUser", method = RequestMethod.POST)
    private String addUser(@Valid @ModelAttribute User user) {
        log.info("user{}", user);
        userRepo.save(user);
        return "user/main-user";
    }

    // ---------------------------------------------------
    // ------------------------------------------------------------
    // [TO GET A LIST OF USERS]

    @RequestMapping(path = "/getUserList", method = RequestMethod.GET)
    private String getUserList(Model model) {
        model.addAttribute("userList", userRepo.findAll());
        return "user/list-user";
    }

    // ---------------------------------------------------
    // ------------------------------------------------------------
    // [TO UPDATE THE USER BY ID]

    @RequestMapping(path = "/update/{id}", method = RequestMethod.GET)
    private String getUpdateUser(@PathVariable("id") Long id, Model model) {
        User user = userRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + id));
        model.addAttribute("user", user);
        return "user/update-user";
    }

    @RequestMapping(path = "/updateUser", method = RequestMethod.POST)
    private String updateUser (@ModelAttribute User user) {
        userRepo.save(user);
        return "user/main-user";
    }

    // ---------------------------------------------------


    // ------------------------------------------------------------
    // [TO DELETE THE USER BY ID]
    @RequestMapping(path = "/delete/{id}", method = RequestMethod.GET)
    private String addUser(@PathVariable("id") Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + id));
        userRepo.delete(user);
        return "user/main-user";
    }
    // ---------------------------------------------------
}
