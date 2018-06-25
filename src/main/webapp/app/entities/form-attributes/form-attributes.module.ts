import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AmoghServerSharedModule } from '../../shared';
import {
    FormAttributesService,
    FormAttributesPopupService,
    FormAttributesComponent,
    FormAttributesDetailComponent,
    FormAttributesDialogComponent,
    FormAttributesPopupComponent,
    FormAttributesDeletePopupComponent,
    FormAttributesDeleteDialogComponent,
    formAttributesRoute,
    formAttributesPopupRoute,
} from './';

const ENTITY_STATES = [
    ...formAttributesRoute,
    ...formAttributesPopupRoute,
];

@NgModule({
    imports: [
        AmoghServerSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        FormAttributesComponent,
        FormAttributesDetailComponent,
        FormAttributesDialogComponent,
        FormAttributesDeleteDialogComponent,
        FormAttributesPopupComponent,
        FormAttributesDeletePopupComponent,
    ],
    entryComponents: [
        FormAttributesComponent,
        FormAttributesDialogComponent,
        FormAttributesPopupComponent,
        FormAttributesDeleteDialogComponent,
        FormAttributesDeletePopupComponent,
    ],
    providers: [
        FormAttributesService,
        FormAttributesPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AmoghServerFormAttributesModule {}
