import { Contact } from './../types/index';

export const ValidationsHelper =  {
    required:"required",
    phoneNumber:'please enter valid Phone number',
    minlength:(minlengthError:any)=>`min Length is ${minlengthError.requiredLength}`,
    maxlength:(maxlengthError:any)=>`max Length is ${maxlengthError.requiredLength}`,
}
export const getContactDisplayName = (contact :Contact)=>{
    return contact.nickName ? contact.nickName : `${contact.firstName} ${contact.lastName}`
  }
  
export type PhoneTypeDisplayData = {
    icon:string,
    text:string
}