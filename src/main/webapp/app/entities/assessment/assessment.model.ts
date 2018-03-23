import { BaseEntity } from './../../shared';

export class Assessment implements BaseEntity {
    constructor(
        public id?: number,
        public threshold?: number,
        public courseId?: number,
    ) {
    }
}
