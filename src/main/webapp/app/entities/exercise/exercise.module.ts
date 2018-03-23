import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AmoghServerSharedModule } from '../../shared';
import {
    ExerciseService,
    ExercisePopupService,
    ExerciseComponent,
    ExerciseDetailComponent,
    ExerciseDialogComponent,
    ExercisePopupComponent,
    ExerciseDeletePopupComponent,
    ExerciseDeleteDialogComponent,
    exerciseRoute,
    exercisePopupRoute,
    ExerciseResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...exerciseRoute,
    ...exercisePopupRoute,
];

@NgModule({
    imports: [
        AmoghServerSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ExerciseComponent,
        ExerciseDetailComponent,
        ExerciseDialogComponent,
        ExerciseDeleteDialogComponent,
        ExercisePopupComponent,
        ExerciseDeletePopupComponent,
    ],
    entryComponents: [
        ExerciseComponent,
        ExerciseDialogComponent,
        ExercisePopupComponent,
        ExerciseDeleteDialogComponent,
        ExerciseDeletePopupComponent,
    ],
    providers: [
        ExerciseService,
        ExercisePopupService,
        ExerciseResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AmoghServerExerciseModule {}
