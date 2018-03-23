import { BaseEntity } from './../../shared';

export class AssessmentStats implements BaseEntity {
    constructor(
        public id?: number,
        public score?: number,
        public userId?: number,
        public assessmentId?: number,
    ) {
    }
}
