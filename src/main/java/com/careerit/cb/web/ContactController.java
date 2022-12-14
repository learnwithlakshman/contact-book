package com.careerit.cb.web;

import com.careerit.cb.domain.Contact;
import com.careerit.cb.service.ContactService;
import com.careerit.cb.util.AppResponse;
import com.careerit.cb.util.AppUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/contact")
@Slf4j
public class ContactController {
  @Autowired
  private ContactService contactService;

  @PostMapping
  public ResponseEntity<Contact> addContact(@RequestBody Contact contact) {
    contact = contactService.addContact(contact);
    log.info("Contact {}", AppUtil.convertString(contact) );
    return ResponseEntity.ok(contact);
  }

  @GetMapping("/all")
  public ResponseEntity<List<Contact>> getContacts() {
    return ResponseEntity.ok(contactService.getContacts());
  }

  @GetMapping("/search/{str}")
  public ResponseEntity<List<Contact>> getContacts(@PathVariable("str")String str) {
    return ResponseEntity.ok(contactService.search(str));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<AppResponse> deleteContact(@PathVariable("id") String id) {
       boolean isDeleted= contactService.deleteContact(UUID.fromString(id));
       String message;
       if(isDeleted){
          message = "Contact deleted successfully";
          AppResponse obj = AppResponse.builder().message(message).status(HttpStatus.OK).date(LocalDateTime.now()).build();
          return ResponseEntity.ok(obj);
       }else{
          message = "Contact was not able delete";
          AppResponse obj = AppResponse.builder().message(message).status(HttpStatus.BAD_REQUEST).date(LocalDateTime.now()).build();
          return ResponseEntity.badRequest().body(obj);
       }
  }

}
