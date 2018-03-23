import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Assessment } from './assessment.model';
import { AssessmentPopupService } from './assessment-popup.service';
import { AssessmentService } from './assessment.service';

@Component({
    selector: 'jhi-assessment-delete-dialog',
    templateUrl: './assessment-delete-dialog.component.html'
})
export class AssessmentDeleteDialogComponent {

    assessment: Assessment;

    constructor(
        private assessmentService: AssessmentService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.assessmentService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'assessmentListModification',
                content: 'Deleted an assessment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-assessment-delete-popup',
    template: ''
})
export class AssessmentDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private assessmentPopupService: AssessmentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.assessmentPopupService
                .open(AssessmentDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
