/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { AmoghServerTestModule } from '../../../test.module';
import { AadharEnrollFormDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/aadhar-enroll-form/aadhar-enroll-form-delete-dialog.component';
import { AadharEnrollFormService } from '../../../../../../main/webapp/app/entities/aadhar-enroll-form/aadhar-enroll-form.service';

describe('Component Tests', () => {

    describe('AadharEnrollForm Management Delete Component', () => {
        let comp: AadharEnrollFormDeleteDialogComponent;
        let fixture: ComponentFixture<AadharEnrollFormDeleteDialogComponent>;
        let service: AadharEnrollFormService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AmoghServerTestModule],
                declarations: [AadharEnrollFormDeleteDialogComponent],
                providers: [
                    AadharEnrollFormService
                ]
            })
            .overrideTemplate(AadharEnrollFormDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AadharEnrollFormDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AadharEnrollFormService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
