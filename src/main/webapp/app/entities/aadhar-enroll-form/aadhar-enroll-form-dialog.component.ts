import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AadharEnrollForm } from './aadhar-enroll-form.model';
import { AadharEnrollFormPopupService } from './aadhar-enroll-form-popup.service';
import { AadharEnrollFormService } from './aadhar-enroll-form.service';

@Component({
    selector: 'jhi-aadhar-enroll-form-dialog',
    templateUrl: './aadhar-enroll-form-dialog.component.html'
})
export class AadharEnrollFormDialogComponent implements OnInit {

    aadharEnrollForm: AadharEnrollForm;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private aadharEnrollFormService: AadharEnrollFormService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.aadharEnrollForm.id !== undefined) {
            this.subscribeToSaveResponse(
                this.aadharEnrollFormService.update(this.aadharEnrollForm));
        } else {
            this.subscribeToSaveResponse(
                this.aadharEnrollFormService.create(this.aadharEnrollForm));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<AadharEnrollForm>>) {
        result.subscribe((res: HttpResponse<AadharEnrollForm>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: AadharEnrollForm) {
        this.eventManager.broadcast({ name: 'aadharEnrollFormListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-aadhar-enroll-form-popup',
    template: ''
})
export class AadharEnrollFormPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private aadharEnrollFormPopupService: AadharEnrollFormPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.aadharEnrollFormPopupService
                    .open(AadharEnrollFormDialogComponent as Component, params['id']);
            } else {
                this.aadharEnrollFormPopupService
                    .open(AadharEnrollFormDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
