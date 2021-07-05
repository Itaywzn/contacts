import { Contact, Address } from '../../types';
import { Component, OnInit ,Input, ViewChild, AfterViewInit, Output, EventEmitter} from '@angular/core';
import {ElementRef,Renderer2} from '@angular/core';
import { getContactDisplayName } from 'src/app/helpers';

@Component({
  selector: 'app-contact-card',
  templateUrl: './contact-card.component.html',
  styleUrls: ['./contact-card.component.scss']
})
export class ContactCardComponent implements OnInit {


  constructor() { }
  @Output() onEdit = new EventEmitter<Contact>();
  @Output() onDelete = new EventEmitter<Contact>();
  @Input() contact:Contact;

  ngOnInit(): void {
  }

  getDisplayName = ()=>{
    return getContactDisplayName(this.contact)
  }
  getSubtitle = ()=>{
    return this.contact.nickName ? `${this.contact.firstName} ${this.contact.lastName}` : ''
  }

  handleDelete = ()=>{
    this.onDelete.emit(this.contact);
  }
  
  handleEdit = ()=>{
    this.onEdit.emit(this.contact);
  }

  hasAddress(){
   return Object.values(this.contact.address || {}).some(val => val  && val !== "Address")
  }
}
