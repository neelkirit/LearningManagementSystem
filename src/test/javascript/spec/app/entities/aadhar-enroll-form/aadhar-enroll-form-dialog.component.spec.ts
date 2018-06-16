/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { AmoghServerTestModule } from '../../../test.module';
import { AadharEnrollFormDialogComponent } from '../../../../../../main/webapp/app/entities/aadhar-enroll-form/aadhar-enroll-form-dialog.component';
import { AadharEnrollFormService } from '../../../../../../main/webapp/app/entities/aadhar-enroll-form/aadhar-enroll-form.service';
import { AadharEnrollForm } from '../../../../../../main/webapp/app/entities/aadhar-enroll-form/aadhar-enroll-form.model';

describe('Component Tests', () => {

    describe('AadharEnrollForm Management Dialog Component', () => {
        let comp: AadharEnrollFormDialogComponent;
        let fixture: ComponentFixture<AadharEnrollFormDialogComponent>;
        let service: AadharEnrollFormService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AmoghServerTestModule],
                declarations: [AadharEnrollFormDialogComponent],
                providers: [
                    AadharEnrollFormService
                ]
            })
            .overrideTemplate(AadharEnrollFormDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AadharEnrollFormDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AadharEnrollFormService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new AadharEnrollForm(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.aadharEnrollForm = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'aadharEnrollFormListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new AadharEnrollForm();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.aadharEnrollForm = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'aadharEnrollFormListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
