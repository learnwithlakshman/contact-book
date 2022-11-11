package com.careerit.cb.service;

import com.careerit.cb.domain.Contact;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@Slf4j
public class ContactServiceInMemoryImpl implements  ContactService {

  @Autowired
  private StorageService storageService;

  @Override
  public Contact addContact(Contact contact) {
    Contact savedContact= storageService.save(contact);
    log.info("Contact is saved with id {}",savedContact.getId());
    return savedContact;
  }

  @Override
  public List<Contact> getContacts() {
    List<Contact> list = storageService.list();
    log.info("Total {} contacts found",list.size());
    return list;
  }

  @Override
  public List<Contact> search(String str) {
    List<Contact> list = storageService.list().stream().filter(c->c.getName().toLowerCase().contains(str.toLowerCase())).collect(Collectors.toList());
    log.info("Search string  {}  has {} matches",str,list.size());
    return list;
  }

  @Override
  public boolean deleteContact(UUID id) {
    storageService.remove(id);
    return true;
  }
}
