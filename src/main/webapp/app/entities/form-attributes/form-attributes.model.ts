import { BaseEntity } from './../../shared';

export class FormAttributes implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public type?: string,
        public isMandatory?: boolean,
        public content?: string,
        public formId?: number,
    ) {
        this.isMandatory = false;
    }
}
