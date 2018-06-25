/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { AmoghServerTestModule } from '../../../test.module';
import { FormAttributesDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/form-attributes/form-attributes-delete-dialog.component';
import { FormAttributesService } from '../../../../../../main/webapp/app/entities/form-attributes/form-attributes.service';

describe('Component Tests', () => {

    describe('FormAttributes Management Delete Component', () => {
        let comp: FormAttributesDeleteDialogComponent;
        let fixture: ComponentFixture<FormAttributesDeleteDialogComponent>;
        let service: FormAttributesService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AmoghServerTestModule],
                declarations: [FormAttributesDeleteDialogComponent],
                providers: [
                    FormAttributesService
                ]
            })
            .overrideTemplate(FormAttributesDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FormAttributesDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FormAttributesService);
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
