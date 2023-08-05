package com.magnus.authapi.controllers;


import com.magnus.authapi.auth.AuthenticationService;
import com.magnus.authapi.upload.UploadService;
import com.magnus.authapi.user.User;
import com.magnus.authapi.user.UserRepository;
import com.magnus.authapi.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

  private final UploadService uploadService;
  private final AuthenticationService authenticationService;
  private final UserService userService;
  private final UserRepository userRepository;

  @PostMapping("/{id}/upload-image")
  public ResponseEntity<User> uploadImage(
      @PathVariable Long id,
      @RequestParam(name = "image") MultipartFile image
  ) {
    User user = authenticationService.getAuthenticatedUser();
    if (!Objects.equals(user.getId(), id)) {
      return ResponseEntity.status(403).build();
    }
    String fileName = uploadService.uploadFile(image);
    User dbUser = userService.getUserById(user.getId());
    dbUser.setImageName(fileName);
    System.out.println(dbUser);
    return ResponseEntity.ok(userRepository.save(dbUser));
  }
}
