import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { FormAttributes } from './form-attributes.model';
import { FormAttributesPopupService } from './form-attributes-popup.service';
import { FormAttributesService } from './form-attributes.service';
import { Form, FormService } from '../form';

@Component({
    selector: 'jhi-form-attributes-dialog',
    templateUrl: './form-attributes-dialog.component.html'
})
export class FormAttributesDialogComponent implements OnInit {

    formAttributes: FormAttributes;
    isSaving: boolean;

    forms: Form[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private formAttributesService: FormAttributesService,
        private formService: FormService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.formService.query()
            .subscribe((res: HttpResponse<Form[]>) => { this.forms = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.formAttributes.id !== undefined) {
            this.subscribeToSaveResponse(
                this.formAttributesService.update(this.formAttributes));
        } else {
            this.subscribeToSaveResponse(
                this.formAttributesService.create(this.formAttributes));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<FormAttributes>>) {
        result.subscribe((res: HttpResponse<FormAttributes>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: FormAttributes) {
        this.eventManager.broadcast({ name: 'formAttributesListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackFormById(index: number, item: Form) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-form-attributes-popup',
    template: ''
})
export class FormAttributesPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private formAttributesPopupService: FormAttributesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.formAttributesPopupService
                    .open(FormAttributesDialogComponent as Component, params['id']);
            } else {
                this.formAttributesPopupService
                    .open(FormAttributesDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
