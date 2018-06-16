import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { AadharEnrollForm } from './aadhar-enroll-form.model';
import { AadharEnrollFormService } from './aadhar-enroll-form.service';

@Injectable()
export class AadharEnrollFormPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private aadharEnrollFormService: AadharEnrollFormService

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
                this.aadharEnrollFormService.find(id)
                    .subscribe((aadharEnrollFormResponse: HttpResponse<AadharEnrollForm>) => {
                        const aadharEnrollForm: AadharEnrollForm = aadharEnrollFormResponse.body;
                        this.ngbModalRef = this.aadharEnrollFormModalRef(component, aadharEnrollForm);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.aadharEnrollFormModalRef(component, new AadharEnrollForm());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    aadharEnrollFormModalRef(component: Component, aadharEnrollForm: AadharEnrollForm): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.aadharEnrollForm = aadharEnrollForm;
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
