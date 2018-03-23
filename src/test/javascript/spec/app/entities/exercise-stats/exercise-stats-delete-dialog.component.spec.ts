/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { AmoghServerTestModule } from '../../../test.module';
import { ExerciseStatsDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/exercise-stats/exercise-stats-delete-dialog.component';
import { ExerciseStatsService } from '../../../../../../main/webapp/app/entities/exercise-stats/exercise-stats.service';

describe('Component Tests', () => {

    describe('ExerciseStats Management Delete Component', () => {
        let comp: ExerciseStatsDeleteDialogComponent;
        let fixture: ComponentFixture<ExerciseStatsDeleteDialogComponent>;
        let service: ExerciseStatsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AmoghServerTestModule],
                declarations: [ExerciseStatsDeleteDialogComponent],
                providers: [
                    ExerciseStatsService
                ]
            })
            .overrideTemplate(ExerciseStatsDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ExerciseStatsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExerciseStatsService);
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
