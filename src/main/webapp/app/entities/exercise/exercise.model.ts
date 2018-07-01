import { BaseEntity } from './../../shared';

export const enum ContentType {
    'ALPHABET',
    'NUMBER',
    'CURRENCY',
    'WORD',
    'SENTENCE',
    'CHECKBOX',
    'COMBOBOX',
    'RADIOBUTTON',
    'IMAGE',
    'FORM',
    'EXCEL',
    'DATE',
    'MATH'
}

export class Exercise implements BaseEntity {
    constructor(
        public id?: number,
        public contentType?: ContentType,
        public content?: string,
        public answer?: string,
        public topicId?: number,
        public templateId?: number,
    ) {
    }
}
