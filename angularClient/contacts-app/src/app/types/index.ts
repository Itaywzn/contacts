export interface Contact {
    id:number
firstName:string,
lastName:string,
nickName?:string,
photoPath?:string,
address: Address
contactGroup ?:ContactGroup
contactGroupId?: number
phoneNumbers?:[PhoneNumber]
}

export interface Address {
    city: string
    street: string
    houseNum: string
    apartmentNum?: string
}

export interface PhoneNumber {
    number: string
}


export interface ContactGroup {
    id:number
    name:string
}