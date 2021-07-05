import { gql } from "apollo-angular";

export const GET_CONTACTS_QUERY = gql`
query getContactsPage($pageNum: Int!,$pageSize: Int!) {
    contacts(pageNum:$pageNum,pageSize:$pageSize){ 	
        id,
        firstName,
        lastName,
        nickName,
        contactGroup {
            id,
            name
        },
        address {
          city,
          street,
          houseNum,
          apartmentNum
        }
        phoneNumbers{
          number
        }
      }
    }
`;
export const GET_CONTACT_GROUPS_QUERY = gql`
 {
    contactGroups{id,name}
    }
`;
export const UPDATE_CONTACT = gql`mutation{
  updateContact(contact:$contact,contactId:$contactId) {
  id,
  firstName,
  lastName,
  nickName,
  address{
    city,
    street,
    houseNum,
    apartmentNum
  }
  phoneNumbers{
    number
  }
	}
}`
export const ADD_CONTACT = gql`mutation AddContact($contact: ContactInput!){
  addContact(contact:$contact) {
  id,
  firstName,
  lastName,
  nickName,
  address{
    city,
    street,
    houseNum,
    apartmentNum
  }
  phoneNumbers{
    number
  }
	}
}`
export const DELETE_CONTACT = gql`
mutation DeleteContact($contactId: Int!){
  deleteContact(contactId:$contactId)
}`