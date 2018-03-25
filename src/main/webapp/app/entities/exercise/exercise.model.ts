import { BaseEntity } from './../../shared';

export const enum ContentType {
    'ALPHABET',
    'NUMBER',
    'CURRENCY',
    'WORD'
}

export class Exercise implements BaseEntity {
    constructor(
        public id?: number,
        public contentType?: ContentType,
        public content?: string,
        public topicId?: number,
        public templateId?: number,
    ) {
    }
}
