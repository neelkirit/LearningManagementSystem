import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AmoghServerSharedModule } from '../../shared';
import {
    AssessmentService,
    AssessmentPopupService,
    AssessmentComponent,
    AssessmentDetailComponent,
    AssessmentDialogComponent,
    AssessmentPopupComponent,
    AssessmentDeletePopupComponent,
    AssessmentDeleteDialogComponent,
    assessmentRoute,
    assessmentPopupRoute,
    AssessmentResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...assessmentRoute,
    ...assessmentPopupRoute,
];

@NgModule({
    imports: [
        AmoghServerSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AssessmentComponent,
        AssessmentDetailComponent,
        AssessmentDialogComponent,
        AssessmentDeleteDialogComponent,
        AssessmentPopupComponent,
        AssessmentDeletePopupComponent,
    ],
    entryComponents: [
        AssessmentComponent,
        AssessmentDialogComponent,
        AssessmentPopupComponent,
        AssessmentDeleteDialogComponent,
        AssessmentDeletePopupComponent,
    ],
    providers: [
        AssessmentService,
        AssessmentPopupService,
        AssessmentResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AmoghServerAssessmentModule {}
