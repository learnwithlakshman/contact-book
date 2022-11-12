package com.careerit.cb.web;

import com.careerit.cb.domain.Contact;
import com.careerit.cb.service.ContactService;
import com.careerit.cb.util.AppUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ContactControllerTest {

  @Autowired
  private ContactController contactController;

  @Autowired
  private ContactService contactService;
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void addContactTest() throws Exception {
    mockMvc.perform(
            MockMvcRequestBuilders.post("/api/contact")
                .content(AppUtil.convertString(getContact()))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Krish"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("krish.t@gmail.com"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.mobile").value("9876543210"));
  }

  @Test
  public void getAllContactsTest() throws Exception {
    contactService.addContact(getRandomContact());
    mockMvc.perform(
            MockMvcRequestBuilders.get("/api/contact/all")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").exists());
  }


  private Contact getContact() {
    Contact contact = new Contact();
    contact.setName("Krish");
    contact.setEmail("krish.t@gmail.com");
    contact.setMobile("9876543210");
    return contact;
  }
  private Contact getRandomContact() {
    Contact contact = new Contact();
    contact.setName("Manoj");
    contact.setEmail("manoj.pvn@gmail.com");
    contact.setMobile("9876543213");
    return contact;
  }

  private Contact getContactWithId() {
    Contact contact = new Contact();
    contact.setId(UUID.randomUUID());
    contact.setName("Krish");
    contact.setEmail("krish.t@gmail.com");
    contact.setMobile("9876543210");
    return contact;
  }
}
