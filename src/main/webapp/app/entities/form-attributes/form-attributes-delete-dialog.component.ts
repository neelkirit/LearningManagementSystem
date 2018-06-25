import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { FormAttributes } from './form-attributes.model';
import { FormAttributesPopupService } from './form-attributes-popup.service';
import { FormAttributesService } from './form-attributes.service';

@Component({
    selector: 'jhi-form-attributes-delete-dialog',
    templateUrl: './form-attributes-delete-dialog.component.html'
})
export class FormAttributesDeleteDialogComponent {

    formAttributes: FormAttributes;

    constructor(
        private formAttributesService: FormAttributesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.formAttributesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'formAttributesListModification',
                content: 'Deleted an formAttributes'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-form-attributes-delete-popup',
    template: ''
})
export class FormAttributesDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private formAttributesPopupService: FormAttributesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.formAttributesPopupService
                .open(FormAttributesDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
