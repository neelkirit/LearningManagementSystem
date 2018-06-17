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
    'EXCEL'
}

export const enum ContentStyle {
    'ITALICS',
    'BOLD',
    'BOLD_ITALICS',
    'CAPITALS',
    'NORMAL'
}

export class Template implements BaseEntity {
    constructor(
        public id?: number,
        public contentType?: ContentType,
        public contentPrefix?: string,
        public contentStyle?: ContentStyle,
        public name?: string,
    ) {
    }
}
