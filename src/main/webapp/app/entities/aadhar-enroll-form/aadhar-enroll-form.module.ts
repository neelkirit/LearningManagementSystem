import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AmoghServerSharedModule } from '../../shared';
import {
    AadharEnrollFormService,
    AadharEnrollFormPopupService,
    AadharEnrollFormComponent,
    AadharEnrollFormDetailComponent,
    AadharEnrollFormDialogComponent,
    AadharEnrollFormPopupComponent,
    AadharEnrollFormDeletePopupComponent,
    AadharEnrollFormDeleteDialogComponent,
    aadharEnrollFormRoute,
    aadharEnrollFormPopupRoute,
} from './';

const ENTITY_STATES = [
    ...aadharEnrollFormRoute,
    ...aadharEnrollFormPopupRoute,
];

@NgModule({
    imports: [
        AmoghServerSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AadharEnrollFormComponent,
        AadharEnrollFormDetailComponent,
        AadharEnrollFormDialogComponent,
        AadharEnrollFormDeleteDialogComponent,
        AadharEnrollFormPopupComponent,
        AadharEnrollFormDeletePopupComponent,
    ],
    entryComponents: [
        AadharEnrollFormComponent,
        AadharEnrollFormDialogComponent,
        AadharEnrollFormPopupComponent,
        AadharEnrollFormDeleteDialogComponent,
        AadharEnrollFormDeletePopupComponent,
    ],
    providers: [
        AadharEnrollFormService,
        AadharEnrollFormPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AmoghServerAadharEnrollFormModule {}
