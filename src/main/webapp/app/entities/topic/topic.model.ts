import { BaseEntity } from './../../shared';

export class Topic implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public courseId?: number,
    ) {
    }
}
