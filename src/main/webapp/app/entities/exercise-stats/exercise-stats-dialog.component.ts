import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ExerciseStats } from './exercise-stats.model';
import { ExerciseStatsPopupService } from './exercise-stats-popup.service';
import { ExerciseStatsService } from './exercise-stats.service';
import { User, UserService } from '../../shared';
import { Exercise, ExerciseService } from '../exercise';

@Component({
    selector: 'jhi-exercise-stats-dialog',
    templateUrl: './exercise-stats-dialog.component.html'
})
export class ExerciseStatsDialogComponent implements OnInit {

    exerciseStats: ExerciseStats;
    isSaving: boolean;

    users: User[];

    exercises: Exercise[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private exerciseStatsService: ExerciseStatsService,
        private userService: UserService,
        private exerciseService: ExerciseService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.exerciseService.query()
            .subscribe((res: HttpResponse<Exercise[]>) => { this.exercises = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.exerciseStats.id !== undefined) {
            this.subscribeToSaveResponse(
                this.exerciseStatsService.update(this.exerciseStats));
        } else {
            this.subscribeToSaveResponse(
                this.exerciseStatsService.create(this.exerciseStats));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ExerciseStats>>) {
        result.subscribe((res: HttpResponse<ExerciseStats>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ExerciseStats) {
        this.eventManager.broadcast({ name: 'exerciseStatsListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackExerciseById(index: number, item: Exercise) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-exercise-stats-popup',
    template: ''
})
export class ExerciseStatsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private exerciseStatsPopupService: ExerciseStatsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.exerciseStatsPopupService
                    .open(ExerciseStatsDialogComponent as Component, params['id']);
            } else {
                this.exerciseStatsPopupService
                    .open(ExerciseStatsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
