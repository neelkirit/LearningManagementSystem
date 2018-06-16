import { BaseEntity } from './../../shared';

export class AadharEnrollForm implements BaseEntity {
    constructor(
        public id?: number,
        public preEnrollmentID?: string,
        public tinNumber?: string,
        public fullName?: string,
        public gender?: string,
        public age?: string,
        public dob?: string,
        public declared?: string,
        public verified?: string,
        public careOf?: string,
        public careOfName?: string,
        public houseNumber?: string,
        public street?: string,
        public landmark?: string,
        public area?: string,
        public village?: string,
        public postOffice?: string,
        public district?: string,
        public subDistrict?: string,
        public state?: string,
        public email?: string,
        public modileNumber?: string,
        public pinCode?: string,
        public detailsOf?: string,
        public detailsOfName?: string,
        public aadharNumber?: string,
        public verficationType?: string,
        public forDocumentBased?: string,
        public forIntroducerBased?: string,
        public forHoFBased?: string,
        public imageUrl?: string,
    ) {
    }
}
