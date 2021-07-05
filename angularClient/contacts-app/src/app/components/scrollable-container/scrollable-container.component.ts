import { FormGroup } from '@angular/forms';
import { Contact, ContactGroup } from '../../types';
import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { AddEditContactDialogComponent } from '../add-edit-contact-dialog/add-edit-contact-dialog.component';
import {Apollo, gql} from 'apollo-angular';
import { ADD_CONTACT, DELETE_CONTACT, GET_CONTACTS_QUERY, UPDATE_CONTACT } from 'src/app/helpers/gqlQueries';
import { ConfirmationDialogComponent } from '../../confirmation-dialog/confirmation-dialog.component';
import { getContactDisplayName } from 'src/app/helpers';


@Component({
  selector: 'app-scrollable-container',
  templateUrl: './scrollable-container.component.html',
  styleUrls: ['./scrollable-container.component.scss'],
})
export class ScrollableContainerComponent implements OnInit {

  constructor(private dialog: MatDialog, private apollo : Apollo) { }

  contacts :Contact[] = [];
  pageNum = 0;
  pageSize = 5;
  hasAnotherPage = true;
  loading = true;
  addEditDialogRef : MatDialogRef<AddEditContactDialogComponent>;
  deleteConfirmationRef : MatDialogRef<ConfirmationDialogComponent>;

  ngOnInit(): void {
    this.fetchContacts()
  }

  groups : ContactGroup[] = [{id:1, name:"group1"}, {id:2, name:"group2"}]

  openContactDialog(contact:any)  : void {
    this.addEditDialogRef = this.dialog.open(AddEditContactDialogComponent, {
      width: '600px',
      height:"600px",
      autoFocus:false,
      data:{contact, groups: this.groups, onSubmit: contact ? this.editContact : this.addContact }
    });
  }

  openConfirmationDialog(contact:Contact)  : void {
    this.deleteConfirmationRef = this.dialog.open(ConfirmationDialogComponent, {

      autoFocus:false,
      data:{title:`Are you sure you want to delete ${getContactDisplayName(contact)}?`, onConfirm:()=> this.deleteContact(contact)}
    });
  }

  addContact = (contactForm :FormGroup)=>{
    const contctToAdd = contactForm.value;
    contctToAdd.phoneNumbers = contctToAdd.phoneNumbers.map((number:string)=>({number}))
    this.apollo.mutate({
      mutation: ADD_CONTACT,
      variables:{
        contact:contctToAdd
      }
    }).subscribe((result: any) => {
      if(!result.error ){
        this.contacts.push(result.data.addContact)
        this.addEditDialogRef.close()
      }
    })
  }

  editContact(contactForm :FormGroup){
    const contctToUpdate = contactForm.value;
    contctToUpdate.phoneNumbers = contctToUpdate.phoneNumbers.map((number:string)=>({number}))
    this.apollo.mutate({
      mutation: UPDATE_CONTACT,
      variables:{
        contact:contctToUpdate,
        contactId:contctToUpdate.id
      }
    }).subscribe((result: any) => {
      if(!result.error ){
        const contactIndex = this.contacts.findIndex((contact=>contact.id === contctToUpdate.id))
        this.contacts[contactIndex] = result.data.updateContact
        this.addEditDialogRef.close()
      }
    })
  }

  deleteContact(contactToDelete:Contact){
    this.apollo.mutate({
      mutation: DELETE_CONTACT,
      variables:{
        contactId:contactToDelete.id
      }
    }).subscribe((result: any) => {
      if(!result.error ){
        this.contacts = this.contacts.filter((contact)=> contact.id !== contactToDelete.id)
        this.deleteConfirmationRef.close()
      }
    })

  }

  fetchNextPage(){
    if(this.hasAnotherPage && !this.loading){
      this.pageNum ++;
      this.fetchContacts()
    }
  }

  fetchContacts(){
    this.loading = true;
    this.apollo.watchQuery({
      query: GET_CONTACTS_QUERY,
      variables:{
        pageNum:this.pageNum, pageSize:this.pageSize
      }
    }).valueChanges.subscribe((result: any) => {
      this.contacts = [...this.contacts , ...result?.data?.contacts];
      if(!result.error && !result?.data?.contacts?.length){
        this.hasAnotherPage = false;
      }
      this.loading = false;
      // this.loading = result.loading;
      // this.error = result.error;
    })
  }
}
