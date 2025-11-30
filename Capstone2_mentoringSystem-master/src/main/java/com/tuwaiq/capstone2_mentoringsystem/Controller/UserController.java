package com.tuwaiq.capstone2_mentoringsystem.Controller;

import Api.ApiResponse;
import com.tuwaiq.capstone2_mentoringsystem.Models.Course;
import com.tuwaiq.capstone2_mentoringsystem.Models.Enrollment;
import com.tuwaiq.capstone2_mentoringsystem.Models.Review;
import com.tuwaiq.capstone2_mentoringsystem.Models.User;
import com.tuwaiq.capstone2_mentoringsystem.Service.UserService;
import com.tuwaiq.capstone2_mentoringsystem.Service.WhatsappService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final WhatsappService whatsappService;

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody @Valid User user, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }else {
            userService.addUser(user);
            return ResponseEntity.status(200).body(new ApiResponse("The user have been added successfully"));
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> getUser(){
        List<User> users=userService.getUsers();
        if (users.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There are no users to show"));
        }else {
            return ResponseEntity.status(200).body(users);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody @Valid User user, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }else {
            if (userService.updateUser(id, user)){
                return ResponseEntity.status(200).body(new ApiResponse("The user have been updated successfully"));
            }else {
                return ResponseEntity.status(400).body(new ApiResponse("There are no users with this id found"));
            }
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id){
        if (userService.deleteUser(id)){
            return ResponseEntity.status(200).body(new ApiResponse("The user have been deleted successfully"));
        }else {
            return ResponseEntity.status(400).body(new ApiResponse("There are no users with this id found"));
        }
    }

    @PutMapping("/add-balance-funds/{userId}/{funds}")
    public ResponseEntity<?> addBalanceFunds(@PathVariable Integer userId,@PathVariable Double funds){
        if (userService.addBalanceFunds(userId,funds)){
            return ResponseEntity.status(200).body(new ApiResponse("The user have been deleted successfully"));
        }else {
            return ResponseEntity.status(400).body(new ApiResponse("There are no users with this id found"));
        }
    }

    @PostMapping("/send-message/{to}/{message}")
    public ResponseEntity<?> addBalanceFunds(@PathVariable String to,@PathVariable String message){
        whatsappService.sendWhatsAppMessage(to,message);
        return ResponseEntity.status(200).body(new ApiResponse("Message sent successfully"));
    }

    @GetMapping("/get-my-review/{userId}")
    public ResponseEntity<?> getMyReviews(@PathVariable Integer userId) {
        List<Review> reviews = userService.getMyReviews(userId);
        if (reviews.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("You have no reviews"));
        } else {
            return ResponseEntity.status(200).body(reviews);
        }
    }

    @GetMapping("/get-my-enrollment/{userId}")
    public ResponseEntity<?> getMyEnrollments(@PathVariable Integer userId) {
        List<Enrollment> enrollments = userService.getMyEnrollment(userId);
        if (enrollments.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("You have no enrollments"));
        } else {
            return ResponseEntity.status(200).body(enrollments);
        }
    }
}
