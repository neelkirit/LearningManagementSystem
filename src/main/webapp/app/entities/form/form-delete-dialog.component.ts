import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Form } from './form.model';
import { FormPopupService } from './form-popup.service';
import { FormService } from './form.service';

@Component({
    selector: 'jhi-form-delete-dialog',
    templateUrl: './form-delete-dialog.component.html'
})
export class FormDeleteDialogComponent {

    form: Form;

    constructor(
        private formService: FormService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.formService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'formListModification',
                content: 'Deleted an form'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-form-delete-popup',
    template: ''
})
export class FormDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private formPopupService: FormPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.formPopupService
                .open(FormDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
