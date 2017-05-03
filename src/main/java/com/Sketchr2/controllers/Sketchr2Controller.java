package com.Sketchr2.controllers;

import com.Sketchr2.entities.User;
import com.Sketchr2.services.CommentRepository;
import com.Sketchr2.services.DrawingRepository;
import com.Sketchr2.services.UserRepository;
import com.Sketchr2.utils.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by ericweidman on 5/2/17.
 */

@RestController
public class Sketchr2Controller {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DrawingRepository drawingRepository;
    @Autowired
    private CommentRepository commentRepository;

    @RequestMapping(path = "/create-user")
    private String createUser(@RequestBody User userSubmittedViaForm, HttpSession userSession) throws Exception {

        User saveUser = new User();
        User checkIfExists = userRepository.findByUserName(userSubmittedViaForm.getUserName());

        //Checks for username submission for null or empty string.
        if (userSubmittedViaForm.getUserName() == null || userSubmittedViaForm.getUserName().trim().length() == 0) {
            throw new Exception("Username not valid");
        }
        //Checks for password submission for null or empty string.
        else if (userSubmittedViaForm.getPasswordHash() == null || userSubmittedViaForm.getPasswordHash().trim().length() == 0) {
            throw new Exception("Password not valid");
        }
        //Checks username submission against database for use.
        else if (checkIfExists != null) {
            throw new Exception("Username already in use");
        } else {
            saveUser.setUserName(userSubmittedViaForm.getUserName().toLowerCase());
            //Hashes password before saving to database.
            saveUser.setPasswordHash(PasswordStorage.createHash(userSubmittedViaForm.getPasswordHash()));
            //Saves user to to database.
            userRepository.save(saveUser);
            //Saves a session based on the username.
            userSession.setAttribute("userName", saveUser.getUserName());
            System.out.println("User Created");
            return "User Created.";
        }
    }

    @RequestMapping(path = "/login")
    public void loginUser(@RequestBody User userSubmittedViaForm, HttpSession userSession) throws Exception {

        //Finds the user in database via the submitted username.
        User checkUserValidity = userRepository.findByUserName(userSubmittedViaForm.getUserName().toLowerCase());

        //Checks for username submission for null or empty string.
        if (userSubmittedViaForm.getUserName() == null || userSubmittedViaForm.getUserName().trim().length() == 0) {
            throw new Exception("Invalid username input");
        }
        //Checks for password submission for null or empty string.
        else if (userSubmittedViaForm.getPasswordHash() == null || userSubmittedViaForm.getPasswordHash().trim().length() == 0) {
            throw new Exception("Invalid password input");
        }
        //Checks if uses exists.
        else if (checkUserValidity == null) {
            throw new Exception("User does not exist");
        }
        //Checks for password equality.
        else if (!PasswordStorage.verifyPassword(userSubmittedViaForm.getPasswordHash(), checkUserValidity.getPasswordHash())) {
            throw new Exception("Incorrect Password");
        } else {
            //Saves session by valid username.
            userSession.setAttribute("userName", checkUserValidity.getUserName());
        }
    }
    @RequestMapping(path = "/logout")
    public void logout(HttpSession userSession){
        userSession.invalidate();
    }
}