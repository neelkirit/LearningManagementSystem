/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { AmoghServerTestModule } from '../../../test.module';
import { ExerciseStatsDetailComponent } from '../../../../../../main/webapp/app/entities/exercise-stats/exercise-stats-detail.component';
import { ExerciseStatsService } from '../../../../../../main/webapp/app/entities/exercise-stats/exercise-stats.service';
import { ExerciseStats } from '../../../../../../main/webapp/app/entities/exercise-stats/exercise-stats.model';

describe('Component Tests', () => {

    describe('ExerciseStats Management Detail Component', () => {
        let comp: ExerciseStatsDetailComponent;
        let fixture: ComponentFixture<ExerciseStatsDetailComponent>;
        let service: ExerciseStatsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AmoghServerTestModule],
                declarations: [ExerciseStatsDetailComponent],
                providers: [
                    ExerciseStatsService
                ]
            })
            .overrideTemplate(ExerciseStatsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ExerciseStatsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExerciseStatsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new ExerciseStats(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.exerciseStats).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
