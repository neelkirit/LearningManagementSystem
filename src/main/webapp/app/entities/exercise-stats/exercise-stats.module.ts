import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AmoghServerSharedModule } from '../../shared';
import { AmoghServerAdminModule } from '../../admin/admin.module';
import {
    ExerciseStatsService,
    ExerciseStatsPopupService,
    ExerciseStatsComponent,
    ExerciseStatsDetailComponent,
    ExerciseStatsDialogComponent,
    ExerciseStatsPopupComponent,
    ExerciseStatsDeletePopupComponent,
    ExerciseStatsDeleteDialogComponent,
    exerciseStatsRoute,
    exerciseStatsPopupRoute,
    ExerciseStatsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...exerciseStatsRoute,
    ...exerciseStatsPopupRoute,
];

@NgModule({
    imports: [
        AmoghServerSharedModule,
        AmoghServerAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ExerciseStatsComponent,
        ExerciseStatsDetailComponent,
        ExerciseStatsDialogComponent,
        ExerciseStatsDeleteDialogComponent,
        ExerciseStatsPopupComponent,
        ExerciseStatsDeletePopupComponent,
    ],
    entryComponents: [
        ExerciseStatsComponent,
        ExerciseStatsDialogComponent,
        ExerciseStatsPopupComponent,
        ExerciseStatsDeleteDialogComponent,
        ExerciseStatsDeletePopupComponent,
    ],
    providers: [
        ExerciseStatsService,
        ExerciseStatsPopupService,
        ExerciseStatsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AmoghServerExerciseStatsModule {}
