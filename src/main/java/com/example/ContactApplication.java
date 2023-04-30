package com.example;

import java.util.List;
import javax.swing.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@SpringBootApplication
public class ContactApplication implements CommandLineRunner {

  private final ContactRepository repository;
  private final ContactRepositoryJpa repositoryJpa;

  public ContactApplication(
      ContactRepository repository, ContactRepositoryJpa contactRepositoryJpa) {
    this.repository = repository;
    this.repositoryJpa = contactRepositoryJpa;
  }

  public static void main(String[] args) {
    SpringApplication.run(ContactApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {

    //    repository.testPersistNewObject();
    //    repository.tryPersisDetachedObject();
    //    repository.testUpdateExisingObject();

    //    System.out.println(repository.getContact());

    System.out.println("RESULT" + repositoryJpa.search("bao"));
    System.out.println("RESULT 1" + repositoryJpa.getContactTo(20L, 50l));
    repositoryJpa.updateContact("Nguyen 1", 19l);
    Pageable sortedByName =
            PageRequest.of(0, 10, Sort.by("contact_id").descending());
    System.out.println(repositoryJpa.allContact(100L,0L,sortedByName).getContent());
  }

  private void createContact() {
    Contact newContact = new Contact();
    newContact.setName("Peter Smith");
    newContact.setEmail("Peter.smith@gmail.com");
    newContact.setAddress("New York, USA");
    newContact.setTelephone("3124123-124123");

    repository.save(newContact);
  }

  private void updateContact() {
    Contact existContact = new Contact();
    existContact.setId(18);
    existContact.setName("Bao ne");
    existContact.setEmail("Bao@gmail.com");
    existContact.setAddress("Da Nang");
    existContact.setTelephone("113");
    Contact updatedContact = repository.update(existContact);
    System.out.println(updatedContact);
  }

  private void listContacts() {
    List<Contact> listContacts = repository.findAll();
    for (Contact a : listContacts) System.out.println(a);
  }

  private void getContact() {
    Integer contactId = 28;
    Contact contact = repository.findById(contactId);
    System.out.println(contact);
  }

  private void deleteContact() {
    Integer contactId = 19;
    repository.removeContact(contactId);
  }
}
