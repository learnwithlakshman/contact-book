package com.careerit.cb.service;

import com.careerit.cb.domain.Contact;

import java.util.List;
import java.util.UUID;

public interface ContactService {

      Contact addContact(Contact contact);
      List<Contact> getContacts();
      boolean deleteContact(UUID id);
}
