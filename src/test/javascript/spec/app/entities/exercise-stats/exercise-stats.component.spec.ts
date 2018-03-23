/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AmoghServerTestModule } from '../../../test.module';
import { ExerciseStatsComponent } from '../../../../../../main/webapp/app/entities/exercise-stats/exercise-stats.component';
import { ExerciseStatsService } from '../../../../../../main/webapp/app/entities/exercise-stats/exercise-stats.service';
import { ExerciseStats } from '../../../../../../main/webapp/app/entities/exercise-stats/exercise-stats.model';

describe('Component Tests', () => {

    describe('ExerciseStats Management Component', () => {
        let comp: ExerciseStatsComponent;
        let fixture: ComponentFixture<ExerciseStatsComponent>;
        let service: ExerciseStatsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AmoghServerTestModule],
                declarations: [ExerciseStatsComponent],
                providers: [
                    ExerciseStatsService
                ]
            })
            .overrideTemplate(ExerciseStatsComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ExerciseStatsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExerciseStatsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ExerciseStats(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.exerciseStats[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
