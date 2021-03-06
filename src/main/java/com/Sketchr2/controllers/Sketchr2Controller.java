package com.Sketchr2.controllers;

import com.Sketchr2.entities.Drawing;
import com.Sketchr2.entities.User;
import com.Sketchr2.services.CommentRepository;
import com.Sketchr2.services.DrawingRepository;
import com.Sketchr2.services.UserRepository;
import com.Sketchr2.utils.PasswordStorage;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

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

    @RequestMapping(path = "/save-image")
    public void saveImage(String title, String base64result, HttpSession userSession) throws Exception {

        String userName = (String) userSession.getAttribute("userName");
        User user = userRepository.findByUserName(userName);

        //Saves images to the public folder.
        byte[] imageByte = Base64.decodeBase64(base64result);
        String fileName = userName + "_" + title + ".png";
        String savelocation = "public/";

        if (userName == null) {
            throw new Exception("You must be logged in to save images.");
        }

        //Saves image data to database.
        Drawing newDrawing = new Drawing(title, fileName, user);
        drawingRepository.save(newDrawing);
        new FileOutputStream(savelocation + fileName).write(imageByte);
    }

    @RequestMapping(path = "/user-images")
    public List<Drawing> userImages(HttpSession userSession) {

        String userName = (String) userSession.getAttribute("userName");
        User user = userRepository.findByUserName(userName);
        return drawingRepository.findAllByUserId(user.getId());

    }

    @RequestMapping(path = "delete-image/{id}", method = RequestMethod.DELETE)
    public void deleteImage(@PathVariable("id") int id, HttpSession userSession) {


        Drawing deleteimage = drawingRepository.findOne(id);
        drawingRepository.delete(id);
        File delete = new File("public/" + deleteimage.getFileName());
        boolean bool;

        try {
            bool = delete.delete();
            System.out.println("File deleted " + bool);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(path = "/logout")
    public void logout(HttpSession userSession) {
        userSession.invalidate();
    }

}