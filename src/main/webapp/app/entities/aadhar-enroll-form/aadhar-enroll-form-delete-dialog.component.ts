import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AadharEnrollForm } from './aadhar-enroll-form.model';
import { AadharEnrollFormPopupService } from './aadhar-enroll-form-popup.service';
import { AadharEnrollFormService } from './aadhar-enroll-form.service';

@Component({
    selector: 'jhi-aadhar-enroll-form-delete-dialog',
    templateUrl: './aadhar-enroll-form-delete-dialog.component.html'
})
export class AadharEnrollFormDeleteDialogComponent {

    aadharEnrollForm: AadharEnrollForm;

    constructor(
        private aadharEnrollFormService: AadharEnrollFormService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.aadharEnrollFormService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'aadharEnrollFormListModification',
                content: 'Deleted an aadharEnrollForm'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-aadhar-enroll-form-delete-popup',
    template: ''
})
export class AadharEnrollFormDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private aadharEnrollFormPopupService: AadharEnrollFormPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.aadharEnrollFormPopupService
                .open(AadharEnrollFormDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
