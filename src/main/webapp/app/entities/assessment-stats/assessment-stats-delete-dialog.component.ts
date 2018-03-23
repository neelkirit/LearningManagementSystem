import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AssessmentStats } from './assessment-stats.model';
import { AssessmentStatsPopupService } from './assessment-stats-popup.service';
import { AssessmentStatsService } from './assessment-stats.service';

@Component({
    selector: 'jhi-assessment-stats-delete-dialog',
    templateUrl: './assessment-stats-delete-dialog.component.html'
})
export class AssessmentStatsDeleteDialogComponent {

    assessmentStats: AssessmentStats;

    constructor(
        private assessmentStatsService: AssessmentStatsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.assessmentStatsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'assessmentStatsListModification',
                content: 'Deleted an assessmentStats'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-assessment-stats-delete-popup',
    template: ''
})
export class AssessmentStatsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private assessmentStatsPopupService: AssessmentStatsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.assessmentStatsPopupService
                .open(AssessmentStatsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
