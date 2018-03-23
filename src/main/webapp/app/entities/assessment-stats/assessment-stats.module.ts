import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AmoghServerSharedModule } from '../../shared';
import { AmoghServerAdminModule } from '../../admin/admin.module';
import {
    AssessmentStatsService,
    AssessmentStatsPopupService,
    AssessmentStatsComponent,
    AssessmentStatsDetailComponent,
    AssessmentStatsDialogComponent,
    AssessmentStatsPopupComponent,
    AssessmentStatsDeletePopupComponent,
    AssessmentStatsDeleteDialogComponent,
    assessmentStatsRoute,
    assessmentStatsPopupRoute,
    AssessmentStatsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...assessmentStatsRoute,
    ...assessmentStatsPopupRoute,
];

@NgModule({
    imports: [
        AmoghServerSharedModule,
        AmoghServerAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AssessmentStatsComponent,
        AssessmentStatsDetailComponent,
        AssessmentStatsDialogComponent,
        AssessmentStatsDeleteDialogComponent,
        AssessmentStatsPopupComponent,
        AssessmentStatsDeletePopupComponent,
    ],
    entryComponents: [
        AssessmentStatsComponent,
        AssessmentStatsDialogComponent,
        AssessmentStatsPopupComponent,
        AssessmentStatsDeleteDialogComponent,
        AssessmentStatsDeletePopupComponent,
    ],
    providers: [
        AssessmentStatsService,
        AssessmentStatsPopupService,
        AssessmentStatsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AmoghServerAssessmentStatsModule {}
