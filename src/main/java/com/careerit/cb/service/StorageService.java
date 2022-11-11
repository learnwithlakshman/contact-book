package com.careerit.cb.service;

import com.careerit.cb.domain.Contact;
import com.careerit.cb.exception.ContactAlreadyExistsException;
import com.careerit.cb.exception.ContactNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class StorageService {
  private Map<UUID, Contact> map;

  public StorageService() {
    map = new HashMap<>();
  }

  public Contact save(Contact contact) {
    Assert.notNull(contact, "Contact information can't null");
    Assert.notNull(contact.getMobile(), "Mobile number can't be empty");
    String mobile = contact.getMobile();
    if (checkMobileNumberExists(mobile)) {
      throw new ContactAlreadyExistsException("With given mobile number some other contact exists");
    }
    if (contact.getId() == null) {
      UUID id = UUID.randomUUID();
      contact.setId(id);
    }
    map.put(contact.getId(), contact);
    return contact;
  }

  private boolean checkMobileNumberExists(String mobile) {
    return map.values()
        .stream()
        .filter(c -> c.getMobile().equals(mobile))
        .findAny()
        .isPresent();
  }

  public List<Contact> list() {
    if (map.values() == null) {
      return Collections.emptyList();
    }
    return map.values().stream().collect(Collectors.toList());
  }

  public void remove(UUID id) {
    if (map.get(id) != null) {
      map.remove(id);
    } else {
      throw new ContactNotFoundException("Contact with given id:" + id + " is not found");
    }
  }


}
