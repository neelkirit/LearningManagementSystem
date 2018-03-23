import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ExerciseStats } from './exercise-stats.model';
import { ExerciseStatsPopupService } from './exercise-stats-popup.service';
import { ExerciseStatsService } from './exercise-stats.service';

@Component({
    selector: 'jhi-exercise-stats-delete-dialog',
    templateUrl: './exercise-stats-delete-dialog.component.html'
})
export class ExerciseStatsDeleteDialogComponent {

    exerciseStats: ExerciseStats;

    constructor(
        private exerciseStatsService: ExerciseStatsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.exerciseStatsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'exerciseStatsListModification',
                content: 'Deleted an exerciseStats'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-exercise-stats-delete-popup',
    template: ''
})
export class ExerciseStatsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private exerciseStatsPopupService: ExerciseStatsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.exerciseStatsPopupService
                .open(ExerciseStatsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
