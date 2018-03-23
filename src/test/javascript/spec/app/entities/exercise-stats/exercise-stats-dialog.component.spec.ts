/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { AmoghServerTestModule } from '../../../test.module';
import { ExerciseStatsDialogComponent } from '../../../../../../main/webapp/app/entities/exercise-stats/exercise-stats-dialog.component';
import { ExerciseStatsService } from '../../../../../../main/webapp/app/entities/exercise-stats/exercise-stats.service';
import { ExerciseStats } from '../../../../../../main/webapp/app/entities/exercise-stats/exercise-stats.model';
import { UserService } from '../../../../../../main/webapp/app/shared';
import { ExerciseService } from '../../../../../../main/webapp/app/entities/exercise';

describe('Component Tests', () => {

    describe('ExerciseStats Management Dialog Component', () => {
        let comp: ExerciseStatsDialogComponent;
        let fixture: ComponentFixture<ExerciseStatsDialogComponent>;
        let service: ExerciseStatsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AmoghServerTestModule],
                declarations: [ExerciseStatsDialogComponent],
                providers: [
                    UserService,
                    ExerciseService,
                    ExerciseStatsService
                ]
            })
            .overrideTemplate(ExerciseStatsDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ExerciseStatsDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExerciseStatsService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ExerciseStats(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.exerciseStats = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'exerciseStatsListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ExerciseStats();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.exerciseStats = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'exerciseStatsListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
