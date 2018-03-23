/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { AmoghServerTestModule } from '../../../test.module';
import { AssessmentStatsDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/assessment-stats/assessment-stats-delete-dialog.component';
import { AssessmentStatsService } from '../../../../../../main/webapp/app/entities/assessment-stats/assessment-stats.service';

describe('Component Tests', () => {

    describe('AssessmentStats Management Delete Component', () => {
        let comp: AssessmentStatsDeleteDialogComponent;
        let fixture: ComponentFixture<AssessmentStatsDeleteDialogComponent>;
        let service: AssessmentStatsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AmoghServerTestModule],
                declarations: [AssessmentStatsDeleteDialogComponent],
                providers: [
                    AssessmentStatsService
                ]
            })
            .overrideTemplate(AssessmentStatsDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AssessmentStatsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AssessmentStatsService);
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
