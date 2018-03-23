import { BaseEntity } from './../../shared';

export class ExerciseStats implements BaseEntity {
    constructor(
        public id?: number,
        public status?: boolean,
        public userId?: number,
        public exerciseId?: number,
    ) {
        this.status = false;
    }
}
