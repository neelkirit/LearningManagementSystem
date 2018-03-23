/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { AmoghServerTestModule } from '../../../test.module';
import { AssessmentStatsDialogComponent } from '../../../../../../main/webapp/app/entities/assessment-stats/assessment-stats-dialog.component';
import { AssessmentStatsService } from '../../../../../../main/webapp/app/entities/assessment-stats/assessment-stats.service';
import { AssessmentStats } from '../../../../../../main/webapp/app/entities/assessment-stats/assessment-stats.model';
import { UserService } from '../../../../../../main/webapp/app/shared';
import { AssessmentService } from '../../../../../../main/webapp/app/entities/assessment';

describe('Component Tests', () => {

    describe('AssessmentStats Management Dialog Component', () => {
        let comp: AssessmentStatsDialogComponent;
        let fixture: ComponentFixture<AssessmentStatsDialogComponent>;
        let service: AssessmentStatsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AmoghServerTestModule],
                declarations: [AssessmentStatsDialogComponent],
                providers: [
                    UserService,
                    AssessmentService,
                    AssessmentStatsService
                ]
            })
            .overrideTemplate(AssessmentStatsDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AssessmentStatsDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AssessmentStatsService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new AssessmentStats(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.assessmentStats = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'assessmentStatsListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new AssessmentStats();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.assessmentStats = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'assessmentStatsListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
