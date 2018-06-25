import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Form } from './form.model';
import { FormPopupService } from './form-popup.service';
import { FormService } from './form.service';

@Component({
    selector: 'jhi-form-dialog',
    templateUrl: './form-dialog.component.html'
})
export class FormDialogComponent implements OnInit {

    form: Form;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private formService: FormService,
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
        if (this.form.id !== undefined) {
            this.subscribeToSaveResponse(
                this.formService.update(this.form));
        } else {
            this.subscribeToSaveResponse(
                this.formService.create(this.form));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Form>>) {
        result.subscribe((res: HttpResponse<Form>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Form) {
        this.eventManager.broadcast({ name: 'formListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-form-popup',
    template: ''
})
export class FormPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private formPopupService: FormPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.formPopupService
                    .open(FormDialogComponent as Component, params['id']);
            } else {
                this.formPopupService
                    .open(FormDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
