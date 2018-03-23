import { BaseEntity } from './../../shared';

export const enum ContentType {
    'ALPHABET',
    'NUMBER',
    'CURRENCY',
    'WORD'
}

export class Template implements BaseEntity {
    constructor(
        public id?: number,
        public style?: string,
        public contentType?: ContentType,
        public contentPrefix?: string,
    ) {
    }
}
