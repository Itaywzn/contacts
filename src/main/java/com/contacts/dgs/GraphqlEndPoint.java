package com.contacts.dgs;

import com.contacts.DgsConstants;
import com.contacts.entities.Address;
import com.contacts.entities.Contact;
import com.contacts.entities.ContactGroup;
import com.contacts.services.AddressService;
import com.contacts.services.ContactGroupService;
import com.contacts.services.ContactService;
import com.netflix.graphql.dgs.*;
import org.dataloader.DataLoader;
import org.dataloader.MappedBatchLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@DgsComponent
public class GraphqlEndPoint {


        private final ContactService contactService;
        private AddressService addressService;
        private final ContactGroupService contactGroupService;

        @DgsDataLoader(name = DgsConstants.ADDRESS.TYPE_NAME )
        public MappedBatchLoader<Long, Address> addressesBatchLoader =
                keys -> CompletableFuture.supplyAsync(() -> addressService.getAddressesMappedByID(keys));

        @Autowired
        public GraphqlEndPoint(ContactService contactService, AddressService addressService, ContactGroupService contactGroupService) {
            this.contactService = contactService;
            this.addressService = addressService;
            this.contactGroupService = contactGroupService;
        }

        @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.Contacts)
        public List<Contact.NoRelations> contacts(@InputArgument("pageNum")int pageNum,
                                                  @InputArgument("pageSize") int pageSize, DgsDataFetchingEnvironment dfe) {
            return contactService.getPage(PageRequest.of(pageNum,pageSize,Sort.by("firstName").descending()));
        }

        @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.ContactGroups)
        public List<ContactGroup> contactGroups() {
            return contactGroupService.getAll();
        }

        @DgsData(parentType = DgsConstants.CONTACT.TYPE_NAME, field = DgsConstants.CONTACT.Address)
        public CompletableFuture<Address> address(DgsDataFetchingEnvironment dfe) {
            DataLoader<Long, Address> dataLoader = dfe.getDataLoader(DgsConstants.ADDRESS.TYPE_NAME);
            Long addressId;
            if (dfe.getSource() instanceof  Contact){
                addressId = ((Contact)dfe.getSource()).getAddressId();
            }else {
                addressId = ((Contact.NoRelations)dfe.getSource()).getAddressId();
            }
            if (addressId == null){
                return null;
            }
            return dataLoader.load(addressId);
        }

        @DgsData(parentType = DgsConstants.MUTATION.TYPE_NAME , field = DgsConstants.MUTATION.AddContact)
        public Contact addContact(@InputArgument Contact  contact) {
            return contactService.save(contact);
        }

        @DgsData(parentType = DgsConstants.MUTATION.TYPE_NAME , field = DgsConstants.MUTATION.UpdateContact)
        public Contact updateContact(@InputArgument Contact  contact, @InputArgument("contactId") long id) {
            return contactService.update(contact, id);
        }

        @DgsData(parentType = DgsConstants.MUTATION.TYPE_NAME, field = DgsConstants.MUTATION.DeleteContact)
        public boolean deleteContact(@InputArgument("contactId") long id) {
           return contactService.deleteById(id);
        }


}
