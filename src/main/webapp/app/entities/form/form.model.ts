import { BaseEntity } from './../../shared';

export class Form implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
    ) {
    }
}
