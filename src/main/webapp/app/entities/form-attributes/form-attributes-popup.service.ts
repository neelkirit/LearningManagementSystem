import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { FormAttributes } from './form-attributes.model';
import { FormAttributesService } from './form-attributes.service';

@Injectable()
export class FormAttributesPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private formAttributesService: FormAttributesService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.formAttributesService.find(id)
                    .subscribe((formAttributesResponse: HttpResponse<FormAttributes>) => {
                        const formAttributes: FormAttributes = formAttributesResponse.body;
                        this.ngbModalRef = this.formAttributesModalRef(component, formAttributes);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.formAttributesModalRef(component, new FormAttributes());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    formAttributesModalRef(component: Component, formAttributes: FormAttributes): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.formAttributes = formAttributes;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
