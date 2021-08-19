import { Contact, ContactGroup } from '../../types';
import { Component, ElementRef, Inject, InjectionToken, Input, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormBuilder, FormArray, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { ValidationsHelper } from 'src/app/helpers';
import { Observable, of } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-add-edit-contact-dialog',
  templateUrl: './add-edit-contact-dialog.component.html',
  styleUrls: ['./add-edit-contact-dialog.component.scss']
})
export class AddEditContactDialogComponent implements OnInit {

  @ViewChild('imgElement') img:ElementRef | undefined = undefined;
  @ViewChild('canvesElement') canvas:ElementRef<HTMLCanvasElement> | undefined = undefined;
  imageSrc:string;
  onSubmit:Function
  imageFilters : {[key: string]: string}
  phoneNumbers: FormArray;
  groups: ContactGroup[];
  contact: Contact;
  contactForm: FormGroup;
  formSubmitClicked = false;

  constructor(private fb: FormBuilder, @Inject(MAT_DIALOG_DATA) public data: any) {
    this.contact = data.contact;
    this.groups = data.groups;
  }

  ngOnInit(): void {
    this.onSubmit =  ()=>{
      this.formSubmitClicked = true;
      if(this.contactForm.valid){
        this.data.onSubmit(this.contactForm)
      }
    }
    this.initializeForm();
  }



  initializeForm(): void {
    const { contact, fb } = this;

    this.phoneNumbers = fb.array(contact?.phoneNumbers?.length ? 
                                    contact.phoneNumbers.map(phone => phone.number) 
                                  : [''])
    const formSchema = {
      firstName: fb.control(contact?.firstName || '', [Validators.required, Validators.maxLength(15)]),
      lastName: fb.control(contact?.lastName || '', [Validators.required, Validators.maxLength(15)]),
      nickName: fb.control(contact?.nickName || '', [Validators.maxLength(15)]),
      address: fb.group({
        city: fb.control(contact?.address?.city || ''),
        street: fb.control(contact?.address?.street || ''),
        houseNum: fb.control(contact?.address?.houseNum || ''),
        apartmentNum: fb.control(contact?.address?.apartmentNum || ''),
      }),
      phoneNumbers: this.phoneNumbers
    }
    this.contactForm = fb.group(formSchema)
  }

  private validatePhoneNum(phoneNumber: AbstractControl) {
    if (phoneNumber.value.match(/^[0][5][0|2|3|4|5|9]{1}[-]{0,1}[0-9]{7}$/)) {
      return null;
    }
    return { phoneNumber: true }
  }

  displayGroupFn(group: ContactGroup | null): any {
    return group?.name
  }

  private getFieldControlObject(fieldPath: string): AbstractControl {
    return this.contactForm.controls[fieldPath];
  }

  getErrorMessage(fieldPath: string): string {
    const formControl = this.getFieldControlObject(fieldPath);
    for (const [key, value] of Object.entries(ValidationsHelper)) {
      if (formControl.errors?.hasOwnProperty(key)) {
        if (typeof value === 'function') {
          return value(formControl.errors[key])
        }
        return <string>value;
      }
    }
    return 'please insert valid input'
  }

  fieldHasError(fieldPath: string): boolean {
    const formControl = this.getFieldControlObject(fieldPath);
    return Boolean((this.formSubmitClicked || formControl?.touched) && formControl?.invalid);
    return false
  }

  removePhoneNumber(index:number) {
    this.phoneNumbers.removeAt(index);
  }
  
  addPhoneNumber = () => {
    this.phoneNumbers.push(this.fb.control('',this.validatePhoneNum));
  }

}
