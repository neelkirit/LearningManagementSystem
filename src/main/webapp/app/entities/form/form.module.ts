import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AmoghServerSharedModule } from '../../shared';
import {
    FormService,
    FormPopupService,
    FormComponent,
    FormDetailComponent,
    FormDialogComponent,
    FormPopupComponent,
    FormDeletePopupComponent,
    FormDeleteDialogComponent,
    formRoute,
    formPopupRoute,
} from './';

const ENTITY_STATES = [
    ...formRoute,
    ...formPopupRoute,
];

@NgModule({
    imports: [
        AmoghServerSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        FormComponent,
        FormDetailComponent,
        FormDialogComponent,
        FormDeleteDialogComponent,
        FormPopupComponent,
        FormDeletePopupComponent,
    ],
    entryComponents: [
        FormComponent,
        FormDialogComponent,
        FormPopupComponent,
        FormDeleteDialogComponent,
        FormDeletePopupComponent,
    ],
    providers: [
        FormService,
        FormPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AmoghServerFormModule {}
